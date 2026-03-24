package pingmonitor;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Клас, що виконує логіку пінгування сайту або IP.
 */
public class Pinger {
    public static PingResult pingHost(String host) {
        // Звертаємося до нашого Singleton для отримання налаштувань
        ConfigManager config = ConfigManager.getInstance();
        int pingCount = config.getPingCount();
        int timeout = config.getTimeout();
        int interval = config.getInterval();

        System.out.println("Пінгування хосту: " + host + " (" + pingCount + " разів)...");

        int successfulPings = 0;
        long totalTime = 0;

        for (int i = 0; i < pingCount; i++) {
            try {
                long startTime = System.currentTimeMillis();
                InetAddress address = InetAddress.getByName(host);
                
                // Зверніть увагу: isReachable в Java іноді потребує root(admin) прав
                // для класичного ICMP пінгу. Інакше він намагатиметься стукати на порт 7 (Echo).
                boolean isReachable = address.isReachable(timeout);
                long endTime = System.currentTimeMillis();

                if (isReachable) {
                    successfulPings++;
                    long timeTaken = endTime - startTime;
                    totalTime += timeTaken;
                    System.out.println("  Відповідь від " + host + ": час=" + timeTaken + "мс");
                } else {
                    System.out.println("  Запит до " + host + " - перевищено інтервал очікування.");
                }

                if (i < pingCount - 1) {
                    Thread.sleep(interval); // інтервал між пінгами
                }

            } catch (IOException e) {
                System.out.println("  Помилка мережі (не знайдено/відключено) для " + host);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("  Перервано процес пінгу.");
            }
        }

        boolean isReachableFinal = successfulPings > 0;
        double avgTime = successfulPings > 0 ? (double) totalTime / successfulPings : 0.0;

        return new PingResult(host, isReachableFinal, avgTime);
    }
}
