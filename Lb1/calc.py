def calculate(num1_str: str, num2_str: str, operation: str) -> str:
    """
    Простий консольний калькулятор.
    Приймає два числа і операцію у вигляді рядків. Повертає результат 
    або повідомлення про помилку у вигляді рядка.
    """
    # Обробка невірного формату чисел (якщо передані літери або спецсимволи)
    try:
        num1 = float(num1_str)
        num2 = float(num2_str)
    except ValueError:
        return "Помилка: невірний формат чисел"

    # Обробка математичних операцій
    if operation == '+':
        result = num1 + num2
    elif operation == '-':
        result = num1 - num2
    elif operation == '*':
        result = num1 * num2
    elif operation == '/':
        # Обробка ділення на нуль
        if num2 == 0:
            return "Помилка: ділення на нуль"
        result = num1 / num2
    else:
        # Обробка невідомої операції
        return "Помилка: невідома операція"

    # Форматування результату: якщо число ціле і не занадто велике, прибираємо дробову частину '.0'
    if result.is_integer() and abs(result) < 1e16:
        return str(int(result))
    
    return str(result)