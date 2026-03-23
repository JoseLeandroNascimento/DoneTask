package com.joseleandro.donetask.ui.screen.agenda

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.ui.theme.DoneTaskTheme
import com.joseleandro.donetask.ui.theme.Purple40
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import java.util.UUID

data class TaskAlertModel(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val date: String,
    val time: String,
    val nameList: String,
    val colorList: Color,
    val iconList: ImageVector
)

val taskAlertModel = listOf(
    TaskAlertModel(
        name = "Lavar o carro",
        date = "12/03/2026",
        time = "07:30",
        nameList = "Casa",
        colorList = Color.Red,
        iconList = Icons.Default.Home
    ),
    TaskAlertModel(
        name = "Reunião com equipe",
        date = "12/03/2026",
        time = "09:00",
        nameList = "Trabalho",
        colorList = Color.Blue,
        iconList = Icons.Default.Work
    ),
    TaskAlertModel(
        name = "Academia",
        date = "12/03/2026",
        time = "18:00",
        nameList = "Saúde",
        colorList = Color.Green,
        iconList = Icons.Default.FitnessCenter
    ),
    TaskAlertModel(
        name = "Estudar Kotlin",
        date = "13/03/2026",
        time = "20:00",
        nameList = "Estudos",
        colorList = Color.Magenta,
        iconList = Icons.Default.School
    ),
    TaskAlertModel(
        name = "Ir ao mercado",
        date = "13/03/2026",
        time = "10:30",
        nameList = "Casa",
        colorList = Color.Yellow,
        iconList = Icons.Default.ShoppingCart
    ),
    TaskAlertModel(
        name = "Consulta médica",
        date = "14/03/2026",
        time = "15:00",
        nameList = "Saúde",
        colorList = Color.Cyan,
        iconList = Icons.Default.LocalHospital
    ),
    TaskAlertModel(
        name = "Pagar contas",
        date = "14/03/2026",
        time = "08:00",
        nameList = "Financeiro",
        colorList = Color.Gray,
        iconList = Icons.Default.AttachMoney
    ),
    TaskAlertModel(
        name = "Passear com o cachorro",
        date = "15/03/2026",
        time = "06:30",
        nameList = "Pessoal",
        colorList = Color.Green,
        iconList = Icons.Default.Pets
    ),
    TaskAlertModel(
        name = "Ler um livro",
        date = "15/03/2026",
        time = "21:00",
        nameList = "Lazer",
        colorList = Color.Blue,
        iconList = Icons.AutoMirrored.Filled.MenuBook
    ),
    TaskAlertModel(
        name = "Organizar tarefas da semana",
        date = "16/03/2026",
        time = "07:00",
        nameList = "Planejamento",
        colorList = Color.Red,
        iconList = Icons.AutoMirrored.Filled.EventNote
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen() {
    val today = remember { LocalDate.now() }
    val scope = rememberCoroutineScope()
    val calendarState = rememberWeekCalendarState(
        startDate = today.plusWeeks(-100),
        endDate = today.plusWeeks(100),
    )

    val monthName by remember {
        derivedStateOf {
            calendarState.firstVisibleWeek.days.first().date.month.getDisplayName(
                TextStyle.FULL, Locale.getDefault()
            ).replaceFirstChar { it.uppercase() }
        }
    }

    val fullYearName by remember {
        derivedStateOf { calendarState.firstVisibleWeek.days.first().date.year.toString() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "$monthName $fullYearName",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            calendarState.animateScrollToWeek(
                                calendarState.lastVisibleWeek.days.last().date.plusWeeks(-1)
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "mês anterior"
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            calendarState.animateScrollToWeek(
                                calendarState.firstVisibleWeek.days.first().date.plusWeeks(1)
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "proxímo mês"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AgendaHeader(calendarState = calendarState, today = today)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                itemsIndexed(
                    items = taskAlertModel,
                    key = { _, taskAlert -> taskAlert.id }) { _, taskAlert ->
                    AgendaCardEventItem(
                        name = taskAlert.name,
                        time = taskAlert.time,
                        colorList = taskAlert.colorList,
                        nameList = taskAlert.nameList,
                        iconList = taskAlert.iconList
                    )
                }
            }
        }
    }
}

@Composable
fun AgendaCardEventItem(
    modifier: Modifier = Modifier,
    name: String,
    time: String,
    colorList: Color,
    nameList: String,
    iconList: ImageVector
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top
    ) {
        // Horário alinhado à esquerda
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        // Seção da Timeline (Linha e Ponto)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        CircleShape
                    )
                    .offset(y = 16.dp) // Alinha o ponto com o texto do horário
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
            )
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = iconList,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = colorList
                    )
                    Text(
                        text = nameList,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AgendaHeader(
    modifier: Modifier = Modifier,
    calendarState: WeekCalendarState,
    today: LocalDate,
) {
    val colorDaySelected = MaterialTheme.colorScheme.secondaryFixed
    Surface(modifier = Modifier.fillMaxWidth(), color = Purple40) {
        WeekCalendar(
            modifier = modifier.fillMaxWidth(),
            state = calendarState,
            dayContent = { weekDay ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = if (weekDay.date == today) colorDaySelected else Color.Transparent,
                                strokeWidth = 8.dp.toPx(),
                                cap = StrokeCap.Round,
                                pathEffect = PathEffect.cornerPathEffect(radius = 8.dp.toPx()),
                                start = Offset(x = 0f, y = size.height),
                                end = Offset(x = size.width, y = size.height)
                            )
                        }
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = weekDay.date.dayOfWeek.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        ).replace(".", "").replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(
                                alpha = 0.7f
                            )
                        )
                    )
                    Text(
                        text = weekDay.date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AgendaScreenLightPreview() {
    DoneTaskTheme(dynamicColor = false, darkTheme = false) { AgendaScreen() }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AgendaScreenDarkPreview() {
    DoneTaskTheme(dynamicColor = false, darkTheme = true) { AgendaScreen() }
}
