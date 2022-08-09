package com.blackonedevs.poetry.poems.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blackonedevs.poetry.poems.domain.model.PoemItem

@Entity
data class PoemItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String, val author: String, val lines: String
) {
    fun toPoemItem(): PoemItem {
        return PoemItem(
            author, lines, title =
            title
        )
    }
}
