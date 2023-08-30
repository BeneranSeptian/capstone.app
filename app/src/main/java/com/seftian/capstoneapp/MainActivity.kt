package com.seftian.capstoneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.seftian.capstoneapp.ui.Screens
import com.seftian.capstoneapp.ui.screens.FavouriteMovieListScreen
import com.seftian.capstoneapp.ui.screens.MovieDetailScreen
import com.seftian.capstoneapp.ui.screens.MovieListScreen
import com.seftian.capstoneapp.ui.theme.CapstoneAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    navHostController: NavHostController = rememberNavController()
) {
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = Screens.Home.route,
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Favourite",
            route = Screens.Favourite.route,
            icon = Icons.Rounded.Favorite,
        ),
    )
    Scaffold(
        bottomBar = {
            if(currentRoute != Screens.DetailGame.route){
                androidx.compose.material3.NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = item.route == backStackEntry.value?.destination?.route

                        NavigationBarItem(
                            selected = selected,
                            onClick = { navHostController.navigate(item.route) },
                            label = {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon",
                                )
                            }
                        )
                    }
                }
            }
        },
        content = {innerPadding ->
            NavHost(navController = navHostController, startDestination = Screens.Home.route, modifier = Modifier.padding(innerPadding)) {
                composable(Screens.Home.route){
                    MovieListScreen(
                        navController = navHostController
                    )
                }
                composable(Screens.DetailGame.route){backStackEntry->
                    val id = backStackEntry.arguments?.getString("id")
                    if (id != null) {
                        MovieDetailScreen(id = id)
                    } else {
                        Text("Error: No Game ID provided.")
                    }
                }

                composable(Screens.Favourite.route){
                   FavouriteMovieListScreen(navController = navHostController)
                }
            }
        }
    )
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)