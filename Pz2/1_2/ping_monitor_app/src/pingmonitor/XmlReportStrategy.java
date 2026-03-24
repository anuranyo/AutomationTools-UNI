package pingmonitor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Реалізація стратегії для генерації звіту у форматі XML.
 */
public class XmlReportStrategy implements ReportStrategy {
    @Override
    public void generateReport(List<PingResult> results, String fileName) {
        try (FileWriter writer = new FileWriter(fileName + ".xml")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<PingResults>\n");
            for (PingResult result : results) {
                writer.write("  <Result>\n");
                writer.write("    <Host>" + result.getHost() + "</Host>\n");
                writer.write("    <Reachable>" + result.isReachable() + "</Reachable>\n");
                writer.write("    <AverageTimeMs>" + result.getAverageTimeMs() + "</AverageTimeMs>\n");
                writer.write("  </Result>\n");
            }
            writer.write("</PingResults>\n");
            System.out.println("XML звіт успішно збережено у файл " + fileName + ".xml");
        } catch (IOException e) {
            System.err.println("Помилка запису XML звіту: " + e.getMessage());
        }
    }
}
