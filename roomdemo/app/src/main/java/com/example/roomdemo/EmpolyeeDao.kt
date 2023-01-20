package com.example.roomdemo

import android.database.sqlite.SQLiteException
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpolyeeDao {
    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    @Query("Select * from `employee-table`")
    fun fetchAllEmployee():Flow<List<EmployeeEntity>>

    @Query("Select * from `employee-table` where id=:id")
    fun fetchEmployeeById(id:Int):Flow<EmployeeEntity>
}