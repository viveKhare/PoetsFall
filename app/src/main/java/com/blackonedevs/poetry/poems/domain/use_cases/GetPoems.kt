package com.blackonedevs.poetry.poems.domain.use_cases

import com.blackonedevs.poetry.poems.core.Resource
import com.blackonedevs.poetry.poems.domain.model.PoemItem
import com.blackonedevs.poetry.poems.domain.repository.PoemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoems @Inject constructor(private val repository: PoemRepository) {
      operator fun invoke(): Flow<Resource<List<PoemItem>>> {

        return repository.getPoems(100)
    }

}