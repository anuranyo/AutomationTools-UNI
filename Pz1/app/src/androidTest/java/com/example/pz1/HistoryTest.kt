package com.example.pz1

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryTest {

    private lateinit var context: Context
    private lateinit var prefs: android.content.SharedPreferences

    @Before
    fun setUp() {
        // Отримуємо контекст та очищуємо SharedPreferences перед кожним тестом
        context = ApplicationProvider.getApplicationContext<Context>()
        prefs = context.getSharedPreferences("calc_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()
    }

    @Test
    fun testHistorySavedInSharedPreferences() {
        val viewModel = CalculatorViewModel(prefs)

        // Виконуємо одну операцію
        viewModel.calculate("10", "5", "+")

        // Перевіряємо, чи зберігся запис
        val history = prefs.getString("history", "")
        assertEquals("10.0 + 5.0 = 15.0", history)
    }

    @Test
    fun testMultipleOperationsAccumulateInHistory() {
        val viewModel = CalculatorViewModel(prefs)

        // Виконуємо кілька операцій поспіль
        viewModel.calculate("10", "5", "+") // 10.0 + 5.0 = 15.0
        viewModel.calculate("2", "3", "*")   // 2.0 * 3.0 = 6.0

        val history = prefs.getString("history", "") ?: ""

        // Перевіряємо, що в історії є обидва записи (через перенос рядка \n)
        assertTrue("Історія має містити перший запис", history.contains("10.0 + 5.0 = 15.0"))
        assertTrue("Історія має містити другий запис", history.contains("2.0 * 3.0 = 6.0"))
    }

    @Test
    fun testErrorStoredInHistory() {
        val viewModel = CalculatorViewModel(prefs)

        // Тестуємо ділення на нуль
        viewModel.calculate("10", "0", "/")

        val history = prefs.getString("history", "")
        // Перевіряємо, що помилка також потрапила в історію
        assertEquals("10.0 / 0.0 = Помилка: Ділення на нуль", history)
    }

    @Test
    fun testHistoryPersistenceAfterNewViewModelInstance() {
        // Симулюємо перший запуск додатка
        val viewModel1 = CalculatorViewModel(prefs)
        viewModel1.calculate("5", "5", "+")

        // Симулюємо створення нового екземпляра (наприклад, після перезапуску Activity)
        val viewModel2 = CalculatorViewModel(prefs)

        // Перевіряємо, чи бачить нова ViewModel старі дані з SharedPreferences
        assertEquals("5.0 + 5.0 = 10.0", viewModel2.history.value)
    }
}