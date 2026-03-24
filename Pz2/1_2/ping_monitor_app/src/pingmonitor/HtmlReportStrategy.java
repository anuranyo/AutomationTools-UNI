package pingmonitor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Реалізація стратегії для генерації звіту у форматі HTML.
 */
public class HtmlReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<PingResult> results, String fileName) {
        try (FileWriter writer = new FileWriter(fileName + ".html")) {
            writer.write("<!DOCTYPE html>\n<html lang=\"uk\">\n");
            writer.write("<head><meta charset=\"UTF-8\"><title>Звіт про пінг-моніторинг</title></head>\n<body>\n");
            writer.write("<h2>Звіт про пінг-моніторинг</h2>\n");
            writer.write("<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\">\n");
            writer.write("<tr><th>Хост (IP / URL)</th><th>Доступність</th><th>Середній час (мс)</th></tr>\n");
            
            for (PingResult result : results) {
                String color = result.isReachable() ? "green" : "red";
                String reachableText = result.isReachable() ? "Так" : "Ні";
                
                writer.write("<tr>");
                writer.write("<td>" + result.getHost() + "</td>");
                writer.write("<td style=\"color:" + color + ";\">" + reachableText + "</td>");
                writer.write("<td>" + String.format("%.2f", result.getAverageTimeMs()) + "</td>");
                writer.write("</tr>\n");
            }
            
            writer.write("</table>\n</body>\n</html>\n");
            System.out.println("HTML звіт успішно збережено у файл " + fileName + ".html");
        } catch (IOException e) {
            System.err.println("Помилка запису HTML звіту: " + e.getMessage());
        }
    }
}
