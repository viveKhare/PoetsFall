package com.blackonedevs.poetry.domain.repository

import com.blackonedevs.poetry.poems.core.Resource
import com.blackonedevs.poetry.poems.domain.model.PoemItem
import com.blackonedevs.poetry.poems.domain.repository.PoemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePoemRepository : PoemRepository {
    override fun getPoems(size: Int): Flow<Resource<List<PoemItem>>> {
        return flow {

            emit(Resource.Loading(data = emptyList()))

            emit(Resource.Success(data = mutableListOf(PoemItem("", "", ""))))

            emit(Resource.Error(data = emptyList(), message = "Error"))


        }
    }
}