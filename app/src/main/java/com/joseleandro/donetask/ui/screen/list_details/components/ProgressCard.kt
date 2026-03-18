package com.joseleandro.donetask.ui.screen.list_details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    collapseFraction: Float
) {
    val progress = if (listData.totalTasks == 0) 0f
    else listData.completedTasks.toFloat() / listData.totalTasks.toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800),
        label = "progress"
    )

    val percentage = (animatedProgress * 100).toInt()
    
    // 🔥 Segundo estágio ativa em 65% do scroll
    val isCollapsed = collapseFraction > 0.65f

    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        tonalElevation = 6.dp
    ) {
        AnimatedContent(
            targetState = isCollapsed,
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
            transitionSpec = {
                // Usando scale + fade para manter o texto "preso" ao círculo durante a mudança
                (fadeIn(tween(400)) + scaleIn(initialScale = 0.8f))
                    .togetherWith(fadeOut(tween(400)) + scaleOut(targetScale = 0.8f))
                    .using(SizeTransform(clip = true))
            },
            label = "card_stage_transition"
        ) { collapsed ->
            if (collapsed) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Progresso da lista",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 1
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // 🔥 Agora o círculo e o texto estão na mesma estrutura em ambos os estados
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { animatedProgress },
                                strokeWidth = 3.dp,
                                color = listData.color,
                                trackColor = listData.color.copy(alpha = 0.15f),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = listData.color
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Progresso Atual", color = Color.Gray)

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
                            style = MaterialTheme.typography.headlineSmall,
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
    }
}

@Preview
@Composable
private fun ProgressCardDarkPreview() {
    DoneTaskTheme (
        dynamicColor = false,
        darkTheme = true
    ) {
        ProgressCard(
            listData = myListsMock.first(),
            collapseFraction = 0f
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
            collapseFraction = 1f
        )
    }
}
