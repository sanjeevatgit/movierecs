package com.moviegeek.tv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviegeek.tv.BuildConfig
import com.moviegeek.tv.data.MovieGeekRepository
import com.moviegeek.tv.data.RecommendationRail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = true,
    val rails: List<RecommendationRail> = emptyList(),
    val error: String? = null
)

class HomeViewModel(
    private val repository: MovieGeekRepository = MovieGeekRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = HomeUiState(loading = true)

        viewModelScope.launch {
            runCatching {
                repository.loadRails(BuildConfig.DEFAULT_USER_ID)
            }.onSuccess { rails ->
                _uiState.value = HomeUiState(loading = false, rails = rails)
            }.onFailure { throwable ->
                _uiState.value = HomeUiState(
                    loading = false,
                    error = throwable.message ?: "Failed to load recommendations"
                )
            }
        }
    }
}
