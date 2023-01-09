package com.example.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.designsystem.extension.customShadow
import com.example.domain.repository.NetworkMonitor
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.TaskManagementTheme
import com.example.domain.repository.UserSignInCheckRepository
import com.example.taskmanagement.navigation.TmNavHost
import com.example.taskmanagement.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var userSignInCheckRepository: UserSignInCheckRepository

    private val isOnline: MutableState<Boolean> = mutableStateOf(true)
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        disposable =
            networkMonitor.isOnline.subscribe { isOnline -> this.isOnline.value = isOnline }
        val isUserSignedIn = userSignInCheckRepository.isSignedIn()
        setContent {
            TaskManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val appState = rememberAppState()
                    val notConnected = stringResource(R.string.notConnected)
                    appState.uiController.setSystemBarsColor(
                        darkIcons = !isSystemInDarkTheme(),
                        color = Color.Transparent
                    )
                    LaunchedEffect(isOnline.value) {
                        if (!isOnline.value)
                            appState.snackbarHostState.showSnackbar(notConnected)
                    }
                    Scaffold(
                        snackbarHost = { SnackbarHost(appState.snackbarHostState) },
                        bottomBar = {
                            if (appState.shouldShowBottomBar)
                                TmBottomBar(appState = appState)
                        }
                    ) { paddingValues ->
                        TmNavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = appState.navController,
                            shouldShowOnboarding = sharedPreferencesUtils.isFirstLaunch(),
                            shouldShowAuth = !isUserSignedIn,
                            onOnboardingPassed = { sharedPreferencesUtils.markOnboardingPassed() },
                            onBackClick = { appState.onBackClick() }
                        )
                    }

                }
            }
        }
    }

    @Composable
    fun TmBottomBar(
        appState: AppState
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 4.dp, horizontal = 12.dp)
                .customShadow(),
            shape = MaterialTheme.shapes.medium,
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                appState.topLevelDestinations.forEach { item ->
                    val isSelected = appState.currentTopLevelDestination == item
                    val isDarkTheme = isSystemInDarkTheme()
                    BottomNavigationItem(
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(if (isSelected) item.selectedIcon else item.unselectedIcon),
                                    contentDescription = null
                                )
                                if (isSelected) {
                                    val primaryColor = MaterialTheme.colors.primary
                                    val secondaryColor = MaterialTheme.colors.secondary
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Canvas(Modifier.size(6.dp)) {
                                        drawCircle(
                                            color = if (isDarkTheme) secondaryColor else primaryColor
                                        )
                                    }
                                }

                            }
                        },
                        selected = isSelected,
                        alwaysShowLabel = false,
                        selectedContentColor = if (isDarkTheme) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary,
                        unselectedContentColor = Gray,
                        onClick = {
                            appState.navigateToTopLevelDestination(item)
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
