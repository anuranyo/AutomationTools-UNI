package com.example.pz1

class Calculator(private val logger: ILogger) {
    fun add(a: Double, b: Double): String {
        val result = a + b
        logger.log("Додавання: $a + $b = $result")
        return result.toString()
    }

    fun subtract(a: Double, b: Double): String {
        val result = a - b
        logger.log("Віднімання: $a - $b = $result")
        return result.toString()
    }

    fun multiply(a: Double, b: Double): String {
        val result = a * b
        logger.log("Множення: $a * $b = $result")
        return result.toString()
    }

    fun divide(a: Double, b: Double): String {
        if (b == 0.0) {
            val error = "Помилка: Ділення на нуль"
            logger.log(error)
            return error
        }
        val result = a / b
        logger.log("Ділення: $a / $b = $result")
        return result.toString()
    }
}
