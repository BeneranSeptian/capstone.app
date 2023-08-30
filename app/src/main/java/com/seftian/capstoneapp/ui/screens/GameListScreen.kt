package com.seftian.capstoneapp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.seftian.capstoneapp.MainViewModel
import com.seftian.capstoneapp.domain.model.Game
import com.seftian.capstoneapp.ui.Screens
import com.seftian.capstoneapp.ui.screens.components.ImageWithTitle
import com.seftian.capstoneapp.ui.screens.components.Section
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameListScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    val games = viewModel.games.observeAsState().value
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight * 0.4f

    val randomGame = remember { mutableStateOf<Game?>(null) }

    LaunchedEffect(games) {
        while (true) {
            games?.randomOrNull()?.let { game ->
                randomGame.value = game
            }
            delay(5000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        randomGame.value?.let { game ->
            AnimatedContent(
                targetState = game,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith
                            fadeOut(animationSpec = tween(500))
                }, label = "header game",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxHeight)
            ){
                ImageWithTitle(
                    name = it.name,
                    id = it.id.toString(),
                    backgroundUrl = it.backgroundImage,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Section(sectionName = "Top Rated Games", gameList = games, onClickItem = {
            navController.navigate(Screens.DetailGame.withId(it))
        })
        Spacer(modifier = Modifier.height(24.dp))
        Section(sectionName = "New Games", gameList = games, onClickItem = {
            navController.navigate(Screens.DetailGame.withId(it))
        })
        Spacer(modifier = Modifier.height(24.dp))
    }
}