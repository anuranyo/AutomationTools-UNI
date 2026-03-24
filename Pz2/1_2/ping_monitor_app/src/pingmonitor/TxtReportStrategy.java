package pingmonitor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Реалізація стратегії для генерації звіту у форматі TXT.
 */
public class TxtReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<PingResult> results, String fileName) {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            writer.write("Звіт про пінг-моніторинг\n");
            writer.write("========================\n");
            for (PingResult result : results) {
                writer.write(String.format("Хост: %s | Доступний: %b | Сер. час: %.2f мс\n",
                        result.getHost(), result.isReachable(), result.getAverageTimeMs()));
            }
            System.out.println("TXT звіт успішно збережено у файл " + fileName + ".txt");
        } catch (IOException e) {
            System.err.println("Помилка запису TXT звіту: " + e.getMessage());
        }
    }
}
