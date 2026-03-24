package com.example.pz1

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed

@RunWith(Parameterized::class)
class CalculatorUITest(
    private val firstOp: String,
    private val secondOp: String,
    private val operation: String,
    private val expectedResult: String
) {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} {2} {1} = {3}")
        fun data(): Collection<Array<String>> {
            return listOf(
                arrayOf("5", "3", "+", "8.0"),
                arrayOf("10", "4", "-", "6.0"),
                arrayOf("6", "7", "*", "42.0"),
                arrayOf("20", "5", "/", "4.0"),
                arrayOf("10", "0", "/", "Помилка: Ділення на нуль")
            )
        }
    }

    @Test
    fun testCalculatorOperations() {
        val firstInput = composeTestRule.onNode(hasText("Перше число") or hasSetTextAction())
        val secondInput = composeTestRule.onAllNodes(hasText("Друге число") or hasSetTextAction())

        // Because there are multiple text fields, we will just use the index if needed,
        // or clear semantics. Compose test for OutlinedTextField can be matched by label text.
        composeTestRule.onNodeWithText("Перше число").performTextInput(firstOp)
        composeTestRule.onNodeWithText("Друге число").performTextInput(secondOp)

        composeTestRule.onNodeWithText(operation).performClick()

        // Verify result text
        composeTestRule.onNodeWithText(expectedResult).assertIsDisplayed()
    }
}
