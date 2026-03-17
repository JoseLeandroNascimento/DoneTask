package com.joseleandro.donetask.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joseleandro.donetask.domain.model.TabScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {

    private val _tabBackStack = MutableStateFlow<List<TabScreen>>(listOf(TabScreen.HomeTabScreen))
    val tabBackStack: StateFlow<List<TabScreen>> = _tabBackStack.asStateFlow()

    val currentTab: TabScreen
        get() = _tabBackStack.value.last()

    fun selectedTab(tab: TabScreen) {
        _tabBackStack.value = _tabBackStack.value.filter { it != tab } + tab
    }

    fun onBack(){
        _tabBackStack.value = _tabBackStack.value.dropLast(1)
    }

}