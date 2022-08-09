package com.blackonedevs.poetry.poems.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blackonedevs.poetry.poems.data.local.dao.PoemItemDao
import com.blackonedevs.poetry.poems.data.local.entity.PoemItemEntity

@Database(
    entities =
    [PoemItemEntity::class], version = 1, exportSchema = false
)
abstract class PoemItemDatabase : RoomDatabase() {
    abstract val dao:PoemItemDao

}