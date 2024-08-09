package com.coding.meet.thirdeye.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coding.meet.thirdeye.ui.common.LoadingDialog
import com.coding.meet.thirdeye.ui.screens.camera.CameraScreen
import com.coding.meet.thirdeye.ui.screens.instruction.InstructionScreen
import com.coding.meet.thirdeye.ui.screens.home.HomeScreen
import com.coding.meet.thirdeye.ui.screens.prompt_write.PromptScreen
import com.coding.meet.thirdeye.ui.screens.result.ResultScreen
import com.coding.meet.thirdeye.util.FadeIn
import com.coding.meet.thirdeye.util.FadeOut
import com.coding.meet.thirdeye.viewmodels.MainViewModel
import com.coding.meet.thirdeye.viewmodels.ToolViewModel

/**
 * Created 28-02-2024 at 03:04 pm
 */
val LocalNavControllerProvider: ProvidableCompositionLocal<NavHostController> = staticCompositionLocalOf {
    error("No Nav Controller provided")
}

@Composable
fun NavGraph(activity: Activity) {
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>()
    val toolViewModel = viewModel<ToolViewModel>()
    val isLoading by mainViewModel.isLoadingStateFlow.collectAsState()
    LoadingDialog(isLoading)
    CompositionLocalProvider(LocalNavControllerProvider provides navController) {
        NavHost(
            navController = navController,
            startDestination = RouteScreen.Home.route,
            enterTransition = { FadeIn },
            exitTransition = { FadeOut },
        ) {
            composable(route = RouteScreen.Home.route) {
                HomeScreen(mainViewModel = mainViewModel,activity)
            }
            composable(route = RouteScreen.Instruction.route) {
                InstructionScreen()
            }
            composable(route = RouteScreen.Prompt.route) {
                PromptScreen(mainViewModel = mainViewModel)
            }
            composable(route = RouteScreen.Camera.route) {
                CameraScreen(mainViewModel = mainViewModel)
            }
            composable(route = RouteScreen.Result.route) {
                ResultScreen(mainViewModel = mainViewModel, toolViewModel = toolViewModel)
            }
        }
    }
}
