package pingmonitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Патерн Singleton (Одинак).
 * Відповідає за зручний доступ до налаштувань з файлу config.properties.
 * Гарантує, що в пам'яті буде лише один об'єкт для роботи з конфігурацією.
 */
public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;

    // Приватний конструктор (ніхто не зможе створити об'єкт через new)
    private ConfigManager() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Помилка читання config.properties. Використовуються стандартні значення.");
        }
    }

    // Глобальна точка доступу до екземпляра
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public int getInterval() {
        return Integer.parseInt(properties.getProperty("ping.interval.ms", "1000"));
    }

    public int getPingCount() {
        return Integer.parseInt(properties.getProperty("ping.count", "4"));
    }

    public int getTimeout() {
        return Integer.parseInt(properties.getProperty("ping.timeout.ms", "3000"));
    }
}
