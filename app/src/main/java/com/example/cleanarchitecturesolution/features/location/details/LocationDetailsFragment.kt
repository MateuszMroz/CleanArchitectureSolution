package com.example.cleanarchitecturesolution.features.location.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cleanarchitecturesolution.core.extensions.showSnackbar
import com.example.cleanarchitecturesolution.databinding.FragmentLocationDetailsBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class LocationDetailsFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private var _binding: FragmentLocationDetailsBinding? = null
    private val binding: FragmentLocationDetailsBinding
        get() = _binding!!

    private val viewModel: LocationDetailsViewModel by viewModel {
        parametersOf(arguments?.get(LOCATION_DETAILS_KEY))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        const val LOCATION_DETAILS_KEY = "LOCATION_DETAILS_KEY"
    }
}
