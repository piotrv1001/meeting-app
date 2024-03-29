package com.vassev.meetingapp.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vassev.meetingapp.presentation.navigation.BottomNavigationBar
import com.vassev.meetingapp.presentation.navigation.Navigation
import com.vassev.meetingapp.presentation.navigation.NavigationItem
import com.vassev.meetingapp.presentation.settings.SettingsViewmodel
import com.vassev.meetingapp.presentation.util.Screen
import com.vassev.meetingapp.ui.theme.MeetingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewmodel = hiltViewModel<SettingsViewmodel>()
            MeetingAppTheme(
                settingsViewmodel
            ) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Black,
                        darkIcons = false
                    )
                }
                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                showBottomBar = when(navBackStackEntry?.destination?.route) {
                    Screen.HomeScreen.route -> true
                    Screen.CalendarScreen.route -> true
                    Screen.SettingsScreen.route -> true
                    else -> false
                }
                Scaffold(
                    bottomBar = {
                        if(showBottomBar)
                            BottomNavigationBar(
                                items = listOf(
                                    NavigationItem(
                                        name = "Home",
                                        route = Screen.HomeScreen.route,
                                        icon = Icons.Outlined.Home,
                                        selectedIcon = Icons.Filled.Home,
                                    ),
                                    NavigationItem(
                                        name = "Calendar",
                                        route = Screen.CalendarScreen.route,
                                        icon = Icons.Outlined.CalendarToday,
                                        selectedIcon = Icons.Filled.CalendarToday,
                                    ),
                                    NavigationItem(
                                        name = "Settings",
                                        route = Screen.SettingsScreen.route,
                                        icon = Icons.Outlined.Settings,
                                        selectedIcon = Icons.Filled.Settings,
                                    ),
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                    }
                ) { innerPadding ->
                    Navigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        settingsViewmodel = settingsViewmodel
                    )
                }
            }
        }
    }
}