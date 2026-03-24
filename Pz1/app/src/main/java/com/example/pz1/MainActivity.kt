package com.example.pz1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pz1.ui.theme.Pz1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pz1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val factory = CalculatorViewModelFactory(applicationContext)
                    val viewModel: CalculatorViewModel = viewModel(factory = factory)
                    CalculatorScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val history by viewModel.history.collectAsState()
    val result by viewModel.result.collectAsState()

    var inputA by remember { mutableStateOf("") }
    var inputB by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = inputA,
            onValueChange = { inputA = it },
            label = { Text("Перше число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = inputB,
            onValueChange = { inputB = it },
            label = { Text("Друге число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.calculate(inputA, inputB, "+") }) { Text("+") }
            Button(onClick = { viewModel.calculate(inputA, inputB, "-") }) { Text("-") }
            Button(onClick = { viewModel.calculate(inputA, inputB, "*") }) { Text("*") }
            Button(onClick = { viewModel.calculate(inputA, inputB, "/") }) { Text("/") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Результат:", style = MaterialTheme.typography.titleMedium)
        Text(text = result, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Історія обчислень:", style = MaterialTheme.typography.titleMedium)

        // Використовуємо Modifier.verticalScroll, щоб список можна було гортати
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp) // Обмежуємо висоту блоку історії
                .verticalScroll(scrollState)
        ) {
            Text(
                text = history,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}