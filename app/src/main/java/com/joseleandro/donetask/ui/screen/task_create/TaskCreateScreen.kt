package com.joseleandro.donetask.ui.screen.task_create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.screen.task_create.components.TaskCreateInputTextSubtask
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(modifier: Modifier = Modifier) {

    var activeAlarm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nova Tarefa",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.voltar)
                        )
                    }
                }
            )
        },
        bottomBar = {
            // 🔥 Botões fixos no rodapé para melhor UX
            Surface(
                modifier = Modifier.navigationBarsPadding(),
                tonalElevation = 2.dp,
                shadowElevation = 8.dp
            ) {
                Column {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            contentPadding = PaddingValues(vertical = 14.dp),
                            onClick = {}
                        ) {
                            Text("Cancelar")
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            contentPadding = PaddingValues(vertical = 14.dp),
                            onClick = {}
                        ) {
                            Text("Criar Tarefa")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(state = rememberScrollState())
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Seção de Categoria
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskCreateLabelSection(label = "Categoria")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(count = 5) {
                        FilterChip(
                            label = { Text("Trabalho") },
                            selected = it == 0,
                            onClick = {}
                        )
                    }
                    item {
                        AssistChip(
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        Icons.Default.AddCircleOutline,
                                        null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text("Novo")
                                }
                            },
                            onClick = {}
                        )
                    }
                }
            }

            // Seção de Inputs
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TaskCreateLabelSection(label = "Informações")
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("O que precisa ser feito?") },
                    placeholder = { Text("Ex: Comprar leite") },
                    value = "",
                    onValueChange = {},
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.Notes,
                                null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text("Anotações")
                        }
                    },
                    placeholder = { Text("Adicione detalhes extras aqui...") },
                    value = "",
                    minLines = 3,
                    onValueChange = {},
                    shape = MaterialTheme.shapes.medium
                )
            }

            // Seção de Alerta
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TaskCreateLabelSection(label = "Lembrete")
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    tonalElevation = 1.dp,
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer.copy(
                                            alpha = 0.4f
                                        ), MaterialTheme.shapes.medium
                                    )
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = if (activeAlarm) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 12.dp)
                            ) {
                                Text(
                                    text = if (activeAlarm) "Alerta Ativado" else "Alerta Desativado",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Notificar sobre esta tarefa",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Switch(checked = activeAlarm, onCheckedChange = { activeAlarm = it })
                        }

                        AnimatedVisibility(
                            visible = activeAlarm,
                            enter = fadeIn(animationSpec = tween(300)) + 
                                    expandVertically(
                                        animationSpec = tween(400, easing = FastOutSlowInEasing),
                                        expandFrom = Alignment.Top
                                    ),
                            exit = fadeOut(animationSpec = tween(200)) + 
                                   shrinkVertically(
                                       animationSpec = tween(400, easing = FastOutSlowInEasing),
                                       shrinkTowards = Alignment.Top
                                   )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(
                                        alpha = 0.5f
                                    )
                                )
                                TaskReminderRow(Icons.Default.CalendarToday, "Data", "Hoje")
                                TaskReminderRow(Icons.Default.AccessTimeFilled, "Horário", "09:00")
                                TaskReminderRow(Icons.Default.Repeat, "Repetir", "Nunca")
                            }
                        }
                    }
                }
            }

            // Seção de Subtarefas
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TaskCreateLabelSection(label = "Subtarefas")
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        TaskCreateInputTextSubtask(
                            label = {
                                Text(
                                    "Adicione subtarefas...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                )
                            },
                            value = "",
                            onValueChange = {},
                            checked = false,
                            onCheckedChange = {},
                            onAddNewSubtask = {}
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

@Composable
private fun TaskReminderRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Icon(
            Icons.Default.ChevronRight,
            null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun TaskCreateLabelSection(label: String) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        ),
        text = label
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskCreateScreenDarkPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        TaskCreateScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCreateScreenLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        TaskCreateScreen()
    }
}
