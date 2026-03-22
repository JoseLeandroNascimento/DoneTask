package com.joseleandro.donetask.ui.screen.task_create.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
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
    enabledChecked: Boolean = true,
    onCheckedChange: () -> Unit,
    trailingIcon: ImageVector = Icons.Default.Add,
    onTrailingIconClick: () -> Unit
) {

    val input = remember { FocusRequester() }

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
        maxLines = 1,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (value.isNotBlank()) {
                    onTrailingIconClick()
                }
            }
        )
    ) { innerTextField ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            RadioButton(
                enabled = enabledChecked,
                onClick = onCheckedChange,
                selected = checked,
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
            ) {

                if (value.isEmpty()) {
                    label()
                }

                innerTextField()

            }

            IconButton(
                onClick = {

                    if (value.trim().isEmpty()) {
                        input.requestFocus()
                    }

                    onTrailingIconClick()
                }
            ) {
                Icon(
                    imageVector = trailingIcon,
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
            onTrailingIconClick = {}
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
            onTrailingIconClick = {}
        )
    }
}