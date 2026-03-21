package com.joseleandro.donetask.ui.screen.list_details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.ui.screen.home.ListModel
import com.joseleandro.donetask.ui.screen.home.myListsMock
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@Composable
fun ProgressCard(
    modifier: Modifier = Modifier,
    listData: ListModel,
) {
    var progress by remember { mutableFloatStateOf(0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800),
        label = "progress"
    )

    val percentage = (animatedProgress * 100).toInt()

    LaunchedEffect(Unit) {
        progress = if (listData.totalTasks == 0) 0f
        else listData.completedTasks.toFloat() / listData.totalTasks.toFloat()
    }

    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Progresso Atual", color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f))

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    strokeWidth = 10.dp,
                    color = listData.color,
                    trackColor = listData.color.copy(alpha = 0.15f),
                    modifier = Modifier.size(120.dp)
                )

                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onSurface),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "${listData.completedTasks} de ${listData.totalTasks} tarefas concluídas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 1
            )
        }

    }
}

@Preview
@Composable
private fun ProgressCardDarkPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ProgressCard(
            listData = myListsMock.first(),
        )
    }
}

@Preview
@Composable
private fun ProgressCardLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        ProgressCard(
            listData = myListsMock.first(),
        )
    }
}
