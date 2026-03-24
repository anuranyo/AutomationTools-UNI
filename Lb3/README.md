# Інструкція по запуску тестів (Лабораторна 3)

### 1. Встановлення залежностей
Переконайтеся, що на вашому комп'ютері встановлені **PHP** та **Composer**. 
Відкрийте термінал у папці проєкту (`d:/Projects/AutomationTools/Lb3`) та виконайте:
```bash
composer install
```
Це встановить PHPUnit та Codeception у папку `vendor`.

### 2. Запуск Unit-тестів (PHPUnit)
Для запуску юніт-тестів для класу `CalculatorClass` виконайте команду:
```bash
./vendor/bin/phpunit tests/Unit/CalculatorClassTest.php
```

### 3. Запуск веб-сервера для Acceptance-тестів
Оскільки Acceptance-тести перевіряють роботу в браузері, спочатку треба запустити вбудований PHP сервер. Залиште цей термінал відкритим:
```bash
php -S 127.0.0.1:8000 -t d:/Projects/AutomationTools/Lb3
```

### 4. Запуск Acceptance-тестів (Codeception)
Перед першим запуском ініціалізуйте Codeception (якщо ще не робили):
```bash
./vendor/bin/codecept bootstrap
```

Відкрийте **нове** вікно терміналу у папці `d:/Projects/AutomationTools/Lb3`. 
Запустіть приймальні тести:
```bash
./vendor/bin/codecept run acceptance
```
Або для конкретного файлу:
```bash
./vendor/bin/codecept run tests/Acceptance/FirstCest.php
```

Якщо Codeception буде просити налаштування з'єднання в `tests/Acceptance.suite.yml`, змініть `url` для `PhpBrowser` на `http://127.0.0.1:8000`.
