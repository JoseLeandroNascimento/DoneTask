package com.joseleandro.donetask.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.joseleandro.donetask.domain.model.TabScreen
import com.joseleandro.donetask.ui.screen.home.HomeScreen
import com.joseleandro.donetask.ui.viewmodel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navigationViewModel: NavigationViewModel = koinViewModel()
    val backStack by navigationViewModel.tabBackStack.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar() {

                TabScreen.tabs.forEach { tab ->
                    NavigationBarItem(
                        label = {
                            Text(
                                text = tab.label
                            )
                        },
                        selected = navigationViewModel.currentTab == tab.screen,
                        onClick = {
                            navigationViewModel.selectedTab(tab = tab.screen)
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = "icone de barra de navegação"
                            )
                        }
                    )
                }

            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = navigationViewModel::onBack,
                entryProvider = entryProvider {
                    entry<TabScreen.HomeTabScreen> {
                        HomeScreen()
                    }
                }
            )
        }
    }

}