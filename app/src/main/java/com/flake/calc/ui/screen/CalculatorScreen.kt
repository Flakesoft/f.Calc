package com.flake.calc.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
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

    // Root surface that respects system bars so the UI reaches the bottom/nav bar
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // =========================
            // TOP (35%) - Display area
            // =========================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.02f),
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.0f)
                                )
                            )
                        ),
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = display,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = when {
                            display.length < 10 -> 56.sp
                            display.length < 16 -> 44.sp
                            else -> 36.sp
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = preview,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                        maxLines = 1
                    )
                }
            }

            // =========================
            // KEYBOARD (65%) - pinned to bottom with a rounded container
            // =========================
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {

                // Inner padding so buttons don't touch rounded corners / nav bar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 18.dp)
                ) {

                    val operators = remember { setOf("/", "*", "-", "+", "=", "(", ")") }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        items(buttons) { button ->

                            val isOperator = operators.contains(button)
                            val isClear = button == "C"
                            val isEqual = button == "="

                            val bgColor = when {
                                isEqual -> MaterialTheme.colorScheme.primary
                                isClear -> MaterialTheme.colorScheme.errorContainer
                                isOperator -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }

                            val contentColor = when {
                                isEqual -> MaterialTheme.colorScheme.onPrimary
                                isClear -> MaterialTheme.colorScheme.onErrorContainer
                                isOperator -> MaterialTheme.colorScheme.onSurface
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            Surface(
                                onClick = { vm.onButtonClick(button) },
                                shape = CircleShape,
                                color = bgColor,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxWidth(),
                                tonalElevation = if (isEqual) 6.dp else 0.dp
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Text(
                                        text = button,
                                        fontSize = if (button.length > 1) 20.sp else 32.sp,
                                        color = contentColor,
                                        fontWeight = if (isOperator || isEqual) FontWeight.Medium else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
