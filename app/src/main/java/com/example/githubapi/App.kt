package com.example.githubapi

import android.app.Application
import com.example.githubapi.di.components.AppComponent
import com.example.githubapi.di.components.DaggerAppComponent
import com.example.githubapi.di.components.ReposSubcomponent
import com.example.githubapi.di.components.UsersSubcomponent
import com.example.githubapi.di.modules.singletones.AppModule
import com.example.githubapi.ui.reposfragment.ReposSubcomponentProvider
import com.example.githubapi.ui.usersfragment.UsersSubcomponentProvider

class App : Application(), UsersSubcomponentProvider, ReposSubcomponentProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private var usersSubcomponent: UsersSubcomponent? = null
    private var reposSubcomponent: ReposSubcomponent? = null

    override fun initUsersSubcomponent() = appComponent
        .provideUsersSubcomponent()
        .also {
            if (usersSubcomponent == null) {
                usersSubcomponent = it
            }
        }

    override fun destroyUsersSubcomponent() {
        usersSubcomponent = null
    }

    override fun initReposSubcomponent() = appComponent
        .provideUsersSubcomponent()
        .provideReposSubcomponent()
        .also {
            if (reposSubcomponent == null) {
                reposSubcomponent = it
            }
        }

    override fun destroyReposSubcomponent() {
        reposSubcomponent = null
    }
}