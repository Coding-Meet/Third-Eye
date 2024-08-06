package com.coding.meet.blindaiassistant.ui.navigation


sealed class RouteScreen(var route: String) {

    data object Home : RouteScreen("home")
    data object Camera : RouteScreen("camera")
    data object Result : RouteScreen("result")
}
