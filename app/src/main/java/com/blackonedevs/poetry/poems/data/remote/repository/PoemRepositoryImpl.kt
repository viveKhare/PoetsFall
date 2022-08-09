package com.blackonedevs.poetry.poems.data.remote.repository

import com.blackonedevs.poetry.poems.core.Resource
import com.blackonedevs.poetry.poems.data.local.dao.PoemItemDao
import com.blackonedevs.poetry.poems.data.remote.PoemsApi
import com.blackonedevs.poetry.poems.domain.model.PoemItem
import com.blackonedevs.poetry.poems.domain.repository.PoemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PoemRepositoryImpl(private val api: PoemsApi, private val dao: PoemItemDao) : PoemRepository {
    override fun getPoems(size: Int): Flow<Resource<List<PoemItem>>> = flow {

        emit(Resource.Loading())
        var data = dao.getAllPoemItems().map { it.toPoemItem() }
        emit(Resource.Loading(data))
        var message = "";
        try {

            val poems = api.getPoems(size).map { it.toPoemItemEntity() }
            (poems.isNotEmpty()).let {
                dao.deleteAllPoemItems()
                dao.insertPoems(poems = poems)
                data = dao.getAllPoemItems().map { it.toPoemItem() }
                emit(Resource.Success(data = data))
            }

        } catch (e: HttpException) {
            e.printStackTrace()
            message = "Something went wrong"
            emit(
                Resource.Error(message = message, data = getDummyData(message = message, data))
            )
        } catch (e: IOException) {
            e.printStackTrace()
            message = "Problem with internet connection"
            emit(
                Resource.Error(message = message, data = getDummyData(message = message, data))
            )

        } catch (e: Exception) {
            e.printStackTrace()
            message = e.message ?: "Something went wrong"
            emit(
                Resource.Error(message = message, data = getDummyData(message = message, data))
            )

        }

    }

    private fun getDummyData(message: String = "", data: List<PoemItem>): List<PoemItem> {

        if (data.isNotEmpty()) {
            return data
        }

        return mutableListOf(
            PoemItem(
                "Lost Connection", "If you see my loved one's in Heaven\n" +
                        "it won't be hard to find.\n" +
                        "they'll be the one to greet you first,\n" +
                        "they r one-of-a-kind.\n" +
                        "\n", message
            )
        )

    }
}