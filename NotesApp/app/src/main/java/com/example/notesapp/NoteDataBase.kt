package com.example.notesapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [NoteModel::class], version = 1)
abstract class NoteDataBase:RoomDatabase() {

    abstract fun noteDao():NotesDao

    companion object{
        private var INSTANCE:NoteDataBase?=null

        fun getInstance(context: Context):NoteDataBase{

            if(INSTANCE==null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, NoteDataBase::class.java, "note-database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}