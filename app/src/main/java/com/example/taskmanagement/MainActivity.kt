package com.example.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.auth.navigation.onboardingRoute
import com.example.auth.navigation.signUpRoute
import com.example.dashboard.navigation.dashboardGraph
import com.example.designsystem.components.Overlay
import com.example.designsystem.extension.customShadow
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.TaskManagementTheme
import com.example.designsystem.theme.dimens
import com.example.designsystem.utils.animationDuration
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
    lateinit var userSignInCheckRepository: UserSignInCheckRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TaskManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isUserSignedIn by remember {
                        mutableStateOf(userSignInCheckRepository.isSignedIn())
                    }
                    val appState = rememberAppState()
                    appState.uiController.setSystemBarsColor(
                        darkIcons = !isSystemInDarkTheme(),
                        color = Color.Transparent
                    )
                    Scaffold(
                        bottomBar = {
                            if (appState.shouldShowBottomBar) TmBottomBar(appState = appState)
                        }
                    ) { paddingValues ->
                        val startDestination =
                            remember { if (!isUserSignedIn) authNavigationRouteGraph else dashboardGraph }
                        val authGraphStartDestination =
                            remember { if (sharedPreferencesUtils.isFirstLaunch()) onboardingRoute else signUpRoute }
                        TmNavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = appState.navController,
                            startDestination = startDestination,
                            authGraphStartDestination = authGraphStartDestination,
                            onOnboardingPassed = { sharedPreferencesUtils.markOnboardingPassed() },
                            onBackClick = { appState.onBackClick() },
                            onAuthPassed = { isUserSignedIn = true }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun TmBottomBar(appState: AppState) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(MaterialTheme.dimens.bottomNavBarSize)
                .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
                .customShadow(),
            shape = MaterialTheme.shapes.medium,
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = MaterialTheme.dimens.paddingSmall,
                        horizontal = MaterialTheme.dimens.paddingMedium
                    ),
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
                                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingSmall))
                                    Canvas(Modifier.size(MaterialTheme.dimens.paddingSmall)) {
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
                        onClick = { appState.navigateToTopLevelDestination(item) }
                    )
                }
            }

        }
    }
}
