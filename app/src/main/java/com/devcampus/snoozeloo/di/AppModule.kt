package com.devcampus.snoozeloo.di

import android.content.Context
import com.devcampus.snoozeloo.usecases.SetAlarmUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSetAlarmUseCase(@ApplicationContext context: Context): SetAlarmUseCase{
        return SetAlarmUseCase(context)
    }
}