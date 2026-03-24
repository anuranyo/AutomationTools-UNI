<?php

namespace Tests\Acceptance;
use Tests\Support\AcceptanceTester;

class FirstCest
{
    /**
     * Виконується перед кожним тестом.
     * Відкриває головну сторінку додатку.
     */
    public function _before(AcceptanceTester $I)
    {
        // Переходимо на головну сторінку
        $I->amOnPage('/index.php');
    }

    /**
     * Перевірка, що головна сторінка завантажується 
     * і містить тексти "Welcome" та "Calculator"
     */
    public function checkMainPageLoadsAndContainsText(AcceptanceTester $I)
    {
        $I->wantTo('перевірити наявність текстів Welcome та Calculator на сторінці');

        // Шукаємо заголовок h1 з текстом Welcome
        $I->see('Welcome', 'h1');
        // Шукаємо заголовок h2 з текстом Calculator
        $I->see('Calculator', 'h2');
    }

    /**
     * Сценарій для перевірки наявності всіх елементів форми (input, select, button) за їхніми ID.
     */
    public function checkFormElementsExistById(AcceptanceTester $I)
    {
        $I->wantTo('перевірити наявність полів вводу, селекту та кнопки за ID');

        // Перевіряємо наявність елементів за CSS селекторами
        $I->seeElement('#number1'); // Поле вводу першого числа
        $I->seeElement('#number2'); // Поле вводу другого числа
        $I->seeElement('#operation'); // Випадаючий список
        $I->seeElement('#calculate-btn'); // Кнопка Calculate
    }

    /**
     * Сценарій взаємодії: введення чисел, вибір операції, 
     * натискання кнопки та перевірка результату.
     * Операція: Додавання.
     */
    public function testAddOperation(AcceptanceTester $I)
    {
        $I->wantTo('перевірити правильність операції додавання через веб-інтерфейс');

        // Вводимо числа
        $I->fillField('#number1', '10');
        $I->fillField('#number2', '15');
        // Вибираємо операцію додавання
        $I->selectOption('#operation', 'add');
        // Натискаємо кнопку
        $I->click('#calculate-btn');

        // Перевіряємо, що на сторінці з'явився правильний результат
        $I->see('Результат: 25', '#result');
    }

    /**
     * Сценарій взаємодії: Віднімання.
     */
    public function testExtractOperation(AcceptanceTester $I)
    {
        $I->wantTo('перевірити правильність операції віднімання через веб-інтерфейс');

        $I->fillField('#number1', '10');
        $I->fillField('#number2', '4');
        $I->selectOption('#operation', 'extract');
        $I->click('#calculate-btn');

        $I->see('Результат: 6', '#result');
    }

    /**
     * Сценарій взаємодії: Множення дробових чисел.
     */
    public function testMultiplyOperation(AcceptanceTester $I)
    {
        $I->wantTo('перевірити правильність операції множення через веб-інтерфейс');

        $I->fillField('#number1', '2.5');
        $I->fillField('#number2', '4');
        $I->selectOption('#operation', 'multiply');
        $I->click('#calculate-btn');

        $I->see('Результат: 10', '#result');
    }

    /**
     * Сценарій взаємодії: Ділення.
     */
    public function testDivideOperation(AcceptanceTester $I)
    {
        $I->wantTo('перевірити правильність операції ділення через веб-інтерфейс');

        $I->fillField('#number1', '20');
        $I->fillField('#number2', '5');
        $I->selectOption('#operation', 'divide');
        $I->click('#calculate-btn');

        $I->see('Результат: 4', '#result');
    }
}
