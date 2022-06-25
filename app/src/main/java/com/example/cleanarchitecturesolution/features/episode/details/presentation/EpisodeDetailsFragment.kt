package com.example.cleanarchitecturesolution.features.episode.details.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.extensions.showSnackbar
import com.example.cleanarchitecturesolution.core.extensions.viewBinding
import com.example.cleanarchitecturesolution.databinding.FragmentEpisodeDetailsBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class EpisodeDetailsFragment : Fragment(R.layout.fragment_episode_details), AndroidScopeComponent {
    override val scope: Scope by fragmentScope()

    private val binding by viewBinding(FragmentEpisodeDetailsBinding::bind)
    private val viewModel: EpisodeDetailsViewModel by viewModel {
        parametersOf(arguments?.get(EPISODES_DETAILS_KEY))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()
    }

    private fun observeUiState() {
        fun showError(message: String?) = with(binding.root) {
            showSnackbar(message)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(STARTED) {
                viewModel.uiState
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, STARTED)
                    .distinctUntilChanged()
                    .collect { uiState ->
                        showError(uiState.errorMessage)
                    }
            }
        }
    }

    companion object {
        const val EPISODES_DETAILS_KEY = "EPISODE_DETAILS_KEY"
    }
}
