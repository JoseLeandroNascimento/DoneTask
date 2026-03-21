package com.joseleandro.donetask.ui.screen.task_create.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@Composable
fun TaskCreateInputTextSubtask(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    checked: Boolean = false,
    onCheckedChange: () -> Unit,
    onAddNewSubtask: () -> Unit
) {

    val (input) = remember { FocusRequester.createRefs() }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(input),
        value = value,
        cursorBrush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.onSurface,
                MaterialTheme.colorScheme.onSurface
            )
        ),
        onValueChange = onValueChange
    ) { innerTextField ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            RadioButton(
                onClick = onCheckedChange,
                selected = checked,
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
            ) {
                if (value.trim().isEmpty()) {
                    label()
                }
                innerTextField()

            }

            IconButton(
                onClick = {

                    if (value.trim().isEmpty()) {
                        input.requestFocus()
                    }

                    onAddNewSubtask()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.adicionar_subtarefa)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCreateInputTextSubtaskLightPreview() {

    var value by remember { mutableStateOf("") }

    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        TaskCreateInputTextSubtask(
            label = {
                Text(
                    text = "Teste",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = .6f
                        )
                    )
                )
            },
            value = value,
            onValueChange = {
                value = it
            },
            onCheckedChange = {},
            checked = true,
            onAddNewSubtask = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCreateInputTextSubtaskDarkPreview() {

    var value by remember { mutableStateOf("") }

    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        TaskCreateInputTextSubtask(
            label = {
                Text(
                    text = "Teste",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = .6f
                        )
                    )
                )
            },
            value = value,
            onValueChange = {
                value = it
            },
            onCheckedChange = {},
            checked = false,
            onAddNewSubtask = {}
        )
    }
}