package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver;

/**
 * @author dyanjun
 * @date 2021/12/25 21:46
 */
public enum DriverStatus {
    SLEEP("SLEEP"),

    RUNNING("RUNNING"),

    DELIVERED("DELIVERED"),

    SUCCESSFUL("SUCCESSFUL"),

    FAIL("FAIL");

    /**
     * 状态
     */
    private final String status;

    /**
     * 构造函数
     *
     * @param status 指令状态
     */
    DriverStatus(String status) {
        this.status = status;
    }

    /**
     * 获取指令状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 从字符串表示转为枚举
     *
     * @param text 字符串表示
     */
    public static DriverStatus fromString(String text) {
        for (DriverStatus b : DriverStatus.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
