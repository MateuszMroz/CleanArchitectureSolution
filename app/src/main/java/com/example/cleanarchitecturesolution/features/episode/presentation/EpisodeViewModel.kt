package com.example.cleanarchitecturesolution.features.episode.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturesolution.features.episode.domain.GetEpisodesUseCase
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode
import com.example.cleanarchitecturesolution.features.episode.presentation.model.EpisodeDisplayable
import com.example.cleanarchitecturesolution.features.episode.presentation.model.EpisodeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// FIXME(Rewrite to MVI)
// FIXME(Pagination)
class EpisodeViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(EpisodeUiState())
    val uiState: StateFlow<EpisodeUiState> = _uiState.asStateFlow()

    // FIXME(During configuration changing fetching data is call again)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fetchEpisodes()
    }

    private fun fetchEpisodes() {
        setLoadingState(isLoading = true)
        getEpisodesUseCase(
            params = Unit,
            scope = viewModelScope,
        ) { result ->
            result.onSuccess {
                setLoadingState(isLoading = false)
                setEpisodesState(it)
            }
            result.onFailure {
                setLoadingState(isLoading = false)
                handleFailure(it)
            }
        }
    }

    private fun setEpisodesState(episodes: List<Episode>) {
        _uiState.update {
            it.copy(episodeItems = episodes.map { episode ->
                EpisodeDisplayable(episode)
            })
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isFetchingEpisode = isLoading)
        }
    }

    private fun handleFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(errorMessage = throwable.message)
        }
    }
}
