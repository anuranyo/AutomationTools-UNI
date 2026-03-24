<?php

/**
 * Клас для виконання базових арифметичних операцій калькулятора.
 */
class CalculatorClass {
    /**
     * Метод для додавання двох чисел
     */
    public function add($a, $b) {
        return $a + $b;
    }

    /**
     * Метод для віднімання двох чисел
     */
    public function extract($a, $b) {
        return $a - $b;
    }

    /**
     * Метод для множення двох чисел
     */
    public function multiply($a, $b) {
        return $a * $b;
    }

    /**
     * Метод для ділення двох чисел
     * Викидає виняток при спробі ділення на нуль
     */
    public function divide($a, $b) {
        if ($b == 0) {
            throw new InvalidArgumentException("Ділення на нуль неможливе.");
        }
        return $a / $b;
    }
}
