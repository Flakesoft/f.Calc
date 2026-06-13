package com.flake.calc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.flake.calc.domain.calculator.ExpressionEvaluator

class CalculatorViewModel : ViewModel() {

    private val evaluator = ExpressionEvaluator()

    var display by mutableStateOf("0")
        private set

    var previewResult by mutableStateOf("0")
        private set

    private var isErrorState = false

    fun onButtonClick(value: String) {

        if (isErrorState) {
            // reset iz Error state-a
            if (value != "C") {
                display = value
                isErrorState = false
            }
        }

        when (value) {

            "C" -> clear()

            "⌫" -> backspace()

            "=" -> calculateFinal()

            else -> append(value)
        }

        updatePreview()
    }

    private fun append(value: String) {

        display = if (display == "0") value else display + value
    }

    private fun backspace() {

        if (display.length <= 1) {
            display = "0"
        } else {
            display = display.dropLast(1)
        }
    }

    private fun calculateFinal() {

        val result = evaluator.evaluate(display)

        if (result == "Error") {
            display = "Error"
            previewResult = "0"
            isErrorState = true
        } else {
            display = result
            previewResult = result
            isErrorState = false
        }
    }

    private fun updatePreview() {

        if (isErrorState) return

        previewResult = try {
            evaluator.evaluate(display)
        } catch (e: Exception) {
            "0"
        }
    }

    private fun clear() {
        display = "0"
        previewResult = "0"
        isErrorState = false
    }
}