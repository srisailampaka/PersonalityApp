package com.personalitytestapp.di

import com.personalitytestapp.ui.personality.PersonalityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainModule {
    @ContributesAndroidInjector
    internal abstract fun contributePersonalityFragmentInjector(): PersonalityFragment
}