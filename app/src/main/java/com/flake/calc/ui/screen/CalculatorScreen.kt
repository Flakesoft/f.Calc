package com.flake.calc.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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

    // Root surface - only pad for status bar so we can let the keypad reach the nav bar
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // =========================
            // TOP (30%) - Display area (slightly reduced to give more room to buttons)
            // =========================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.30f)
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
            // KEYBOARD - pinned to bottom with a rounded container and ZERO bottom padding so buttons can touch nav bar
            // =========================
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.70f), // give more room to buttons
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {

                // Inner padding: only horizontal and top; bottom = 0 so buttons can reach nav bar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 14.dp, end = 14.dp, top = 18.dp, bottom = 0.dp)
                ) {

                    val operators = remember { setOf("/", "*", "-", "+", "=", "(", ")") }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            // interaction source to detect press state
                            val interactionSource = remember { MutableInteractionSource() }
                            val pressed by interactionSource.collectIsPressedAsState()

                            // animate corner radius (squircle-ish) and scale when pressed
                            val targetRadius: Dp = if (pressed) 12.dp else 50.dp
                            val cornerRadius by animateDpAsState(targetValue = targetRadius)
                            val scale by animateFloatAsState(targetValue = if (pressed) 0.985f else 1f)

                            Surface(
                                onClick = { vm.onButtonClick(button) },
                                shape = RoundedCornerShape(cornerRadius),
                                color = bgColor,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxWidth()
                                    .scale(scale),
                                tonalElevation = if (isEqual) 6.dp else 0.dp,
                                interactionSource = interactionSource
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Text(
                                        text = button,
                                        fontSize = if (button.length > 1) 20.sp else 36.sp, // slightly larger digits
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
