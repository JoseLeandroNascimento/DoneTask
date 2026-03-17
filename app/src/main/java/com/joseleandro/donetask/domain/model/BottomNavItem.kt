package com.joseleandro.donetask.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val screen: TabScreen,
    val label: String,
    val icon: ImageVector
)