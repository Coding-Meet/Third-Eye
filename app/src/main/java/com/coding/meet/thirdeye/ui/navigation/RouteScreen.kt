package com.coding.meet.thirdeye.ui.navigation


sealed class RouteScreen(var route: String) {

    data object Home : RouteScreen("home")
    data object Instruction : RouteScreen("instruction")
    data object Prompt : RouteScreen("prompt")
    data object Camera : RouteScreen("camera")
    data object Result : RouteScreen("result")
}
