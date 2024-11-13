package com.example.bootapp.di

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.bootapp.data.BootDao
import com.example.bootapp.data.BootDatabase
import com.example.bootapp.data.BootRepositoryImpl
import com.example.bootapp.domain.BootRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): BootDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BootDatabase::class.java,
            "Boot.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBootDao(database: BootDatabase): BootDao = database.bootDao()
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindBootRepository(
        bootRepositoryImpl: BootRepositoryImpl
    ): BootRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal class SystemModule {
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideHiltWorkerFactory(
        workerFactory: HiltWorkerFactory
    ): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}