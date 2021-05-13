package interactDB;

import java.time.LocalDateTime;

public class UserActivityInfo
{
    private String name;
    private double cpuUsage;
    private long ramUsage;
    private int threadAmount;
    private int activeWindowTime;
    private int windowTime;
    private int packagesAmount;
    private LocalDateTime creationDate;

    public UserActivityInfo(String name, LocalDateTime creationDate, double cpuUsage, long ramUsage, int threadAmount, int activeWindowTime, int windowTime, int packagesAmount) {
        this.name = name;
        this.cpuUsage = cpuUsage;
        this.ramUsage = ramUsage;
        this.threadAmount = threadAmount;
        this.activeWindowTime = activeWindowTime;
        this.windowTime = windowTime;
        this.packagesAmount = packagesAmount;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public long getRamUsage() {
        return ramUsage;
    }

    public int getThreadAmount() {
        return threadAmount;
    }

    public int getActiveWindowTime() {
        return activeWindowTime;
    }

    public int getWindowTime() {
        return windowTime;
    }

    public int getPackagesAmount() {
        return packagesAmount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
