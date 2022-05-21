package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.model.HistoryCacheRepoEntity

@Dao
interface HistoryCacheReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(models: List<HistoryCacheRepoEntity>)

    @Query("SELECT * FROM HistoryCacheRepoEntity WHERE ownerId = :ownerId")
    fun getReposByOwnerId(ownerId: String): List<HistoryCacheRepoEntity>
}