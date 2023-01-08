package com.example.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Observable
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {

    override val isOnline: Observable<Boolean> = Observable.create {emitter ->

        val connectivityManager: ConnectivityManager? = context.getSystemService()

        /**
         * The callback's methods are invoked on changes to *any* network, not just the active
         * network. So to check for network connectivity, one must query the active network of the
         * ConnectivityManager.
         */

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                emitter.onNext(connectivityManager.isCurrentlyConnected())
            }

            override fun onLost(network: Network) {
                emitter.onNext(connectivityManager.isCurrentlyConnected())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                emitter.onNext(connectivityManager.isCurrentlyConnected())
            }
        }

        connectivityManager?.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback
        )

        emitter.onNext(connectivityManager.isCurrentlyConnected())

        emitter.setCancellable {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }

@Suppress("DEPRECATION")
private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
    null -> false
    else -> when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
            activeNetwork
                ?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        else -> activeNetworkInfo?.isConnected ?: false
    }
}
}