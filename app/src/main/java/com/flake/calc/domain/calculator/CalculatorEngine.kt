package com.flake.calc.domain.calculator

class CalculatorEngine {

    fun evaluate(expression: String): String {
        return try {
            val sanitized = expression.replace(" ", "")

            val operator = findOperator(sanitized)

            if (operator == null) {
                return sanitized
            }

            val parts = sanitized.split(operator)

            if (parts.size != 2) {
                return "Error"
            }

            val first = parts[0].toDouble()
            val second = parts[1].toDouble()

            val result = when (operator) {
                "+" -> first + second
                "-" -> first - second
                "×" -> first * second
                "÷" -> {
                    if (second == 0.0) return "Error"
                    first / second
                }
                else -> return "Error"
            }

            formatResult(result)

        } catch (e: Exception) {
            "Error"
        }
    }

    private fun findOperator(expression: String): String? {
        val operators = listOf("+", "-", "×", "÷")

        for (operator in operators) {
            if (expression.contains(operator)) {
                return operator
            }
        }

        return null
    }

    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
}