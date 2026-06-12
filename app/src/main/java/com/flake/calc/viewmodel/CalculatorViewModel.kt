package com.flake.calc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.flake.calc.domain.calculator.CalculatorEngine

class CalculatorViewModel : ViewModel() {

    private val engine = CalculatorEngine()

    // glavni input (ono što korisnik kuca)
    var display by mutableStateOf("0")
        private set

    // live preview (rezultat u real-time)
    var previewResult by mutableStateOf("")
        private set

    // = poslednji finalni rezultat (za istoriju kasnije)
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
    // INPUT LOGIC
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
            return
        }

        display = display.dropLast(1)
    }

    private fun clear() {
        display = "0"
        previewResult = ""
        lastResult = ""
    }

    // -------------------------
    // CALCULATION
    // -------------------------

    private fun evaluateFinal() {

        val result = engine.evaluate(display)

        display = result
        lastResult = result
        previewResult = ""
    }

    // -------------------------
    // LIVE PREVIEW
    // -------------------------

    private fun updatePreview() {
        previewResult = try {
            val result = engine.evaluate(display)

            // ako engine vrati error → ne pokazuj ništa
            if (result == "Error") "" else result

        } catch (e: Exception) {
            ""
        }
    }
}