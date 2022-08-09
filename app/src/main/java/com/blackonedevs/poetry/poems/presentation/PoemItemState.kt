package com.blackonedevs.poetry.poems.presentation

import com.blackonedevs.poetry.poems.domain.model.PoemItem

data class PoemItemState(
    val poemItems: List<PoemItem>?
    = null, val isLoading: Boolean = false, val message: String? = "",val showRetry:Boolean=false
)
