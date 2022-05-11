package com.example.githubapi.data.database.dao

import androidx.room.*
import com.example.githubapi.data.database.model.RepoEntity

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(models: List<RepoEntity>)

    @Query("SELECT * FROM RepoEntity WHERE ownerId = :ownerId")
    fun getReposByOwnerId(ownerId: String): List<RepoEntity>
}