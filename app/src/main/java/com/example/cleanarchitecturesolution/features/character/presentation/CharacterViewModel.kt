package com.example.cleanarchitecturesolution.features.character.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.features.character.domain.GetCharactersUseCase
import com.example.cleanarchitecturesolution.features.character.domain.model.Character
import com.example.cleanarchitecturesolution.features.character.presentation.model.CharacterDisplayable
import com.example.cleanarchitecturesolution.features.character.presentation.model.CharacterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// FIXME(Rewrite to MVI)
// FIXME(Pagination)
class CharacterViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val errorMapper: ErrorMapper,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow()

    // FIXME(During configuration changing fetching data is call again)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fetchCharacters()
    }

    private fun fetchCharacters() {
        setLoadingState(true)
        getCharactersUseCase(
            params = Unit,
            scope = viewModelScope,
        ) { result ->
            setLoadingState(isLoading = false)
            result.onSuccess { setCharactersState(it) }
            result.onFailure { handleError(it) }
        }
    }

    private fun setCharactersState(characters: List<Character>) {
        _uiState.update {
            it.copy(characterItems = characters.map { character ->
                CharacterDisplayable(character)
            })
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isFetchingCharacter = isLoading)
        }
    }

    private fun handleError(throwable: Throwable) {
        _uiState.update { it.copy(errorMessage = errorMapper.map(throwable)) }
    }
}
