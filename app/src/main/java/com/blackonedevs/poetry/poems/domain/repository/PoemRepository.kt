package com.blackonedevs.poetry.poems.domain.repository

import com.blackonedevs.poetry.poems.core.Resource
import com.blackonedevs.poetry.poems.domain.model.PoemItem
import kotlinx.coroutines.flow.Flow


interface PoemRepository {
      fun  getPoems(size: Int = 100): Flow<Resource<List<PoemItem>>>
}