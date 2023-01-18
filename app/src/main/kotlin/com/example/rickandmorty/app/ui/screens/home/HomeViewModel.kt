package com.example.rickandmorty.app.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.analytics.AnalyticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val analyticsManager: AnalyticsManager
) : ViewModel() {

    fun logCharactersScreenView() {
        analyticsManager.logEvent(AnalyticsManager.CHARACTERS_SCREEN_VIEW)
    }

    fun logLocationsScreenView() {
        analyticsManager.logEvent(AnalyticsManager.LOCATIONS_SCREEN_VIEW)
    }

    fun logEpisodesScreenView() {
        analyticsManager.logEvent(AnalyticsManager.EPISODES_SCREEN_VIEW)
    }

}
