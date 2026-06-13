package com.flake.calc.domain.calculator

import java.util.Stack

class ExpressionEvaluator {

    fun evaluate(expression: String): String {
        return try {

            val expr = expression.replace(" ", "")

            if (expr.isEmpty()) return "0"

            val result = evaluateExpression(expr)

            format(result)

        } catch (e: Exception) {
            "Error"
        }
    }

    // ----------------------------
    // CORE ENGINE (shunting-yard)
    // ----------------------------

    private fun evaluateExpression(expr: String): Double {

        val values = Stack<Double>()
        val ops = Stack<Char>()

        var i = 0

        while (i < expr.length) {

            val c = expr[i]

            when {

                c.isDigit() || c == '.' -> {

                    val sb = StringBuilder()

                    while (i < expr.length &&
                        (expr[i].isDigit() || expr[i] == '.')
                    ) {
                        sb.append(expr[i])
                        i++
                    }

                    values.push(sb.toString().toDouble())
                    i--
                }

                c == '(' -> {
                    ops.push(c)
                }

                c == ')' -> {

                    while (ops.isNotEmpty() && ops.peek() != '(') {
                        applyOp(values, ops.pop())
                    }

                    if (ops.isNotEmpty() && ops.peek() == '(') {
                        ops.pop()
                    }
                }

                isOperator(c) -> {

                    while (
                        ops.isNotEmpty() &&
                        precedence(ops.peek()) >= precedence(c)
                    ) {
                        applyOp(values, ops.pop())
                    }

                    ops.push(c)
                }
            }

            i++
        }

        while (ops.isNotEmpty()) {
            applyOp(values, ops.pop())
        }

        return values.pop()
    }

    // ----------------------------
    // APPLY OPERATOR
    // ----------------------------

    private fun applyOp(values: Stack<Double>, op: Char) {

        val b = values.pop()
        val a = values.pop()

        val result = when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw ArithmeticException("Division by zero")
                a / b
            }
            else -> 0.0
        }

        values.push(result)
    }

    // ----------------------------
    // HELPERS
    // ----------------------------

    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/'
    }

    private fun precedence(op: Char): Int {
        return when (op) {
            '+', '-' -> 1
            '*', '/' -> 2
            else -> 0
        }
    }

    private fun format(result: Double): String {
        return if (result % 1.0 == 0.0)
            result.toInt().toString()
        else
            result.toString()
    }
}