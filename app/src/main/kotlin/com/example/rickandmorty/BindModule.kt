package com.example.rickandmorty

import com.example.rickandmorty.app.data.network.repositories.CharacterRepositoryImpl
import com.example.rickandmorty.app.data.network.repositories.EpisodeRepositoryImpl
import com.example.rickandmorty.app.data.network.repositories.LocationsRepositoryImpl
import com.example.rickandmorty.app.domain.repositories.CharacterRepository
import com.example.rickandmorty.app.domain.repositories.EpisodeRepository
import com.example.rickandmorty.app.domain.repositories.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindCharacterRepository(repo: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun bindLocationRepository(repo: LocationsRepositoryImpl): LocationRepository

    @Binds
    abstract fun bindEpisodeRepository(repo: EpisodeRepositoryImpl): EpisodeRepository


}
