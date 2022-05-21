package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.model.FavouriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavouriteUserEntity)

    @Delete
    fun delete(user: FavouriteUserEntity)

    @Query("SELECT * FROM FavouriteUserEntity")
    fun getAll(): Flow<List<FavouriteUserEntity>>
}