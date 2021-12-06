package cn.edu.sjtu.ist.ecssbackendedge.model.enumeration;

/**
 * @author dyanjun
 * @date 2021/11/20 19:05
 */
public enum AsynDataStatus {
    WAITING_DATA("waitingData");

    /**
     * 异步数据状态
     */
    private final String dataStatus;

    /**
     * 构造函数
     *
     * @param dataStatus 异步数据状态
     */
    AsynDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    /**
     * 获取异步数据状态
     */
    public String getDataStatus() {
        return dataStatus;
    }

    /**
     * 从字符串表示转为枚举
     *
     * @param text 字符串表示
     */
    public static AsynDataStatus fromString(String text) {
        for (AsynDataStatus b : AsynDataStatus.values()) {
            if (b.dataStatus.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
