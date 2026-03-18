package com.joseleandro.donetask.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joseleandro.donetask.domain.model.Screen
import com.joseleandro.donetask.domain.model.TabScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {

    private val _tabBackStack = MutableStateFlow<List<TabScreen>>(listOf(TabScreen.HomeTabScreen))
    val tabBackStack: StateFlow<List<TabScreen>> = _tabBackStack.asStateFlow()

    private val _screenBackStack = MutableStateFlow<List<Screen>>(listOf(Screen.MainScreen))
    val screenBackStack: StateFlow<List<Screen>> = _screenBackStack.asStateFlow()

    val currentTab: TabScreen
        get() = _tabBackStack.value.last()

    val currentScreen: Screen
        get() = _screenBackStack.value.last()

    fun navigateByScreen(screen: Screen) {
        _screenBackStack.value += screen
    }

    fun onBack(){
        _screenBackStack.value = _screenBackStack.value.dropLast(1)
    }

    fun selectedTab(tab: TabScreen) {
        _tabBackStack.value = _tabBackStack.value.filter { it != tab } + tab
    }

    fun unselectedTab() {
        _tabBackStack.value = _tabBackStack.value.dropLast(1)
    }

}