package com.flake.calc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.flake.calc.domain.calculator.ExpressionEvaluator

class CalculatorViewModel : ViewModel() {

    var display by mutableStateOf("0")
        private set

    fun onButtonClick(value: String) {

        when (value) {
            "C" -> clear()

            "⌫" -> backspace()

            "=" -> calculate()

            else -> append(value)
        }
    }

    private fun append(value: String) {

        display = if (display == "0") {
            value
        } else {
            display + value
        }
    }

    private fun calculate() {

        val result = ExpressionEvaluator.evaluate(display)

        display = result
    }

    private fun backspace() {

        if (display.length <= 1 || display == "Error") {
            display = "0"
            return
        }

        display = display.dropLast(1)
    }

    private fun clear() {
        display = "0"
    }
}