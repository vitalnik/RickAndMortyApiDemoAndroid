package com.example.rickandmorty.data.analytics

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsProvider @Inject constructor() {

    init {
        // analytics provider initialization
    }

    fun log(event: String) {
        Log.d("TAG", "Logging analytics event: $event")
    }

}