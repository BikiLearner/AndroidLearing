package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.RowId

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(noteModel: NoteModel)

    @Update
    suspend fun updateNote(noteModel: NoteModel)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id:Int)

    @Query("SELECT * From notes")
    fun fetchAll():LiveData<List<NoteModel>>

    @Query("SELECT * From notes WHERE id = :id")
    fun fetchContentById(id:Int):LiveData<NoteModel>

    @Query("UPDATE notes SET encrypt= :encryption WHERE id= :id")
    suspend fun update(encryption:Boolean,id: Int)
}