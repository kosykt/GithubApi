package com.example.githubapi.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubapi.App
import com.example.githubapi.data.database.dao.ReposDao
import com.example.githubapi.data.database.dao.UsersDao
import com.example.githubapi.data.database.model.RepoEntity
import com.example.githubapi.data.database.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        RepoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val usersDao: UsersDao
    abstract val reposDao: ReposDao
}