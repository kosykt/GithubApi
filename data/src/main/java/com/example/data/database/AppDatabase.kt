package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.ReposDao
import com.example.data.database.dao.UsersDao
import com.example.data.database.model.RepoEntity
import com.example.data.database.model.UserEntity

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