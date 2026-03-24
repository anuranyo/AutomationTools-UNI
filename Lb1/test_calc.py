import unittest
from calc import calculate

class TestCalculator(unittest.TestCase):
    """
    Набір автоматизованих тестів для функції calculate.
    Використовує бібліотеку unittest.
    """
    
    # Виносимо повідомлення про помилки у константи класу для уникнення хардкоду у самих тестах.
    ERR_INVALID_FORMAT = "Помилка: невірний формат чисел"
    ERR_DIV_ZERO = "Помилка: ділення на нуль"
    ERR_UNKNOWN_OP = "Помилка: невідома операція"

    def test_addition_positive(self):
        """
        Позитивний тест: додавання цілих чисел.
        Техніка: Класи еквівалентності (типові цілі додатні числа).
        Мета: Перевірити базовий сценарій коректного математичного додавання.
        """
        num1, num2, op = "15", "25", "+"
        expected = "40"
        
        result = calculate(num1, num2, op)
        
        # Додаємо LOGування кроків і результату
        print(f"\n[LOG] Тест 'Додавання': операція {num1} {op} {num2}")
        print(f"[LOG] Очікувано отримати: {expected}")
        print(f"[LOG] Фактично отримано:  {result}")
        
        self.assertEqual(result, expected, "Числа додані неправильно")
        print("[LOG] ✔ Перевірка успішна (Assert Passed)!")

    def test_division_by_zero_negative(self):
        """
        Негативний тест: ділення на нуль.
        Мета: Переконатися, що функція відловлює спробу ділення на 0 
        та повертає коректне повідомлення про помилку замість падіння з Exception.
        """
        num1, num2, op = "10", "0", "/"
        
        result = calculate(num1, num2, op)
        
        print(f"\n[LOG] Тест 'Ділення на нуль': операція {num1} {op} {num2}")
        print(f"[LOG] Очікувано отримати повідомлення: '{self.ERR_DIV_ZERO}'")
        print(f"[LOG] Фактично отримано повідомлення:  '{result}'")
        
        self.assertEqual(result, self.ERR_DIV_ZERO, "Некоректна обробка ділення на нуль")
        print("[LOG] ✔ Помилка успішно відловлена!")

    def test_invalid_format_negative(self):
        """
        Негативний тест: введення літер замість чисел.
        Техніка: Класи еквівалентності (невалідні типи даних).
        Мета: Перевірити валідацію вхідних рядків на можливість конвертації у float.
        """
        num1, num2, op = "abc", "5", "+"
        
        result = calculate(num1, num2, op)
        
        print(f"\n[LOG] Тест 'Невалідний формат (літери)': операція '{num1}' {op} '{num2}'")
        print(f"[LOG] Очікувано отримати повідомлення: '{self.ERR_INVALID_FORMAT}'")
        print(f"[LOG] Фактично отримано повідомлення:  '{result}'")
        
        self.assertEqual(result, self.ERR_INVALID_FORMAT, "Не відловлено помилку формату (літери замість чисел)")
        print("[LOG] ✔ Некоректний формат успішно відхилено!")

    def test_negative_numbers_data_driven(self):
        """
        Позитивний тест: робота з від'ємними числами.
        Техніка: Класи еквівалентності (від'ємні числа) + Data-driven тестування.
        Мета: Перевірити коректність роботи різних математичних операцій із від'ємними значеннями.
        """
        test_data = [
            {"num1": "-5",  "num2": "-3", "op": "+", "expected": "-8"},
            {"num1": "-10", "num2": "5",  "op": "-", "expected": "-15"},
            {"num1": "-4",  "num2": "-2", "op": "*", "expected": "8"},
            {"num1": "-20", "num2": "4",  "op": "/", "expected": "-5"}
        ]
        
        print("\n[LOG] Починаємо Data-driven тести для від'ємних чисел:")
        for case in test_data:
            with self.subTest(case=case):
                result = calculate(case["num1"], case["num2"], case["op"])
                print(f"[LOG]   -> Підтест: {case['num1']} {case['op']} {case['num2']} | Очікуємо: {case['expected']} | Фактично: {result}")
                self.assertEqual(
                    result, 
                    case["expected"], 
                    f"Помилка при виконанні {case['num1']} {case['op']} {case['num2']}"
                )
        print("[LOG] ✔ Усі підтести з від'ємними числами пройдено!")

    def test_boundary_large_numbers(self):
        """
        Тест граничних значень: робота з дуже великими числами (наприклад, 1e308).
        Техніка: Аналіз граничних значень (число лежить на верхній межі місткості типу float).
        Мета: Перевірити, чи здатна система оперувати екстремально великими числами без падіння.
        """
        num1, num2, op = "1e308", "2", "/"
        expected = "5e+307" # Результат ділення на 2
        
        result = calculate(num1, num2, op)
        
        print(f"\n[LOG] Тест 'Граничні значення (дуже великі числа)': {num1} {op} {num2}")
        print(f"[LOG] Очікувано отримати: {expected}")
        print(f"[LOG] Фактично отримано:  {result}")
        
        self.assertEqual(result, expected, "Помилка при обробці гранично великих чисел")
        print("[LOG] ✔ Перевірка успішна (робота з пам'яттю коректна)!")

if __name__ == '__main__':
    unittest.main()
