package pingmonitor;

import java.util.List;

/**
 * Патерн Strategy (Стратегія).
 * Спільний інтерфейс для різних форматів обміну/генерації звітів.
 */
public interface ReportStrategy {
    void generateReport(List<PingResult> results, String fileName);
}
