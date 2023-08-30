package com.seftian.capstoneapp.ui

sealed class Screens(val route: String) {
    object Home: Screens("home")
    object DetailGame : Screens("detail_game/{id}") {
        fun withId(id: String): String = "detail_game/$id"
    }
    object Favourite: Screens("favourite")
}
