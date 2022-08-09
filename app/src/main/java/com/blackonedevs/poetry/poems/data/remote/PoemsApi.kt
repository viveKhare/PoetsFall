package com.blackonedevs.poetry.poems.data.remote

import com.blackonedevs.poetry.poems.data.remote.dto.PoemItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PoemsApi {
   companion object {
        const val BASE_URL="https://poetrydb.org/author,poemcount/"
    }

    @GET("all;{size}")
    suspend fun getPoems(@Path("size") size: Int):
            ArrayList<PoemItemDto>

}