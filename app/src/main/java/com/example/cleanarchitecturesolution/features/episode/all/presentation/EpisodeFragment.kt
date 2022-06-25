package com.example.cleanarchitecturesolution.features.episode.all.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.extensions.showSnackbar
import com.example.cleanarchitecturesolution.core.extensions.viewBinding
import com.example.cleanarchitecturesolution.databinding.FragmentEpisodeBinding
import com.example.cleanarchitecturesolution.features.episode.all.presentation.adapter.EpisodeAdapter
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeDisplayable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class EpisodeFragment : Fragment(R.layout.fragment_episode), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val binding by viewBinding(FragmentEpisodeBinding::bind)
    private val viewModel: EpisodeViewModel by viewModel()
    private val adapter: EpisodeAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setOnEpisodeClickListener()
        bindViewModelToLifecycle()
        observerUiState()
    }

    private fun setupAdapter() {
        with(binding.episodeRv) {
            adapter = this@EpisodeFragment.adapter
            layoutManager = get<LinearLayoutManager>()
            setHasFixedSize(true)
            addItemDecoration(get<DividerItemDecoration>())
        }
    }

    private fun setOnEpisodeClickListener() {
        adapter.onClick = { episodeId ->
            viewModel.onEpisodeClick(episodeId)
        }
    }

    private fun observerUiState() {
        fun showLoader(isLoading: Boolean) {
            binding.loader.isVisible = isLoading
        }

        fun showEpisodes(episodes: List<EpisodeDisplayable>) {
            adapter.submitList(episodes)
        }

        fun showError(message: String?) = with(binding.root) {
            showSnackbar(message)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(STARTED) {
                viewModel.uiState
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, STARTED)
                    .distinctUntilChanged()
                    .collect { uiState ->
                        showLoader(uiState.isFetchingEpisode)
                        showEpisodes(uiState.episodeItems)
                        showError(uiState.errorMessage)
                    }
            }
        }
    }

    private fun bindViewModelToLifecycle() {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
    }
}
