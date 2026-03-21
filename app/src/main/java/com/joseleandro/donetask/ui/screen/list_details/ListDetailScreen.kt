package com.joseleandro.donetask.ui.screen.list_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.domain.model.Screen
import com.joseleandro.donetask.ui.screen.home.ListModel
import com.joseleandro.donetask.ui.screen.home.myListsMock
import com.joseleandro.donetask.ui.screen.list_details.components.ProgressCard
import com.joseleandro.donetask.ui.theme.DoneTaskTheme
import com.joseleandro.donetask.ui.viewmodel.NavigationViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import kotlin.math.roundToInt

@Composable
fun ListDetailScreen(
    listId: UUID
) {

    val navigationViewModel: NavigationViewModel = koinViewModel()
    val listData = myListsMock.first { it.id == listId }

    ListDetailScreen(
        listData = listData,
        onBack = navigationViewModel::onBack,
        onNavigate = navigationViewModel::navigateByScreen
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    listData: ListModel,
    onBack: () -> Unit,
    onNavigate: (Screen) -> Unit
) {

    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    var headerHeightPx by remember { mutableFloatStateOf(0f) }
    var cardHeightPx by remember { mutableFloatStateOf(0f) }

    val spacer1Px = with(density) { 24.dp.toPx() }
    val spacer2Px = with(density) { 16.dp.toPx() }

    val stickyPointPx = headerHeightPx + spacer1Px

    val maxCollapsePx = if (headerHeightPx > 0f) stickyPointPx else with(density) { 250.dp.toPx() }

    var offsetY by remember { mutableFloatStateOf(0f) }

    val showTopBarInfo by remember {
        derivedStateOf {
            headerHeightPx > 0 && offsetY > headerHeightPx
        }
    }

    val listState = rememberLazyListState()

    fun handleManualScroll(delta: Float) {
        val oldOffset = offsetY
        offsetY = (offsetY - delta).coerceIn(0f, maxCollapsePx)
        val consumedByHeader = offsetY - oldOffset

        if (consumedByHeader == 0f && delta < 0) {
            scope.launch {
                listState.scrollBy(-delta)
            }
        }
    }

    val headerFadeProgress = (offsetY / headerHeightPx)
        .coerceIn(0f, 1f)

    val headerAlpha = 1f - headerFadeProgress


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta < 0 && offsetY < maxCollapsePx) { // Scrolling up
                    val oldOffset = offsetY
                    offsetY = (offsetY - delta).coerceAtMost(maxCollapsePx)
                    val consumed = offsetY - oldOffset
                    return Offset(0f, -consumed)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                if (delta > 0 && offsetY > 0 && !listState.canScrollBackward) {
                    val oldOffset = offsetY
                    offsetY = (offsetY - delta).coerceAtLeast(0f)
                    val consumedByHeader = offsetY - oldOffset
                    return Offset(0f, -consumedByHeader)
                }
                return Offset.Zero
            }
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = showTopBarInfo,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {

                        ListDetailTitle(
                            nameList = listData.name,
                            icon = listData.icon,
                            color = listData.color
                        )

                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    actionIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = listData.color,
                contentColor = Color.White,
                onClick = {
                    onNavigate(Screen.TaskCreateScreen)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        modifier = Modifier.background(
            Brush.verticalGradient(
                listOf(
                    listData.color.copy(alpha = 0.8f),
                    listData.color.copy(alpha = 0.5f),
                    Color.Transparent
                )
            )
        )
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {

            ListDetailHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = headerAlpha
                        translationY = -offsetY * 0.3f
                    }
                    .onSizeChanged {
                        if (headerHeightPx == 0f) headerHeightPx = it.height.toFloat()
                    },
                color = listData.color,
                icon = listData.icon,
                nameList = listData.name
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        val y = (stickyPointPx - offsetY).coerceAtLeast(0f)
                        IntOffset(0, y.roundToInt())
                    }
                    .onSizeChanged { if (cardHeightPx == 0f) cardHeightPx = it.height.toFloat() }
            ) {
                ProgressCard(
                    listData = listData,
                )
            }

            val currentHeaderVisiblePx =
                (stickyPointPx + cardHeightPx - offsetY).coerceAtLeast(cardHeightPx)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { currentHeaderVisiblePx.toDp() })
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume()
                            handleManualScroll(dragAmount)
                        }
                    }
            )

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .offset {
                        val listStartPx = stickyPointPx + cardHeightPx + spacer2Px
                        val y = (listStartPx - offsetY).coerceAtLeast(cardHeightPx + spacer2Px)
                        IntOffset(0, y.roundToInt())
                    },
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 350.dp)
                ) {
                    stickyHeader {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                                .padding(horizontal = 16.dp),
                            color = Color.White,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.TaskAlt,
                                    contentDescription = null,
                                    tint = Color.DarkGray.copy(alpha = .5f)
                                )
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 4.dp),
                                    text = "Minhas tarefas",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = Color.DarkGray.copy(
                                            alpha = .5f
                                        )
                                    )
                                )

                                IconButton(
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Sort,
                                        contentDescription = null,
                                        tint = Color.DarkGray.copy(
                                            alpha = .5f
                                        )
                                    )
                                }
                            }
                        }
                    }
                    items(20) {
                        ListItem(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            colors = ListItemDefaults.colors(containerColor = Color.White),
                            headlineContent = {
                                Text(
                                    "Tarefa de exemplo",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.DarkGray.copy(
                                            alpha = .8f
                                        )
                                    )
                                )
                            },
                            trailingContent = { RadioButton(selected = false, onClick = {}) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListDetailTitle(
    modifier: Modifier = Modifier,
    nameList: String,
    color: Color,
    icon: ImageVector,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color, MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = nameList,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ListDetailHeader(
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector,
    nameList: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(color, shape = MaterialTheme.shapes.extraLarge)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = nameList,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ListDetailScreenDarkPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        val listData = myListsMock.first()

        ListDetailScreen(
            listData = listData,
            onNavigate = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListDetailScreenLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        val listData = myListsMock.first()

        ListDetailScreen(
            listData = listData,
            onNavigate = {},
            onBack = {}
        )
    }
}
