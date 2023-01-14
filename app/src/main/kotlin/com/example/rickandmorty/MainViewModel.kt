package com.example.rickandmorty

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmorty.app.network.dto.CharacterDTO
import com.example.rickandmorty.app.network.dto.EpisodeDTO
import com.example.rickandmorty.app.network.dto.LocationDTO
import com.example.rickandmorty.app.network.repositories.CharactersRepository
import com.example.rickandmorty.app.network.repositories.EpisodesRepository
import com.example.rickandmorty.app.network.repositories.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val charactersRepo: CharactersRepository,
    private val locationsRepo: LocationsRepository,
    private val episodesRepo: EpisodesRepository,
) :
    ViewModel() {

    val characterNameSearchValue = mutableStateOf("")

    val charactersPagingData: Flow<PagingData<CharacterDTO>> = Pager(
        pagingSourceFactory = {
            CustomPagingSource(dataProvider = {

                val searchQuery =
                    if (characterNameSearchValue.value.isNotEmpty()) "&name=${characterNameSearchValue.value}" else ""

                charactersRepo.getCharacters(it, searchQuery)
            })
        },
        config = PagingConfig(pageSize = 20)
    ).flow.cachedIn(viewModelScope)

    val locationNameSearchValue = mutableStateOf("")

    val locationsPagingData: Flow<PagingData<LocationDTO>> = Pager(
        pagingSourceFactory = {
            CustomPagingSource(dataProvider = {

                val searchQuery =
                    if (locationNameSearchValue.value.isNotEmpty()) "&name=${locationNameSearchValue.value}" else ""

                locationsRepo.getLocations(it, searchQuery)
            })
        },
        config = PagingConfig(pageSize = 20)
    ).flow.cachedIn(viewModelScope)

    val episodesPagingData: Flow<PagingData<EpisodeDTO>> = Pager(
        pagingSourceFactory = {
            CustomPagingSource(dataProvider = {
                episodesRepo.getEpisodes(it)
            })
        },
        config = PagingConfig(pageSize = 20)
    ).flow.cachedIn(viewModelScope)

}

class CustomPagingSource<T : Any>(private val dataProvider: suspend (pageNumber: Int) -> List<T>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val pageNumber = params.key ?: 1
            val response = dataProvider.invoke(pageNumber)
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey =
                if (response.isNotEmpty() && response.count() == 20) pageNumber + 1 else null

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
