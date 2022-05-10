package com.example.githubapi.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubapi.App
import com.example.githubapi.model.UsersDTO

@Database(entities = [UsersDTO::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val usersDao: UsersDao

    companion object{

        private const val DB_NAME = "database.db"

        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}