package com.joseleandro.donetask.ui.screen.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen() {

    val today by remember { mutableStateOf(LocalDate.now()) }


    val scope = rememberCoroutineScope()

    val calendarState = rememberWeekCalendarState(
        startDate = today.plusWeeks(-100),
        endDate = today.plusWeeks(100),
    )

    val monthName by remember {
        derivedStateOf {
            calendarState.firstVisibleWeek.days.first().date.month.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ).replaceFirstChar { it.uppercase() }
        }

    }

    val fullYearName by remember {

        derivedStateOf {
            calendarState.firstVisibleWeek.days.first().date.year.toString()
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "$monthName $fullYearName",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
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
                    IconButton(
                        onClick = {
                            scope.launch {
                                calendarState.animateScrollToWeek(
                                    date = calendarState.lastVisibleWeek.days.last().date.plusWeeks(
                                        -1
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "mês anterior"
                        )
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                calendarState.animateScrollToWeek(
                                    date = calendarState.firstVisibleWeek.days.first().date.plusWeeks(
                                        1
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "proxímo mês"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            AgendaHeader(
                calendarState = calendarState,
                today = today
            )

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

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Purple40
    ) {
        WeekCalendar(
            modifier = modifier
                .fillMaxWidth(),
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
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )
                    Text(
                        text = weekDay.date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
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
    DoneTaskTheme(dynamicColor = false, darkTheme = false) {
        AgendaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AgendaScreenDarkPreview() {
    DoneTaskTheme(dynamicColor = false, darkTheme = true) {
        AgendaScreen()
    }
}
