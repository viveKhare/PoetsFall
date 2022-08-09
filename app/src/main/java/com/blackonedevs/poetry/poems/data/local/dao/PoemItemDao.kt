package com.blackonedevs.poetry.poems.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blackonedevs.poetry.poems.data.local.entity.PoemItemEntity
import com.blackonedevs.poetry.poems.domain.model.PoemItem

@Dao
interface PoemItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoems(poems:List<PoemItemEntity>)

    @Delete
    suspend fun deletePoem(poemItem: PoemItemEntity)

    @Query("Delete From PoemItemEntity where 1")
    suspend fun deleteAllPoemItems()

    @Query("Select * From PoemItemEntity")
    suspend fun getAllPoemItems():List<PoemItemEntity>


}