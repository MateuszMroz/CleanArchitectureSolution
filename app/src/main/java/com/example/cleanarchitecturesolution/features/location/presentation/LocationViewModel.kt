package com.example.cleanarchitecturesolution.features.location.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturesolution.features.location.domain.GetLocationsUseCase
import com.example.cleanarchitecturesolution.features.location.domain.model.Location
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationDisplayable
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// FIXME(Rewrite to MVI)
// FIXME(Pagination)
class LocationViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(LocationUiState())
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    // FIXME(During configuration changing fetching data is call again)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fetchLocations()
    }

    private fun fetchLocations() {
        setLoadingState(isLoading = true)
        getLocationsUseCase(
            params = Unit,
            scope = viewModelScope,
        ) { result ->
            result.onSuccess {
                setLoadingState(isLoading = false)
                setLocationsState(it)
            }
            result.onFailure {
                setLoadingState(isLoading = false)
                handleFailure(it)
            }
        }
    }

    private fun setLocationsState(locations: List<Location>) {
        _uiState.update {
            it.copy(locationItems = locations.map { location ->
                LocationDisplayable(location)
            })
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isFetchingLocation = isLoading)
        }
    }

    private fun handleFailure(throwable: Throwable) {
        _uiState.update {
            it.copy(errorMessage = throwable.message)
        }
    }
}
