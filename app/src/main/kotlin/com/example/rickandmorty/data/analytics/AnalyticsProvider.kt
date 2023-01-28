package com.example.rickandmorty.data.analytics

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsProvider @Inject constructor() {

    init {
        // analytics provider initialization
    }

    fun log(event: String) {
        Timber.d("Logging analytics event: $event")
    }

}