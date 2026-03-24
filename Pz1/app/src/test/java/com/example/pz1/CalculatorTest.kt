package com.example.pz1

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CalculatorTest {

    private lateinit var calculator: Calculator
    private lateinit var logger: ILogger

    @Before
    fun setUp() {
        // Створюємо мок-об'єкт логера [cite: 17]
        logger = mock()
        calculator = Calculator(logger)
    }

    @Test
    fun testAddition() {
        val result = calculator.add(10.0, 5.0)
        assertEquals("15.0", result)
        // Перевіряємо, що логування викликано 1 раз з правильним повідомленням [cite: 17]
        verify(logger, times(1)).log("Додавання: 10.0 + 5.0 = 15.0")
    }

    @Test
    fun testSubtraction() {
        val result = calculator.subtract(10.0, 5.0)
        assertEquals("5.0", result)
        verify(logger, times(1)).log("Віднімання: 10.0 - 5.0 = 5.0")
    }

    @Test
    fun testMultiplication() {
        val result = calculator.multiply(4.0, 3.0)
        assertEquals("12.0", result)
        verify(logger, times(1)).log("Множення: 4.0 * 3.0 = 12.0")
    }

    @Test
    fun testDivision() {
        val result = calculator.divide(20.0, 4.0)
        assertEquals("5.0", result)
        verify(logger, times(1)).log("Ділення: 20.0 / 4.0 = 5.0")
    }

    @Test
    fun testDivisionByZero() {
        val result = calculator.divide(10.0, 0.0)
        assertEquals("Помилка: Ділення на нуль", result)
        // Перевіряємо логування помилки [cite: 16, 17]
        verify(logger, times(1)).log("Помилка: Ділення на нуль")
    }
}