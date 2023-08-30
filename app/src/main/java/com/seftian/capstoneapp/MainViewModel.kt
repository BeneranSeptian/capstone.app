package com.seftian.capstoneapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seftian.capstoneapp.data.ResourceState
import com.seftian.capstoneapp.domain.model.Game
import com.seftian.capstoneapp.domain.model.GameDetail
import com.seftian.capstoneapp.domain.usecase.GamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gamesUseCase: GamesUseCase
) : ViewModel() {

    private val _games = MutableLiveData<List<Game>>()
    val games get() = _games

    init {
        viewModelScope.launch {
            gamesUseCase.getAllGames().collectLatest {
                _games.value = it
            }
        }
    }

    fun refreshGame() {
        viewModelScope.launch {
            gamesUseCase.refreshGames()
        }
    }

    private val _gameDetailState = MutableStateFlow<ResourceState<GameDetail>>(ResourceState.Loading)
    val gameDetailState: StateFlow<ResourceState<GameDetail>> = _gameDetailState

    fun getGameDetail(id: Int) {
        viewModelScope.launch {
            gamesUseCase.getDetailGame(id).collect { state ->
                _gameDetailState.value = state
            }
        }
    }
}