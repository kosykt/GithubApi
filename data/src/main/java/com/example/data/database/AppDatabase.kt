package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.FavouriteReposDao
import com.example.data.database.dao.FavouriteUserDao
import com.example.data.database.dao.HistoryCacheReposDao
import com.example.data.database.dao.HistoryCacheUsersDao
import com.example.data.database.model.FavouriteReposEntity
import com.example.data.database.model.FavouriteUserEntity
import com.example.data.database.model.HistoryCacheRepoEntity
import com.example.data.database.model.HistoryCacheUserEntity

@Database(
    entities = [
        HistoryCacheUserEntity::class,
        HistoryCacheRepoEntity::class,
        FavouriteUserEntity::class,
        FavouriteReposEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val historyCacheUsersDao: HistoryCacheUsersDao
    abstract val historyCacheReposDao: HistoryCacheReposDao
    abstract val favouriteUserDao: FavouriteUserDao
    abstract val favouriteReposDao: FavouriteReposDao
}