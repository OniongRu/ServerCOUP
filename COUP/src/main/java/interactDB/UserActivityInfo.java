package interactDB;

import java.time.LocalDateTime;

public class UserActivityInfo
{
    private String name;
    private Double cpuUsage;
    private Long ramUsage;
    private Integer threadAmount;
    private Integer activeWindowTime;
    private Integer windowTime;
    private Integer packagesAmount;
    private LocalDateTime creationDate;

    public UserActivityInfo(String name, LocalDateTime creationDate, Double cpuUsage, Long ramUsage, Integer threadAmount, Integer activeWindowTime, Integer windowTime, Integer packagesAmount) {
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

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public Long getRamUsage() {
        return ramUsage;
    }

    public Integer getThreadAmount() {
        return threadAmount;
    }

    public Integer getActiveWindowTime() {
        return activeWindowTime;
    }

    public Integer getWindowTime() {
        return windowTime;
    }

    public Integer getPackagesAmount() {
        return packagesAmount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
