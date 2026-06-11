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

            // TOP DISPLAY AREA (35%)
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
                        // TODO: history/menu
                    },
                    modifier = Modifier.size(50.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.08f
                        )
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Display
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    // Main expression
                    Text(
                        text = display,
                        fontSize = 44.sp,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Live result (placeholder)
                    Text(
                        text = "0",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.5f
                        )
                    )
                }
            }

            // KEYBOARD AREA (65%)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
                    .navigationBarsPadding()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 0.dp
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
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.08f
                        ),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = button,
                                fontSize = 26.sp
                            )
                        }
                    }
                }
            }
        }
    }
}