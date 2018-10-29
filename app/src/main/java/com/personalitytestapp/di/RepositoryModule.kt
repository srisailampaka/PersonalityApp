package com.personalitytestapp.di

import android.content.Context
import com.personalitytestapp.data.PersonalityService
import com.personalitytestapp.data.repository.PersonalityRepository
import com.personalitytestapp.data.repository.PersonalityRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideNewsRepo(personalityServices: PersonalityService,
                        context: Context): PersonalityRepository = PersonalityRepositoryImpl(personalityServices, context)
}