package com.example.cleanarchitecturesolution.features.episode.all.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeDisplayable
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeUiState
import com.example.cleanarchitecturesolution.features.episode.domain.GetEpisodesUseCase
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode
import com.example.cleanarchitecturesolution.features.episode.navigation.EpisodeNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// FIXME(Rewrite to MVI)
// FIXME(Pagination)
class EpisodeViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val episodeNavigator: EpisodeNavigator,
    private val errorMapper: ErrorMapper,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(EpisodeUiState())
    val uiState: StateFlow<EpisodeUiState> = _uiState.asStateFlow()

    // FIXME(During configuration changing fetching data is call again)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fetchEpisodes()
    }

    fun onEpisodeClick(episodeId: Int) {
        episodeNavigator.openEpisodeDetailsScreen(episodeId)
    }

    private fun fetchEpisodes() {
        setLoadingState(isLoading = true)
        getEpisodesUseCase(
            params = Unit,
            scope = viewModelScope,
        ) { result ->
            setLoadingState(isLoading = false)
            result.onSuccess { setEpisodesState(it) }
            result.onFailure { handleError(it) }
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

    private fun handleError(throwable: Throwable) {
        _uiState.update { it.copy(errorMessage = errorMapper.map(throwable)) }
    }
}
