package com.example.gamecatalog.di

import com.example.gamecatalog.data.repository.*
import com.example.gamecatalog.domain.usecase.GetGameDetailUseCase
import com.example.gamecatalog.domain.usecase.GetGamesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RealRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FakeRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RealUseCase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FakeUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    @RealRepo
    abstract fun bindRealRepository(impl: RealGameRepository): GameRepository

    @Binds
    @Singleton
    @FakeRepo
    abstract fun bindFakeRepository(impl: FakeGameRepository): GameRepository

    @Binds
    @Singleton
    abstract fun bindGameDetailRepository(impl: RealGameDetailRepository): GameDetailRepository

    companion object {

        @Provides
        @Singleton
        @RealUseCase
        fun provideRealUseCase(@RealRepo repository: GameRepository): GetGamesUseCase =
            GetGamesUseCase(repository)

        @Provides
        @Singleton
        @FakeUseCase
        fun provideFakeUseCase(@FakeRepo repository: GameRepository): GetGamesUseCase =
            GetGamesUseCase(repository)

        @Provides
        @Singleton
        fun provideGetGameDetailUseCase(repository: GameDetailRepository): GetGameDetailUseCase =
            GetGameDetailUseCase(repository)
    }
}