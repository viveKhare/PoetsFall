package com.blackonedevs.poetry.poems.data.local.dao

import com.blackonedevs.poetry.poems.data.local.PoemItemDatabase
import com.blackonedevs.poetry.poems.data.local.entity.PoemItemEntity
import com.blackonedevs.poetry.poems.di.PoemModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(PoemModule::class)
class PoemDaoTest {
   @Inject lateinit var database: PoemItemDatabase
    private lateinit var dao: PoemItemDao

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertPoems() {
        val size = 50
        val data = mutableListOf<PoemItemEntity>()
        for (i in 1..size) {
            data.add(PoemItemEntity(author = "Author $i", title = "Title $i", lines = "Lines $i"))
        }
        runTest {
            dao.insertPoems(data.toList())
            val data = dao.getAllPoemItems()
            assertThat(data).hasSize(size)
        }



    }
    @Test
    fun deletePoems() {
        val size = 0
        val data = mutableListOf<PoemItemEntity>()
        for (i in 1..10) {
            data.add(PoemItemEntity(author = "Author $i", title = "Title $i", lines = "Lines $i"))
        }
        runTest {
            dao.insertPoems(data.toList())
            dao.deleteAllPoemItems()
            val data = dao.getAllPoemItems()
            assertThat(data).hasSize(size)
        }



    }
}