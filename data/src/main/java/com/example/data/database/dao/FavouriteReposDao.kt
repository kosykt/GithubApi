package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.model.FavouriteReposEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteReposDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(reposEntity: FavouriteReposEntity)

    @Delete
    fun delete(reposEntity: FavouriteReposEntity)

    @Query("SELECT * FROM FavouriteReposEntity")
    fun getAll(): Flow<List<FavouriteReposEntity>>
}