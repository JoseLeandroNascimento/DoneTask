package com.joseleandro.donetask.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import kotlinx.serialization.Serializable

sealed interface TabScreen {

    @Serializable
    data object HomeTabScreen : TabScreen

    @Serializable
    data object ProgressTabScreen : TabScreen

    @Serializable
    data object ReminderTabScreen : TabScreen

    @Serializable
    data object ProfileTabScreen : TabScreen

    companion object {

        val tabs = listOf(
            BottomNavItem(
                screen = TabScreen.HomeTabScreen,
                label = "Início",
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                screen = TabScreen.ReminderTabScreen,
                label = "Lembretes",
                icon = Icons.Default.Timer
            ),
            BottomNavItem(
                screen = TabScreen.ProgressTabScreen,
                label = "Progresso",
                icon = Icons.Default.BarChart
            ),
            BottomNavItem(
                screen = TabScreen.ProfileTabScreen,
                label = "Perfil",
                icon = Icons.Default.Person
            )
        )
    }
}