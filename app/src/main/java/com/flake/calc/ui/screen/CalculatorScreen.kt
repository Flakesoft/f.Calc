package com.flake.calc.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flake.calc.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen() {

    val vm: CalculatorViewModel = viewModel()

    val display = vm.display
    val preview = vm.previewResult

    val buttons = listOf(
        "C", "⌫", "(", ")",
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        ".", "0", "=", "+"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // =========================
            // TOP (35%)
            // =========================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {

                FilledIconButton(
                    onClick = { },
                    modifier = Modifier.size(52.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
                    )
                ) {
                    Icon(Icons.Rounded.Menu, contentDescription = null)
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = display,
                        fontSize = when {
                            display.length < 10 -> 48.sp
                            display.length < 16 -> 40.sp
                            else -> 32.sp
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = preview,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                        maxLines = 1
                    )
                }
            }

            // =========================
            // KEYBOARD (65%) - PINNED FEEL
            // =========================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 14.dp,
                            end = 14.dp,
                            bottom = 18.dp
                        )
                ) {

                    items(buttons) { button ->

                        Surface(
                            onClick = { vm.onButtonClick(button) },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxWidth()
                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {

                                Text(
                                    text = button,
                                    fontSize = 32.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}