package com.example.githubapi.di.modules.singletones

import android.app.Application
import com.example.githubapi.data.DatabaseRepository
import com.example.githubapi.data.DomainRepositoryImpl
import com.example.githubapi.data.NetworkRepository
import com.example.githubapi.data.database.AppDatabase
import com.example.githubapi.data.database.DatabaseRepositoryImpl
import com.example.githubapi.data.network.NetworkRepositoryImpl
import com.example.githubapi.data.network.RetrofitService
import com.example.githubapi.domain.DomainRepository
import com.example.githubapi.utils.NetworkObserver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideNetworkObserver(): NetworkObserver{
        return NetworkObserver(application)
    }

    @Singleton
    @Provides
    fun provideDomainRepository(
        network: NetworkRepository,
        database: DatabaseRepository
    ): DomainRepository {
        return DomainRepositoryImpl(network, database)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(retrofitService: RetrofitService): NetworkRepository {
        return NetworkRepositoryImpl(retrofitService)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(db: AppDatabase): DatabaseRepository {
        return DatabaseRepositoryImpl(db)
    }
}