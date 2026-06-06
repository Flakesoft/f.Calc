package com.flake.calc.domain.calculator

class CalculatorEngine {

    fun evaluate(expression: String): String {
        return try {
            expression
        } catch (e: Exception) {
            "Error"
        }
    }
}
