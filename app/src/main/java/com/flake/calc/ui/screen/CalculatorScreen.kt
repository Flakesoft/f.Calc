package com.flake.calc.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // TOP 35%
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    FilledIconButton(
                        onClick = { },
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = display,
                        fontSize = 42.sp,
                        maxLines = 1
                    )

                    Text(
                        text = "0",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // BOTTOM 65%
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {

                    items(buttons) { button ->

                        Button(
                            onClick = {
                                calculatorViewModel.onButtonClick(button)
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxWidth()
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