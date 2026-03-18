package com.joseleandro.donetask.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.domain.model.Screen
import com.joseleandro.donetask.ui.screen.home.components.HomeListItemCard
import com.joseleandro.donetask.ui.screen.home.components.HomeScreenTopBar
import com.joseleandro.donetask.ui.screen.list_create.ListCreateBottomSheet
import com.joseleandro.donetask.ui.theme.DoneTaskTheme
import com.joseleandro.donetask.ui.viewmodel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

data class ListModel(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val color: Color,
    val icon: ImageVector,
    val totalTasks: Int,
    val completedTasks: Int
)

val myListsMock: List<ListModel> = listOf(

    ListModel(
        name = "Casa",
        color = Color(0xFFE57373),
        icon = Icons.Default.Home,
        totalTasks = 10,
        completedTasks = 5
    ),

    ListModel(
        name = "Trabalho",
        color = Color(0xFF64B5F6),
        icon = Icons.Default.Work,
        totalTasks = 8,
        completedTasks = 3
    ),

    ListModel(
        name = "Estudos",
        color = Color(0xFF81C784),
        icon = Icons.Default.School,
        totalTasks = 12,
        completedTasks = 9
    ),

    ListModel(
        name = "Academia",
        color = Color(0xFFFFB74D),
        icon = Icons.Default.FitnessCenter,
        totalTasks = 6,
        completedTasks = 2
    ),

    ListModel(
        name = "Compras",
        color = Color(0xFFBA68C8),
        icon = Icons.Default.ShoppingCart,
        totalTasks = 15,
        completedTasks = 10
    ),

    ListModel(
        name = "Projetos",
        color = Color(0xFF4DB6AC),
        icon = Icons.Default.Code,
        totalTasks = 5,
        completedTasks = 1
    )
)

@Composable
fun HomeScreen() {

    val navigationViewModel: NavigationViewModel = koinViewModel()

    HomeScreen(
        onNavigate = navigationViewModel::navigateByScreen
    )
}

@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit
) {

    var createListBottomSheetVisible by remember { mutableStateOf(false) }


    if (createListBottomSheetVisible) {
        ListCreateBottomSheet(
            onDismissRequest = {
                createListBottomSheetVisible = false
            }
        )
    }

    Scaffold(
        topBar = {
            HomeScreenTopBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    createListBottomSheetVisible = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_list)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    bottom = 80.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = myListsMock, key = { listItem -> listItem.id }) { listItem ->
                    HomeListItemCard(
                        title = listItem.name,
                        icon = listItem.icon,
                        colorRepresentation = listItem.color,
                        totalTasks = listItem.totalTasks,
                        completedTasks = listItem.completedTasks,
                        onClick = {
                            onNavigate(Screen.ListDetailScreen(listId = listItem.id))
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenDarkPreview() {

    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        HomeScreen(
            onNavigate = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        HomeScreen(
            onNavigate = {}
        )
    }
}