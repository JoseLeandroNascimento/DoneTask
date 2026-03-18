package com.joseleandro.donetask.ui.screen.list_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.ui.screen.home.myListsMock
import com.joseleandro.donetask.ui.screen.list_details.components.ProgressCard
import com.joseleandro.donetask.ui.theme.DoneTaskTheme
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    listId: UUID
) {
    val listData = myListsMock.first { it.id == listId }
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    var headerHeightPx by remember { mutableFloatStateOf(0f) }
    var cardHeightPx by remember { mutableFloatStateOf(0f) }
    
    val spacer1Px = with(density) { 24.dp.toPx() }
    val spacer2Px = with(density) { 16.dp.toPx() }

    val maxCollapsePx = if (headerHeightPx > 0f && cardHeightPx > 0f) {
        headerHeightPx + cardHeightPx + spacer1Px + spacer2Px
    } else {
        with(density) { 450.dp.toPx() }
    }

    var offsetY by remember { mutableFloatStateOf(0f) }
    val collapseFraction = (offsetY / maxCollapsePx).coerceIn(0f, 1f)

    // Ponto para mostrar o título na TopBar (ex: quando o header original sumir 80%)
//    val showTopBarInfo by remember {
//        derivedStateOf { collapseFraction > 0.8f }
//    }

    val showTopBarInfo by remember {
        derivedStateOf {
            val currentFraction = (offsetY / maxCollapsePx).coerceIn(0f, 1f)
            currentFraction > 0.8f
        }
    }

    val animatedCollapse by animateFloatAsState(
        targetValue = collapseFraction,
        animationSpec = tween(250),
        label = "collapse"
    )

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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(listData.color, MaterialTheme.shapes.small),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = listData.icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = listData.name,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
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

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        handleManualScroll(dragAmount)
                    }
                }
        ) {

            // Header dinâmico
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (headerHeightPx > 0f) Modifier.height(with(density) { 
                            (headerHeightPx - offsetY).coerceAtLeast(0f).toDp() 
                        }) else Modifier.wrapContentHeight()
                    )
                    .clipToBounds()
            ) {
                Column(
                    modifier = Modifier
                        .onSizeChanged { size ->
                            if (headerHeightPx == 0f && size.height > 0) {
                                headerHeightPx = size.height.toFloat()
                            }
                        }
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                listData.color,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = listData.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Text(
                        text = listData.name,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Card dinâmico
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (cardHeightPx > 0f) Modifier.height(with(density) { 
                            val cardOffset = (offsetY - headerHeightPx - spacer1Px).coerceAtLeast(0f)
                            (cardHeightPx - cardOffset).coerceAtLeast(0f).toDp() 
                        }) else Modifier.wrapContentHeight()
                    )
                    .clipToBounds()
            ) {

                    ProgressCard(
                        modifier = Modifier.onSizeChanged { size ->
                            if (cardHeightPx == 0f && size.height > 0) {
                                cardHeightPx = size.height.toFloat()
                            }
                        },
                        listData = listData,
                        collapseFraction = animatedCollapse
                    )

            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📜 LISTA
            Surface(
                modifier = Modifier.weight(1f),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(20) {
                        ListItem(
                            colors = ListItemDefaults.colors(containerColor = Color.White),
                            headlineContent = {
                                Text(
                                    "Tarefa de exemplo",
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray)
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


@Preview(showBackground = true)
@Composable
private fun ListDetailScreenDarkPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ListDetailScreen(listId = myListsMock.first().id)
    }
}

@Preview(showBackground = true)
@Composable
private fun ListDetailScreenLightPreview() {
    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        ListDetailScreen(listId = myListsMock.first().id)
    }
}
