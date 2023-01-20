package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmpolyeeDataBase:RoomDatabase() {
    abstract fun empolyeeDao():EmpolyeeDao

    companion object{
        @Volatile
        private var INSTANCE:EmpolyeeDataBase?=null

        fun getInstance(context: Context):EmpolyeeDataBase{
            synchronized(this){
                var instance= INSTANCE

                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,EmpolyeeDataBase::class.java,
                       "employee-database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE=instance
                }
                return instance
            }

        }
    }
}