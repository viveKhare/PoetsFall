package com.blackonedevs.poetry.poems.presentation

import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.blackonedevs.poetry.poems.core.TestTags
import com.blackonedevs.poetry.poems.di.PoemModule
import com.blackonedevs.poetry.poems.domain.repository.PoemRepository
import com.blackonedevs.poetry.poems.domain.use_cases.GetPoems
import com.blackonedevs.poetry.poems.theme.ComposePoetryTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(PoemModule::class)
class PoemsListScreenTest {
    lateinit var viewModel: PoemViewModel

    @Inject
    lateinit var repository: PoemRepository

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<PoemsListScreen>()

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = PoemViewModel(getPoems = GetPoems(repository = repository))
    }

    @Test
    fun noInternet_retry_isVisible() {
        if (viewModel.state.value.showRetry)
            composeRule.onNodeWithTag(TestTags.Retry, useUnmergedTree = true).assertIsDisplayed()
                .performClick()

    }

    @Test
    fun loader_isVisible() {
        if (viewModel.state.value.isLoading)
            composeRule.onNodeWithTag(TestTags.Loading, useUnmergedTree = true).assertIsDisplayed()
    }
}