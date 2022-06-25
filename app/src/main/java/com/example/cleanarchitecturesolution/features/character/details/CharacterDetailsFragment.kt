package com.example.cleanarchitecturesolution.features.character.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.extensions.showSnackbar
import com.example.cleanarchitecturesolution.core.extensions.viewBinding
import com.example.cleanarchitecturesolution.databinding.FragmentCharacterDetailsBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details),
    AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val binding by viewBinding(FragmentCharacterDetailsBinding::bind)
    private val viewModel: CharacterDetailsViewModel by viewModel {
        parametersOf(arguments?.get(CHARACTER_DETAILS_KEY))
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    .distinctUntilChanged()
                    .collect { uiState ->
                        showError(uiState.errorMessage)
                    }
            }
        }
    }

    companion object {
        const val CHARACTER_DETAILS_KEY = "CHARACTER_DETAILS_KEY"
    }
}
