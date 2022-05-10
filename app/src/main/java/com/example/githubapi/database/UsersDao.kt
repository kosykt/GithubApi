package com.example.githubapi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapi.model.UsersDTO

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<UsersDTO>)

    @Query("SELECT * FROM UsersDTO")
    suspend fun getAll(): List<UsersDTO>
}