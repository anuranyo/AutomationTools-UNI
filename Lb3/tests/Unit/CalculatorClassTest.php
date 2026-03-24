<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/../../CalculatorClass.php';

class CalculatorClassTest extends TestCase {
    private $calculator;

    /**
     * Ініціалізація об'єкта перед кожним тестом
     */
    protected function setUp(): void {
        $this->calculator = new CalculatorClass();
    }

    /**
     * Очищення після кожного тесту
     */
    protected function tearDown(): void {
        $this->calculator = null;
    }

    // --- Тести для методу add() ---

    // 1. Позитивний тест: додавання цілих чисел
    public function testAddPositiveIntegers() {
        $result = $this->calculator->add(5, 7);
        $this->assertEquals(12, $result, "5 + 7 має дорівнювати 12");
    }

    // 2. Позитивний тест: додавання дробових чисел
    public function testAddFloats() {
        $result = $this->calculator->add(1.5, 2.3);
        $this->assertEqualsWithDelta(3.8, $result, 0.0001, "1.5 + 2.3 має дорівнювати 3.8");
    }

    // 3. Негативний тест: додавання (перевірка на неправильний результат)
    public function testAddNegativeCheck() {
        $result = $this->calculator->add(5, 5);
        $this->assertNotEquals(11, $result, "5 + 5 не повинно дорівнювати 11");
    }

    // --- Тести для методу extract() ---

    // 4. Позитивний тест: віднімання цілих чисел
    public function testExtractIntegers() {
        $result = $this->calculator->extract(10, 4);
        $this->assertEquals(6, $result, "10 - 4 має дорівнювати 6");
    }

    // 5. Позитивний тест: віднімання так, щоб результат був від'ємним
    public function testExtractResultNegative() {
        $result = $this->calculator->extract(3, 8);
        $this->assertEquals(-5, $result, "3 - 8 має дорівнювати -5");
    }

    // 6. Негативний тест: віднімання (перевірка неправильного результату)
    public function testExtractNegativeCheck() {
        $result = $this->calculator->extract(10, 5);
        $this->assertNotEquals(4, $result, "10 - 5 не повинно дорівнювати 4");
    }

    // --- Тести для методу multiply() ---

    // 7. Позитивний тест: множення цілих чисел
    public function testMultiplyIntegers() {
        $result = $this->calculator->multiply(4, 3);
        $this->assertEquals(12, $result, "4 * 3 має дорівнювати 12");
    }

    // 8. Позитивний тест: множення на нуль
    public function testMultiplyByZero() {
        $result = $this->calculator->multiply(5, 0);
        $this->assertEquals(0, $result, "5 * 0 має дорівнювати 0");
    }

    // 9. Негативний тест: множення (неправильний результат)
    public function testMultiplyNegativeCheck() {
        $result = $this->calculator->multiply(2, 3);
        $this->assertNotEquals(5, $result, "2 * 3 не повинно дорівнювати 5");
    }

    // --- Тести для методу divide() ---

    // 10. Позитивний тест: ділення цілих чисел без залишку
    public function testDivideIntegers() {
        $result = $this->calculator->divide(20, 4);
        $this->assertEquals(5, $result, "20 / 4 має дорівнювати 5");
    }

    // 11. Позитивний тест: ділення дробових чисел
    public function testDivideFloats() {
        $result = $this->calculator->divide(5.5, 2);
        $this->assertEqualsWithDelta(2.75, $result, 0.0001, "5.5 / 2 має дорівнювати 2.75");
    }

    // 12. Негативний тест: ділення (неправильний результат)
    public function testDivideNegativeCheck() {
        $result = $this->calculator->divide(10, 2);
        $this->assertNotEquals(4, $result, "10 / 2 не повинно дорівнювати 4");
    }

    // 13. Тест на виняток: ділення на нуль
    public function testDivideByZeroThrowsException() {
        $this->expectException(InvalidArgumentException::class);
        $this->expectExceptionMessage("Ділення на нуль неможливе.");
        $this->calculator->divide(10, 0);
    }
}
