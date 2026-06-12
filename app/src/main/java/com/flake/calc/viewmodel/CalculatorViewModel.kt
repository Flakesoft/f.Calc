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

    var previewResult by mutableStateOf("")
        private set

    var lastResult by mutableStateOf("")
        private set

    fun onButtonClick(value: String) {
        when (value) {

            "C" -> clear()

            "⌫" -> backspace()

            "=" -> evaluateFinal()

            else -> append(value)
        }

        updatePreview()
    }

    // -------------------------
    // INPUT
    // -------------------------

    private fun append(value: String) {

        display = if (display == "0") {
            value
        } else {
            display + value
        }
    }

    private fun backspace() {

        if (display.length <= 1) {
            display = "0"
        } else {
            display = display.dropLast(1)
        }
    }

    private fun clear() {
        display = "0"
        previewResult = ""
        lastResult = ""
    }

    // -------------------------
    // EVALUATION
    // -------------------------

    private fun evaluateFinal() {

        val result = evaluator.evaluate(display)

        display = result
        lastResult = result
        previewResult = ""
    }

    // -------------------------
    // LIVE PREVIEW
    // -------------------------

    private fun updatePreview() {

        previewResult = try {
            val result = evaluator.evaluate(display)

            if (result == "Error") "" else result

        } catch (e: Exception) {
            ""
        }
    }
}