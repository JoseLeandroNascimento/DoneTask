package com.joseleandro.donetask.ui.screen.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.minhas_listas),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.icone_menu)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.mais_opcoes)
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeScreenTopBarDarkPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        HomeScreenTopBar()
    }
}

@Preview
@Composable
private fun HomeScreenTopBarLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        HomeScreenTopBar()
    }
}