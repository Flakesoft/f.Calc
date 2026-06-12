package com.flake.calc.domain.calculator

class ExpressionEvaluator {

    fun evaluate(expression: String): String {
        return try {

            val expr = expression.replace(" ", "")

            val result = evaluateSimple(expr)

            result

        } catch (e: Exception) {
            "Error"
        }
    }

    private fun evaluateSimple(expr: String): String {

        var current = ""
        val numbers = mutableListOf<Double>()
        val ops = mutableListOf<Char>()

        for (c in expr) {

            if (c.isDigit() || c == '.') {
                current += c
            } else {
                if (current.isNotEmpty()) {
                    numbers.add(current.toDouble())
                    current = ""
                }
                ops.add(c)
            }
        }

        if (current.isNotEmpty()) {
            numbers.add(current.toDouble())
        }

        if (numbers.isEmpty()) return "0"

        // * i /
        var i = 0
        while (i < ops.size) {

            if (ops[i] == '*' || ops[i] == '/') {

                val left = numbers[i]
                val right = numbers[i + 1]

                val result = when (ops[i]) {
                    '*' -> left * right
                    '/' -> {
                        if (right == 0.0) return "Error"
                        left / right
                    }
                    else -> 0.0
                }

                numbers[i] = result
                numbers.removeAt(i + 1)
                ops.removeAt(i)
                i--

            }

            i++
        }

        // + i -
        var result = numbers[0]
        var j = 0

        for (op in ops) {

            val next = numbers[j + 1]

            result = when (op) {
                '+' -> result + next
                '-' -> result - next
                else -> result
            }

            j++
        }

        return if (result % 1.0 == 0.0)
            result.toInt().toString()
        else
            result.toString()
    }
}