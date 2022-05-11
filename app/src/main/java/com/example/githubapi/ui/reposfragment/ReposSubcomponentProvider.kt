package com.example.githubapi.ui.reposfragment

import com.example.githubapi.di.components.ReposSubcomponent

interface ReposSubcomponentProvider {

    fun initReposSubcomponent(): ReposSubcomponent
    fun destroyReposSubcomponent()
}