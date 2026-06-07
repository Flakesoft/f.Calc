package com.flake.calc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.flake.calc.domain.calculator.CalculatorEngine

class CalculatorViewModel : ViewModel() {

    private val calculatorEngine = CalculatorEngine()

    var display by mutableStateOf("0")
        private set

    fun onButtonClick(value: String) {
        when (value) {
            "C" -> clear()
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
        display = calculatorEngine.evaluate(display)
    }

    private fun clear() {
        display = "0"
    }
}
