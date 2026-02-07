package com.example.week4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week4.navigation.ROUTE_CALENDAR
import com.example.week4.navigation.ROUTE_HOME
import com.example.week4.navigation.ROUTE_SETTINGS
import com.example.week4.view.HomeScreen
import com.example.week4.view.CalendarScreen
import com.example.week4.view.SettingsScreen
import com.example.week4.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: TaskViewModel = viewModel()

            // navhost
            NavHost(
                navController = navController,
                startDestination = ROUTE_HOME
            ) {

                composable(ROUTE_HOME) {
                    HomeScreen(
                        taskViewModel = viewModel,
                        onTaskClick = { id ->
                            viewModel.openTask(id)
                        },
                        onAddClick = {
                            viewModel.addTaskDialogVisible.value = true
                        },
                        onNavigateCalendar = {
                            navController.navigate(ROUTE_CALENDAR)
                        },
                        onNavigateSettings = {
                            navController.navigate(ROUTE_SETTINGS)
                        }
                    )
                }

                composable(ROUTE_CALENDAR) {
                    CalendarScreen(
                        viewModel = viewModel,
                        onTaskClick = { id ->
                            viewModel.openTask(id)
                        },
                        onNavigateHome = {
                            navController.navigate(ROUTE_HOME)
                        }
                    )
                }
                composable(ROUTE_SETTINGS) {
                    SettingsScreen(
                        viewModel = viewModel,
                        onTaskClick = { id ->
                            viewModel.openTask(id)
                        },
                        onNavigateHome = {
                            navController.navigate(ROUTE_HOME)
                        }
                    )
                }
            }
        }
    }
}


