package com.joseleandro.donetask.domain.model

import java.util.UUID

sealed interface Screen {

    data object MainScreen : Screen

    data class ListDetailScreen(val listId: UUID) : Screen

    data object TaskCreateScreen : Screen

}