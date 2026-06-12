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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flake.calc.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen() {

    val calculatorViewModel: CalculatorViewModel = viewModel()
    val display = calculatorViewModel.display

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
            // TOP AREA (35%)
            // =========================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {

                // Hamburger
                FilledIconButton(
                    onClick = {
                        // TODO: history drawer
                    },
                    modifier = Modifier.size(52.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // DISPLAY AREA
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = display,
                        fontSize = 46.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // LIVE RESULT (placeholder for now)
                    val liveResult = "" // <-- kasnije iz ViewModel-a

                    if (liveResult.isNotBlank()) {
                        Text(
                            text = liveResult,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            // =========================
            // KEYBOARD (65%) - FIXED TO BOTTOM
            // =========================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 16.dp
                        )
                ) {

                    items(buttons) { button ->

                        Surface(
                            onClick = {
                                calculatorViewModel.onButtonClick(button)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                            tonalElevation = 0.dp,
                            shadowElevation = 0.dp
                        ) {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = button,
                                    fontSize = 28.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}