package com.example.rickandmorty.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.rickandmorty.ui.screens.home.HomeScreen
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreenContentTest() {
        composeTestRule.setContent {
            RickAndMortyTheme {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithText("Characters").assertIsDisplayed()
        composeTestRule.onNodeWithText("Locations").assertIsDisplayed()
        composeTestRule.onNodeWithText("Episodes").assertIsDisplayed()
    }
}