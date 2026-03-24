using System;
using System.Globalization;

namespace LabWork
{
    // Інтерфейс для логування (Вимога 1)
    public interface ILogger
    {
        void Log(string message);
    }

    // Константи для уникнення хардкодингу в коді та тестах (Вимога ЛБ 1)
    public static class ErrorMessages
    {
        public const string DivideByZero = "Ділення на нуль неможливе.";
        public const string InvalidFormat = "Неправильний формат числа.";
        public const string UnknownOperation = "Невідома операція.";
    }

    // Головний клас Calculator
    public class Calculator
    {
        private readonly ILogger _logger;

        // Dependency Injection через конструктор (Вимога 2)
        public Calculator(ILogger logger)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
        }

        // Основний метод виконання операцій (Вимога 3)
        public double Calculate(string a, string b, string op)
        {
            _logger.Log($"Початок операції: {a} {op} {b}");

            // Конвертація рядків у double з підтримкою інваріантної культури (крапка для дробів)
            if (!double.TryParse(a, NumberStyles.Any, CultureInfo.InvariantCulture, out double numA) || 
                !double.TryParse(b, NumberStyles.Any, CultureInfo.InvariantCulture, out double numB))
            {
                _logger.Log($"Помилка: {ErrorMessages.InvalidFormat}");
                throw new FormatException(ErrorMessages.InvalidFormat);
            }

            double result = 0;
            switch (op)
            {
                case "+":
                    result = numA + numB;
                    break;
                case "-":
                    result = numA - numB;
                    break;
                case "*":
                    result = numA * numB;
                    break;
                case "/":
                    if (numB == 0) // Перевірка ділення на нуль
                    {
                        _logger.Log($"Помилка: {ErrorMessages.DivideByZero}");
                        throw new DivideByZeroException(ErrorMessages.DivideByZero);
                    }
                    result = numA / numB;
                    break;
                default:
                    // Обробка невідомих операцій
                    _logger.Log($"Помилка: {ErrorMessages.UnknownOperation} (отримано: {op})");
                    throw new ArgumentException(ErrorMessages.UnknownOperation);
            }

            _logger.Log($"Успіх. Результат: {result}");
            return result;
        }
    }

    // Клас для додаткового завдання (ЛБ 2)
    public class StringProcessor
    {
        public bool IsPalindrome(string text)
        {
            if (string.IsNullOrEmpty(text)) return false;

            // Видаляємо пробіли та переводимо в нижній регістр
            string cleanText = text.Replace(" ", "").ToLower();
            int left = 0;
            int right = cleanText.Length - 1;

            while (left < right)
            {
                if (cleanText[left] != cleanText[right])
                    return false;
                left++;
                right--;
            }

            return true;
        }
    }
}
