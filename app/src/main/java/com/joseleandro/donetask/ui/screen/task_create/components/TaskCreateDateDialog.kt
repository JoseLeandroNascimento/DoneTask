package com.joseleandro.donetask.ui.screen.task_create.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joseleandro.donetask.R
import com.joseleandro.donetask.ui.theme.DoneTaskTheme

@Composable
fun TaskCreateDateDialog(
    onDismissRequest: () -> Unit
) {

    val datePickerState = rememberDatePickerState()

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismissRequest
    ) {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.fechar)
                        )
                    }
                }

                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = "Hora",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.AccessTimeFilled,
                                contentDescription = stringResource(id = R.string.hora)
                            )
                        },
                        trailingContent = {
                            Text(
                                text = "Não",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    )

                    ListItem(
                        headlineContent = {
                            Text(
                                text = "Hora",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.AccessTimeFilled,
                                contentDescription = stringResource(id = R.string.hora)
                            )
                        },
                        trailingContent = {
                            Text(
                                text = "Não",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    )

                    ListItem(
                        headlineContent = {
                            Text(
                                text = "Hora",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.AccessTimeFilled,
                                contentDescription = stringResource(id = R.string.hora)
                            )
                        },
                        trailingContent = {
                            Text(
                                text = "Não",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskCreateDateDialogDarkPreview() {

    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        TaskCreateDateDialog { }
    }
}

@Preview
@Composable
private fun TaskCreateDateDialogLightPreview() {

    DoneTaskTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        TaskCreateDateDialog { }
    }
}