package com.example.rickandmorty.app

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.EpisodeModel
import com.example.rickandmorty.domain.models.LocationModel
import com.example.rickandmorty.domain.usecases.character.GetCharactersUseCase
import com.example.rickandmorty.domain.usecases.episode.GetEpisodesUseCase
import com.example.rickandmorty.domain.usecases.location.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val FIRST_PAGE_INDEX = 1
const val PAGE_SIZE = 20

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase,
) : ViewModel() {

    val characterNameSearchValue = mutableStateOf("")

    val charactersPagingData: Flow<PagingData<CharacterModel>> = Pager(
        pagingSourceFactory = {
            CustomPagingSource(dataProvider = {

                val searchQuery =
                    if (characterNameSearchValue.value.isNotEmpty()) "&name=${characterNameSearchValue.value}" else ""

                getCharactersUseCase(it, searchQuery)
            })
        },
        config = PagingConfig(pageSize = PAGE_SIZE)
    ).flow.cachedIn(viewModelScope)

    val locationNameSearchValue = mutableStateOf("")

    val locationsPagingData: Flow<PagingData<LocationModel>> = Pager(
        pagingSourceFactory = {
            CustomPagingSource(dataProvider = {

                val searchQuery =
                    if (locationNameSearchValue.value.isNotEmpty()) "&name=${locationNameSearchValue.value}" else ""

                getLocationsUseCase(it, searchQuery)
            })
        },
        config = PagingConfig(pageSize = PAGE_SIZE)
    ).flow.cachedIn(viewModelScope)

    val episodeSearchValue = mutableStateOf("")

    private val seasons = mutableStateListOf<Int>()

    val episodesPagingData: Flow<PagingData<EpisodeModel>> = Pager(
        pagingSourceFactory = {
            CustomPagingSource(dataProvider = {

                val searchQuery =
                    if (episodeSearchValue.value.isNotEmpty()) "&episode=${episodeSearchValue.value}" else ""

                val allEpisodes = getEpisodesUseCase(it, searchQuery)

                // collecting unique season numbers
                allEpisodes.forEach { episode ->
                    if (!seasons.contains(episode.seasonNumber)) {
                        seasons.add(episode.seasonNumber)
                    }
                }

                allEpisodes
            })
        },
        config = PagingConfig(pageSize = PAGE_SIZE)
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
            val pageNumber = params.key ?: FIRST_PAGE_INDEX
            val response = dataProvider.invoke(pageNumber)
            val prevKey = if (pageNumber > FIRST_PAGE_INDEX) pageNumber - 1 else null
            val nextKey =
                if (response.isNotEmpty() && response.count() == PAGE_SIZE) pageNumber + 1 else null

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
