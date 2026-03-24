package pingmonitor;

/**
 * Патерн Factory (Фабрика).
 * Інкапсулює логіку вибору/створення потрібного формату звіту.
 */
public class ReportFactory {
    public static ReportStrategy getReportStrategy(String reportType) {
        if (reportType == null) {
            return new TxtReportStrategy(); // За замовчуванням
        }
        
        switch (reportType.toUpperCase()) {
            case "XML":
                return new XmlReportStrategy();
            case "HTML":
                return new HtmlReportStrategy();
            case "TXT":
            default:
                return new TxtReportStrategy(); // За замовчуванням
        }
    }
}
