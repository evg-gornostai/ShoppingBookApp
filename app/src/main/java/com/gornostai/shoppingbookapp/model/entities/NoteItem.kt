package com.gornostai.shoppingbookapp.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_list")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val description: String,
    @ColumnInfo(name = "time")
    val time: String
) : Serializable