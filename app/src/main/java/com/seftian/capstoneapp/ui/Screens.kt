package com.seftian.capstoneapp.ui

sealed class Screens(val route: String) {
    object Home: Screens("home")
    object DetailMovie : Screens("detail_movie/{id}") {
        fun withId(id: String): String = "detail_movie/$id"
    }
    object Favourite: Screens("favourite")
}
