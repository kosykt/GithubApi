package com.example.githubapi.ui.usersfragment

import com.example.githubapi.di.components.UsersSubcomponent

interface UsersSubcomponentProvider {

    fun initUsersSubcomponent(): UsersSubcomponent
    fun destroyUsersSubcomponent()
}