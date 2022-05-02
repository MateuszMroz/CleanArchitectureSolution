package com.example.cleanarchitecturesolution.features.location.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchitecturesolution.core.extensions.showSnackbar
import com.example.cleanarchitecturesolution.databinding.FragmentLocationBinding
import com.example.cleanarchitecturesolution.features.location.presentation.adapter.LocationAdapter
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class LocationFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private var _binding: FragmentLocationBinding? = null
    private val binding: FragmentLocationBinding
        get() = _binding!!

    private val viewModel: LocationViewModel by viewModel()
    private val adapter: LocationAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(viewModel)

        setupAdapter()
        bindViewModelToLifecycle()
        observerUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAdapter() {
        with(binding.locationRv) {
            adapter = this@LocationFragment.adapter
            layoutManager = get<LinearLayoutManager>()
            setHasFixedSize(true)
            addItemDecoration(get<DividerItemDecoration>())
        }
    }

    private fun observerUiState() {
        fun showLoader(uiState: LocationUiState) {
            binding.loader.isVisible = uiState.isFetchingLocation
        }

        fun showLocations(uiState: LocationUiState) {
            adapter.submitList(uiState.locationItems)
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
                        showLoader(uiState)
                        showLocations(uiState)
                        showError(uiState.errorMessage)
                    }
            }
        }
    }

    private fun bindViewModelToLifecycle() {
        lifecycle.addObserver(viewModel)
    }
}
