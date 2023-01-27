package com.example.notesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
@PrimaryKey(autoGenerate = true)
val id:Int,
@ColumnInfo(name = "Title")
val title:String,
@ColumnInfo(name = "Note")
val theNote:String,
@ColumnInfo(name = "date")
val date:String,
@ColumnInfo(name = "No of Character")
val NoOfCharacter:Int,
@ColumnInfo(name = "encrypt")
val encryption:Boolean
)
