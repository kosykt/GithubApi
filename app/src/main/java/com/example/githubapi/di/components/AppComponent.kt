package com.example.githubapi.di.components

import com.example.githubapi.di.modules.singletones.AppModule
import com.example.githubapi.di.modules.singletones.RetrofitModule
import com.example.githubapi.di.modules.singletones.RoomModule
import com.example.githubapi.di.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RoomModule::class,
        RetrofitModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun provideUsersSubcomponent(): UsersSubcomponent
}