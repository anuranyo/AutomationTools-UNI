package com.example.pz1

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val logger = object : ILogger {
        override fun log(message: String) {
            Log.d("CalculatorApp", message)
        }
    }

    private val calculator = Calculator(logger)

    private val _history = MutableStateFlow(sharedPreferences.getString("history", "") ?: "")
    val history: StateFlow<String> = _history.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun calculate(a: String, b: String, operation: String) {
        val numA = a.toDoubleOrNull()
        val numB = b.toDoubleOrNull()

        if (numA == null || numB == null) {
            _result.value = "Помилка: Введіть коректні числа"
            return
        }

        val calcResult = when (operation) {
            "+" -> calculator.add(numA, numB)
            "-" -> calculator.subtract(numA, numB)
            "*" -> calculator.multiply(numA, numB)
            "/" -> calculator.divide(numA, numB)
            else -> "Помилка: Невідома операція"
        }

        _result.value = calcResult
        saveToHistory("$numA $operation $numB = $calcResult")
    }

    private fun saveToHistory(record: String) {
        // Отримуємо поточну історію з StateFlow
        val currentHistory = _history.value

        // Формуємо нову історію: додаємо новий запис зверху (або знизу)
        val newHistory = if (currentHistory.isEmpty()) {
            record
        } else {
            "$record\n$currentHistory" // Нові записи будуть зверху
        }

        // Оновлюємо стан та SharedPreferences
        _history.value = newHistory
        sharedPreferences.edit().putString("history", newHistory).apply()
    }
}

class CalculatorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            val prefs = context.getSharedPreferences("calc_prefs", Context.MODE_PRIVATE)
            @Suppress("UNCHECKED_CAST")
            return CalculatorViewModel(prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
