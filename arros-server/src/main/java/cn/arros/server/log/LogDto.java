package cn.arros.server.log;

/**
 * @Author Verge
 * @Date 2021/11/25 20:50
 * @Version 1.0
 */
public class LogDto {
    private String log;
    private String buildHistoryId;

    public LogDto(String log, String buildHistoryId) {
        this.log = log;
        this.buildHistoryId = buildHistoryId;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getBuildHistoryId() {
        return buildHistoryId;
    }

    public void setBuildHistoryId(String buildHistoryId) {
        this.buildHistoryId = buildHistoryId;
    }
}
