package com.joseleandro.donetask.ui.screen.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@Composable
fun HomeListItemCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    colorRepresentation: Color,
    totalTasks: Int,
    completedTasks: Int = 0
) {

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = .1f)
        ),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BoxIconListItemCard(
                icon = icon,
                colorRepresentation = colorRepresentation
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = stringResource(R.string.progresso_tarefas, completedTasks, totalTasks),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun BoxIconListItemCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    colorRepresentation: Color
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                color = colorRepresentation.copy(alpha = .5f),
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = stringResource(R.string.icone_de_representacao_da_lista),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun HomeListItemCardDarkPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        HomeListItemCard(
            title = "Lista de casa",
            icon = Icons.Default.Build,
            colorRepresentation = Color.Red,
            totalTasks = 10,
            completedTasks = 5
        )
    }
}

@Preview
@Composable
private fun HomeListItemCardLisghtPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        HomeListItemCard(
            title = "Lista de casa",
            icon = Icons.Default.Build,
            colorRepresentation = Color.Red,
            totalTasks = 10,
            completedTasks = 5
        )
    }
}