package com.example.cleanarchitecturesolution.features.character.all.presentation

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
import com.example.cleanarchitecturesolution.databinding.FragmentCharacterBinding
import com.example.cleanarchitecturesolution.features.character.all.presentation.adapter.CharacterAdapter
import com.example.cleanarchitecturesolution.features.character.all.presentation.model.CharacterDisplayable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class CharacterFragment : Fragment(R.layout.fragment_character), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val viewModel: CharacterViewModel by viewModel()
    private val adapter: CharacterAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setOnCharacterClickListener()
        bindViewModelToLifecycle()
        observerUiState()
    }

    private fun setupAdapter() {
        with(binding.characterRv) {
            adapter = this@CharacterFragment.adapter
            layoutManager = get<LinearLayoutManager>()
            setHasFixedSize(true)
            addItemDecoration(get<DividerItemDecoration>())
        }
    }

    private fun setOnCharacterClickListener() {
        adapter.onClick = { characterId ->
            viewModel.onCharacterClick(characterId)
        }
    }

    private fun observerUiState() {
        fun showLoader(isLoading: Boolean) {
            binding.loader.isVisible = isLoading
        }

        fun showCharacters(characters: List<CharacterDisplayable>) {
            adapter.submitList(characters)
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
                        showLoader(uiState.isFetchingCharacter)
                        showCharacters(uiState.characterItems)
                        showError(uiState.errorMessage)
                    }
            }
        }
    }

    private fun bindViewModelToLifecycle() {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
    }

}
