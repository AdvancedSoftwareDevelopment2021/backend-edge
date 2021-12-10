package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration;

public enum Status {

    NONE(""),

    /**
     * 未连接、未监听
     */
    SLEEP("sleeping"),

    /**
     * 已连接、已监听、正常运行中
     */
    RUNNING("running"),

    /**
     * 收集数据中
     */
    COLLECTING("collecting"),

    /**
     * 收集数据成功、设备运行正常
     */
    SUCCESS("success"),

    /**
     * 收集数据失败、设备运行故障
     */
    FAILURE("failure");

    Status(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }

    /**
     * 从字符串表示转为枚举
     *
     * @param text 字符串表示
     */
    public static Status fromString(String text) {
        for (Status status : Status.values()) {
            if (status.type.equals(text)) {
                return status;
            }
        }
        return NONE;
    }
}
