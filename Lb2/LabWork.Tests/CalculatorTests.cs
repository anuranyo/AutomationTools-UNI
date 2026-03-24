using System;
using System.Collections.Generic;
using NUnit.Framework;

namespace LabWork.Tests
{
    // Mock-об'єкт (Fake), модифікований для виводу логів у консоль тесту
    public class FakeLogger : ILogger
    {
        public List<string> Logs { get; } = new List<string>();

        public void Log(string message)
        {
            Logs.Add(message);
            // Вивід у консоль NUnit для відображення в Test Explorer (Standard Output)
            Console.WriteLine($"[LOG ІЗ КАЛЬКУЛЯТОРА]: {message}");
        }
    }

    [TestFixture]
    public class CalculatorTests
    {
        private Calculator _calculator;
        private FakeLogger _fakeLogger;

        [SetUp]
        public void Setup()
        {
            _fakeLogger = new FakeLogger();
            _calculator = new Calculator(_fakeLogger);
        }

        [TestCase("5", "3", "+", 8.0)]
        [TestCase("-10", "5", "+", -5.0)]
        [TestCase("10.5", "2", "*", 21.0)]
        [TestCase("-20", "-2", "/", 10.0)]
        public void Calculate_ValidInputs_ReturnsCorrectResult(string a, string b, string op, double expected)
        {
            Console.WriteLine($"--- Початок тесту: {a} {op} {b} ---");

            double actual = _calculator.Calculate(a, b, op);

            Console.WriteLine($"Результат: Очікувано {expected}, Отримано {actual}");

            Assert.That(actual, Is.EqualTo(expected).Within(0.0001));
            Assert.That(_fakeLogger.Logs.Count, Is.GreaterThan(0), "Логування має бути виконано.");

            Console.WriteLine("--- Тест пройдено успішно ---\n");
        }

        [TestCaseSource(nameof(BoundaryValuesSource))]
        public void Calculate_BoundaryValues_WorksCorrectly(string a, string b, string op, double expected)
        {
            Console.WriteLine($"--- Тест граничних значень: {a} {op} {b} ---");

            double actual = _calculator.Calculate(a, b, op);

            Console.WriteLine($"Результат обчислення: {actual}");
            Assert.That(actual, Is.EqualTo(expected));
        }

        public static IEnumerable<TestCaseData> BoundaryValuesSource()
        {
            yield return new TestCaseData("1e300", "1e300", "*", double.PositiveInfinity);
            yield return new TestCaseData("1.7976931348623157E+308", "0", "+", double.MaxValue);
            yield return new TestCaseData("-1.7976931348623157E+308", "0", "+", double.MinValue);
        }

        [Test]
        public void Calculate_DivideByZero_ThrowsDivideByZeroException()
        {
            Console.WriteLine("--- Тест: Негативний сценарій (Ділення на нуль) ---");
            var ex = Assert.Throws<DivideByZeroException>(() => _calculator.Calculate("10", "0", "/"));
            Console.WriteLine($"Помилка успішно оброблена: {ex.Message}");
            Assert.That(ex.Message, Is.EqualTo(ErrorMessages.DivideByZero));
        }

        [TestCase("abc", "5", "+")]
        public void Calculate_InvalidFormat_ThrowsFormatException(string a, string b, string op)
        {
            Console.WriteLine($"--- Тест: Невалідний формат даних ({a}) ---");
            var ex = Assert.Throws<FormatException>(() => _calculator.Calculate(a, b, op));
            Console.WriteLine($"Система відхилила введення: {ex.Message}");
            Assert.That(ex.Message, Is.EqualTo(ErrorMessages.InvalidFormat));
        }

        [Test]
        public void Calculate_UnknownOperation_ThrowsArgumentException()
        {
            Console.WriteLine("--- Тест: Спроба використати невідому операцію ---");
            var ex = Assert.Throws<ArgumentException>(() => _calculator.Calculate("5", "5", "^"));
            Console.WriteLine($"Операція заблокована: {ex.Message}");
            Assert.That(ex.Message, Is.EqualTo(ErrorMessages.UnknownOperation));
        }
    }

    [TestFixture]
    public class StringProcessorTests
    {
        private StringProcessor _processor;

        [SetUp]
        public void Setup()
        {
            _processor = new StringProcessor();
        }

        [Test]
        public void IsPalindrome_ValidPalindromeIncludingSpaces_ReturnsTrue()
        {
            string input = "А радар а";
            Console.WriteLine($"--- Тест паліндрома: '{input}' ---");
            bool result = _processor.IsPalindrome(input);
            Console.WriteLine($"Результат перевірки: {result}");
            Assert.That(result, Is.True);
        }

        [Test]
        public void IsPalindrome_NotPalindrome_ReturnsFalse()
        {
            string input = "С#.NET";
            Console.WriteLine($"--- Тест на звичайний рядок: '{input}' ---");
            bool result = _processor.IsPalindrome(input);
            Console.WriteLine($"Результат перевірки: {result}");
            Assert.That(result, Is.False);
        }
    }
}