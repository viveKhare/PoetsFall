package com.blackonedevs.poetry.poems.domain.use_cases

import androidx.lifecycle.viewModelScope
import com.blackonedevs.poetry.domain.repository.FakePoemRepository
import com.blackonedevs.poetry.poems.core.Resource
import com.blackonedevs.poetry.poems.domain.repository.PoemRepository
import com.blackonedevs.poetry.poems.presentation.PoemViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Before
import org.junit.Test

class GetPoemsTest {
    private lateinit var repository: FakePoemRepository
    private lateinit var getPoemsUseCase: GetPoems

    @Before
    fun setup() {
        repository = FakePoemRepository()
        getPoemsUseCase = GetPoems(repository)

    }


    @Test
    fun testPoems() {
        runTest {
            getPoemsUseCase().map { result ->
                when (result) {
                    is Resource.Success -> {
                        Truth.assertThat(result.data!!).hasSize(1)
                    }
                    is Resource.Error -> {
                        Truth.assertThat(result.data!!).hasSize(0)
                        Truth.assertThat(result.message!!).contains("Error")
                    }
                    is Resource.Loading -> {
                        Truth.assertThat(result.data!!).hasSize(0)
                    }
                }

            }.launchIn(this)
        }


    }
}