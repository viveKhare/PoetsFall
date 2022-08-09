package com.blackonedevs.poetry.poems.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackonedevs.poetry.poems.core.Resource
import com.blackonedevs.poetry.poems.domain.model.PoemItem
import com.blackonedevs.poetry.poems.domain.use_cases.GetPoems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PoemViewModel @Inject constructor(private val getPoems: GetPoems) : ViewModel() {
    private val _state = mutableStateOf(PoemItemState())
    val state: State<PoemItemState> = _state


    init {
        loadData()

    }

    fun loadData() {


        getPoems().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        state.value.copy(
                            poemItems = result.data,
                            isLoading = false,
                            showRetry = false
                        )
                }
                is Resource.Error -> {
                    _state.value =
                        state.value.copy(
                            poemItems = result.data,
                            message = result.message,
                            isLoading = false, showRetry = true
                        )


                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        poemItems = result.data,
                        message = result.message,
                        isLoading = true, showRetry = false
                    )
                }
            }


        }.launchIn(viewModelScope)


    }


}