using System;
using LabWork;

Console.OutputEncoding = System.Text.Encoding.UTF8;
Console.WriteLine("=== ДЕМОНСТРАЦІЯ РОБОТИ КАЛЬКУЛЯТОРА (ЛБ №2) ===\n");

var logger = new ConsoleLogger();
var calc = new Calculator(logger);

// 1. Позитивний сценарій
ShowCase(calc, "Додавання цілих чисел", "15", "25", "+");

// 2. Робота з від'ємними числами
ShowCase(calc, "Віднімання від'ємних чисел", "-10", "5", "-");

// 3. Граничні значення
ShowCase(calc, "Граничні значення (Великі числа)", "1e300", "1e300", "*");

// 4. Негативний сценарій: Ділення на нуль
ShowCase(calc, "Спроба ділення на нуль", "10", "0", "/");

// 5. Негативний сценарій: Невалідний формат
ShowCase(calc, "Невалідний формат вводу", "abc", "5", "+");

Console.WriteLine("\nДемонстрацію завершено. Натисніть будь-яку клавішу для виходу.");
Console.ReadKey();

static void ShowCase(Calculator calc, string description, string a, string b, string op)
{
    Console.WriteLine($"--- ТЕСТ: {description} ---");
    try
    {
        double result = calc.Calculate(a, b, op);
        Console.WriteLine($"РЕЗУЛЬТАТ: {a} {op} {b} = {result}");
    }
    catch (Exception e)
    {
        Console.WriteLine($"ОБРОБЛЕНО ПОМИЛКУ: {e.Message}");
    }
    Console.WriteLine();
}

public class ConsoleLogger : ILogger
{
    public void Log(string message)
    {
        // Виділяємо лог кольором для наочності в консолі
        var oldColor = Console.ForegroundColor;
        Console.ForegroundColor = ConsoleColor.Cyan;
        Console.WriteLine($"[LOG]: {message}");
        Console.ForegroundColor = oldColor;
    }
}