package pingmonitor;

/**
 * Клас для збереження результатів пінгування одного сайту або IP-адреси.
 */
public class PingResult {
    private String host;
    private boolean reachable;
    private double averageTimeMs;

    public PingResult(String host, boolean reachable, double averageTimeMs) {
        this.host = host;
        this.reachable = reachable;
        this.averageTimeMs = averageTimeMs;
    }

    public String getHost() {
        return host;
    }

    public boolean isReachable() {
        return reachable;
    }

    public double getAverageTimeMs() {
        return averageTimeMs;
    }
}
