package com.example.githubapi.data.database.dao

import androidx.room.*
import com.example.githubapi.data.database.model.UserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(users: List<UserEntity>)

    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>
}