package com.blackonedevs.poetry.poems.di

import android.app.Application
import androidx.room.Room
import com.blackonedevs.poetry.poems.core.Constant
import com.blackonedevs.poetry.poems.data.local.PoemItemDatabase
import com.blackonedevs.poetry.poems.data.local.dao.PoemItemDao
import com.blackonedevs.poetry.poems.data.remote.PoemsApi
import com.blackonedevs.poetry.poems.data.remote.repository.PoemRepositoryImpl
import com.blackonedevs.poetry.poems.domain.repository.PoemRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PoemModule {
    @Provides
    @Singleton
    fun providePoemApi(app: Application): PoemsApi {

        /*val interceptor =
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(interceptor).build()*/
        val build = Retrofit.Builder().baseUrl(PoemsApi.BASE_URL)/*.client(client)*/
            .addConverterFactory(GsonConverterFactory.create()).build()
        return build.create(PoemsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePoemDatabaseDao(app: Application): PoemItemDatabase {
        return Room.databaseBuilder(app, PoemItemDatabase::class.java, "poems_db")
            .build()
    }

    @Provides
    @Singleton
    fun providePoemRepository(api: PoemsApi, db: PoemItemDatabase): PoemRepository {
        return PoemRepositoryImpl(api = api, dao = db.dao);
    }


}