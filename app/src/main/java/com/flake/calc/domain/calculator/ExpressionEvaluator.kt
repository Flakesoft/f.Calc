package com.flake.calc.domain.calculator

import net.objecthunter.exp4j.ExpressionBuilder

object ExpressionEvaluator {

    fun evaluate(expression: String): String {

        return try {

            val normalizedExpression = expression
                .replace("×", "*")
                .replace("÷", "/")

            val result = ExpressionBuilder(
                normalizedExpression
            ).build().evaluate()

            if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                result.toString()
            }

        } catch (_: Exception) {
            "Error"
        }
    }
}
