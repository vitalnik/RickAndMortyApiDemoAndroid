package com.example.rickandmorty.data.analytics

import javax.inject.Inject
import javax.inject.Singleton

/**
 * A simple manager class for logging analytics using injected analytics provider
 */
@Singleton
class AnalyticsManager @Inject constructor(private val analyticsProvider: AnalyticsProvider) {

    fun logEvent(event: String) {
        analyticsProvider.log(event)
    }

    companion object {
        const val CHARACTERS_SCREEN_VIEW = "CHARACTERS_SCREEN_VIEW"
        const val LOCATIONS_SCREEN_VIEW = "LOCATIONS_SCREEN_VIEW"
        const val EPISODES_SCREEN_VIEW = "EPISODES_SCREEN_VIEW"
    }

}