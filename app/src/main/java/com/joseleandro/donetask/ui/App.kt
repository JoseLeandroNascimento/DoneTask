package com.joseleandro.donetask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.joseleandro.donetask.domain.model.Screen
import com.joseleandro.donetask.ui.screen.list_details.ListDetailScreen
import com.joseleandro.donetask.ui.viewmodel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(modifier: Modifier = Modifier) {

    val navigationViewModel: NavigationViewModel = koinViewModel()
    val backStack by navigationViewModel.screenBackStack.collectAsStateWithLifecycle()

    NavDisplay(
        backStack = backStack,
        onBack = navigationViewModel::onBack,
        entryProvider = entryProvider {

            entry<Screen.MainScreen> {
                MainScreen()
            }

            entry<Screen.ListDetailScreen> {
                ListDetailScreen(listId = it.listId)
            }
        }
    )

}