package com.blackonedevs.poetry.poems.data.remote.dto

import com.blackonedevs.poetry.poems.data.local.entity.PoemItemEntity
import com.blackonedevs.poetry.poems.domain.model.PoemItem

data class PoemItemDto(
    val author: String,
    val linecount: String,
    val lines: ArrayList<String>,
    val title: String


) {
    fun toPoemItemEntity(): PoemItemEntity {
        var poem=""
        for (line in lines)
        {
            poem+=line
            poem+="\n"
        }
        return PoemItemEntity(author = author, lines = poem, title = title)
    }
}
