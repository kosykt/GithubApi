package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.model.HistoryCacheUserEntity

@Dao
interface HistoryCacheUsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(users: List<HistoryCacheUserEntity>)

    @Query("SELECT * FROM HistoryCacheUserEntity")
    fun getAll(): List<HistoryCacheUserEntity>
}