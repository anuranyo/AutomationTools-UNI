package pingmonitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас програми, що реалізує життєвий цикл.
 */
public class PingMonitorApp {
    public static void main(String[] args) {
        // Форсуємо вивід в консоль у кодуванні UTF-8 для правильного відображення української мови в Windows
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
        
        System.out.println("=== Програма для пінг-моніторингу ===");

        // 1. Data-Driven: Читаємо список хостів з текстового файлу
        List<String> hosts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("hosts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    hosts.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка читання файлу hosts.txt: " + e.getMessage());
            System.err.println("Створіть файл hosts.txt в корені проєкту!");
            return;
        }

        if (hosts.isEmpty()) {
            System.out.println("Список хостів порожній!");
            return;
        }

        // 2. Логіка: пінгуємо кожен хост
        List<PingResult> results = new ArrayList<>();
        for (String host : hosts) {
            PingResult result = Pinger.pingHost(host);
            results.add(result);
            System.out.println("----------------------------------------");
        }

        // 3. Питаємо користувача про формат звіту (демонстрація патернів Factory і Strategy)
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nОберіть формат звіту (введіть TXT, XML або HTML): ");
        String format = scanner.nextLine();

        // Отримуємо "рецепт" звіту за допомогою Патерну Factory
        ReportStrategy strategy = ReportFactory.getReportStrategy(format);

        // 4. Патерн Strategy: Генеруємо звіт
        // (контекст просто викликає .generateReport, логіка лежить в обраному класі)
        strategy.generateReport(results, "ping_report");

        System.out.println("Програму успішно завершено!");
        scanner.close();
    }
}
