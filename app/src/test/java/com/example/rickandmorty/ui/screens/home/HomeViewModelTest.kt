package com.example.rickandmorty.ui.screens.home

import com.example.rickandmorty.app.data.analytics.AnalyticsManager
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    @MockK
    lateinit var analyticsManager: AnalyticsManager

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = HomeViewModel(analyticsManager)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testAnalytics() {

        every { analyticsManager.logEvent(any()) } returns Unit

        viewModel.logCharactersScreenView()

        verify { analyticsManager.logEvent(AnalyticsManager.CHARACTERS_SCREEN_VIEW) }
    }


}