package com.joseleandro.donetask.ui.screen.task_create

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.OutlinedFlag
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.screen.home.ListModel
import com.joseleandro.donetask.ui.screen.home.myListsMock
import com.joseleandro.donetask.ui.screen.task_create.components.TaskCreateInputTextSubtask
import com.joseleandro.donetask.ui.theme.DoneTaskTheme
import com.joseleandro.donetask.ui.viewmodel.NavigationViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

data class SubtaskModel(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    val checked: Boolean
)

@Composable
fun TaskCreateScreen() {

    val navigationViewModel: NavigationViewModel = koinViewModel()

    TaskCreateScreen(
        onBack = navigationViewModel::onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(
    onBack: () -> Unit
) {

    var activeAlarm by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableIntStateOf(1) } // 0: Baixa, 1: Média, 2: Alta
    var selectedList by remember { mutableStateOf(myListsMock.first()) }

    val subtasks = remember { mutableStateListOf<SubtaskModel>() }
    var nameSubtask by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.nova_tarefa),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.voltar)
                        )
                    }
                },
            )
        },
        bottomBar = {
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
                            Text(stringResource(R.string.cancelar))
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            contentPadding = PaddingValues(vertical = 14.dp),
                            onClick = {}
                        ) {
                            Text(stringResource(R.string.criar_tarefa))
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
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Selecionar Lista
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TaskCreateLabelSection(label = "Lista")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(myListsMock) { list ->
                        ListSelectorItem(
                            list = list,
                            isSelected = selectedList == list,
                            onClick = { selectedList = list }
                        )
                    }
                }
            }


            // Seção de Inputs
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TaskCreateLabelSection(label = stringResource(R.string.informa_es))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("O que precisa ser feito?") },
                    placeholder = { Text(stringResource(R.string.ex_comprar_leite)) },
                    value = "",
                    onValueChange = {},
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                        focusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    ),
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
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                        focusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    ),
                    placeholder = { Text("Adicione detalhes extras aqui...") },
                    value = "",
                    minLines = 3,
                    onValueChange = {},
                    shape = MaterialTheme.shapes.medium
                )
            }

            // Seção de Categoria
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskCreateLabelSection(label = stringResource(R.string.categoria))
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

            // 🔥 Seção de Prioridade
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TaskCreateLabelSection(label = "Prioridade")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PriorityItem(
                        modifier = Modifier.weight(1f),
                        label = "Baixa",
                        color = Color(0xFF2196F3), // Azul
                        isSelected = selectedPriority == 0,
                        onClick = { selectedPriority = 0 }
                    )
                    PriorityItem(
                        modifier = Modifier.weight(1f),
                        label = "Média",
                        color = Color(0xFFFFC107), // Amarelo
                        isSelected = selectedPriority == 1,
                        onClick = { selectedPriority = 1 }
                    )
                    PriorityItem(
                        modifier = Modifier.weight(1f),
                        label = "Alta",
                        color = Color(0xFFF44336), // Vermelho
                        isSelected = selectedPriority == 2,
                        onClick = { selectedPriority = 2 }
                    )
                }
            }

            // Seção de Alerta
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TaskCreateLabelSection(label = stringResource(R.string.lembrete))
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
                                    text = if (activeAlarm) stringResource(R.string.alerta_ativado) else stringResource(
                                        R.string.alerta_desativado
                                    ),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = stringResource(R.string.notificar_sobre_esta_tarefa),
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
                                TaskReminderRow(
                                    Icons.Default.CalendarToday,
                                    stringResource(R.string.data),
                                    "Hoje"
                                )
                                TaskReminderRow(
                                    Icons.Default.AccessTimeFilled,
                                    stringResource(R.string.horario),
                                    "09:00"
                                )
                                TaskReminderRow(
                                    Icons.Default.Repeat,
                                    stringResource(R.string.repetir),
                                    "Nunca"
                                )
                            }
                        }
                    }
                }
            }

            // Seção de Subtarefas
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TaskCreateLabelSection(label = stringResource(R.string.subtarefas))
                CardSubtasks() {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            subtasks.forEachIndexed { index, subtask ->
                                TaskCreateInputTextSubtask(
                                    label = { Text("Subtarefa ${index + 1}") },
                                    value = subtask.name,
                                    onValueChange = { newName ->
                                        subtasks[index] = subtask.copy(name = newName)
                                    },
                                    checked = subtask.checked,
                                    enabledChecked = true,
                                    onCheckedChange = {
                                        subtasks[index] = subtask.copy(checked = !subtask.checked)
                                    },
                                    trailingIcon =  Icons.Default.Close,
                                    onTrailingIconClick = {
                                        subtasks.removeAt(index)
                                    }
                                )
                            }
                        }

                        TaskCreateInputTextSubtask(
                            label = {
                                Text(
                                    "Adicione subtarefas...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                )
                            },
                            value = nameSubtask,
                            onValueChange = {
                                nameSubtask = it
                            },
                            checked = false,
                            enabledChecked = false,
                            onCheckedChange = {},
                            trailingIcon = if (nameSubtask.isEmpty()) Icons.Default.Add else Icons.Default.Check,
                            onTrailingIconClick = {
                                if (nameSubtask.isNotBlank()) {
                                    subtasks.add(
                                        SubtaskModel(
                                            name = nameSubtask,
                                            checked = false
                                        )
                                    )
                                    nameSubtask = ""
                                }
                            }
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
private fun ListSelectorItem(
    list: ListModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderWidth = 1.dp
    val borderColor by animateColorAsState(
        if (isSelected) list.color else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
        label = "borderColor"
    )
    val containerSize = 46.dp

    Column(
        modifier = Modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = if (isSelected) list.color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
            border = BorderStroke(borderWidth, borderColor),
            modifier = Modifier.size(containerSize),
            tonalElevation = if (isSelected) 4.dp else 0.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = list.icon,
                    contentDescription = null,
                    tint = if (isSelected) list.color else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(if (isSelected) 28.dp else 24.dp)
                )
            }
        }
        Text(
            text = list.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.7f
                )
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CardSubtasks(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val borderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .drawBehind({
                val strokeWidth = 2.dp.toPx()
                val dashWidth = 10.dp.toPx()
                val gapWidth = 6.dp.toPx()

                drawRoundRect(
                    color = borderColor,
                    size = size,
                    style = Stroke(
                        width = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(dashWidth, gapWidth),
                            0f
                        )
                    ),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.medium
    ) {
        content()
    }
}

@Composable
private fun PriorityItem(
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) color.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
        animationSpec = tween(300)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) color else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        animationSpec = tween(300)
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) color else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
        animationSpec = tween(300)
    )

    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.OutlinedFlag,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = contentColor
                )
            )
        }
    }
}

@Composable
private fun TaskReminderRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
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
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f),
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
        TaskCreateScreen(
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCreateScreenLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        TaskCreateScreen(
            onBack = {}
        )
    }
}
