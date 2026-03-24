<?php
require_once 'CalculatorClass.php';

$result = null;

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $num1 = isset($_POST['number1']) ? (float)$_POST['number1'] : 0;
    $num2 = isset($_POST['number2']) ? (float)$_POST['number2'] : 0;
    $operation = isset($_POST['operation']) ? $_POST['operation'] : '';

    $calculator = new CalculatorClass();

    try {
        switch ($operation) {
            case 'add':
                $result = $calculator->add($num1, $num2);
                break;
            case 'extract':
                $result = $calculator->extract($num1, $num2);
                break;
            case 'multiply':
                $result = $calculator->multiply($num1, $num2);
                break;
            case 'divide':
                $result = $calculator->divide($num1, $num2);
                break;
            default:
                $result = "Невідома операція";
        }
    } catch (Exception $e) {
        $result = "Помилка: " . $e->getMessage();
    }
}
?>

<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculator App</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 100px; }
        .result-box { margin-top: 20px; font-weight: bold; color: green; }
        .error-box { color: red; }
    </style>
</head>
<body>
    <h1>Welcome</h1>
    <h2>Calculator</h2>

    <!-- Форма для обчислень -->
    <form method="POST" action="index.php">
        <div class="form-group">
            <label for="number1">Number 1:</label>
            <!-- Поле вводу першого числа -->
            <input type="number" step="any" id="number1" name="number1" required value="<?php echo isset($_POST['number1']) ? htmlspecialchars($_POST['number1']) : ''; ?>">
        </div>
        
        <div class="form-group">
            <label for="operation">Operation:</label>
            <!-- Випадаючий список з операціями -->
            <select id="operation" name="operation" required>
                <option value="add" <?php echo (isset($_POST['operation']) && $_POST['operation'] == 'add') ? 'selected' : ''; ?>>Додавання (+)</option>
                <option value="extract" <?php echo (isset($_POST['operation']) && $_POST['operation'] == 'extract') ? 'selected' : ''; ?>>Віднімання (-)</option>
                <option value="multiply" <?php echo (isset($_POST['operation']) && $_POST['operation'] == 'multiply') ? 'selected' : ''; ?>>Множення (*)</option>
                <option value="divide" <?php echo (isset($_POST['operation']) && $_POST['operation'] == 'divide') ? 'selected' : ''; ?>>Ділення (/)</option>
            </select>
        </div>

        <div class="form-group">
            <label for="number2">Number 2:</label>
            <!-- Поле вводу другого числа -->
            <input type="number" step="any" id="number2" name="number2" required value="<?php echo isset($_POST['number2']) ? htmlspecialchars($_POST['number2']) : ''; ?>">
        </div>

        <!-- Кнопка розрахунку -->
        <button type="submit" id="calculate-btn">Calculate</button>
    </form>

    <!-- Виведення результату -->
    <?php if ($result !== null): ?>
        <div class="result-box" id="result">
            <?php 
                if (is_numeric($result)) {
                    echo "Результат: " . htmlspecialchars($result);
                } else {
                    echo "<span class='error-box'>" . htmlspecialchars($result) . "</span>";
                }
            ?>
        </div>
    <?php endif; ?>
</body>
</html>
