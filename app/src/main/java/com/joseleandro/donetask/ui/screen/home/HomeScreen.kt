package com.joseleandro.donetask.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.screen.home.components.HomeListItemCard
import com.joseleandro.donetask.ui.screen.home.components.HomeScreenTopBar
import com.joseleandro.donetask.ui.screen.list_create.ListCreateBottomSheet
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@Composable
fun HomeScreen() {

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
                items(count = 10) {
                    HomeListItemCard(
                        title = "Lista de casa",
                        icon = Icons.Default.Build,
                        colorRepresentation = Color.Red,
                        totalTasks = 10,
                        completedTasks = 5
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
        HomeScreen()
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        HomeScreen()
    }
}