package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command;

/**
 * @author dyanjun
 * @date 2021/12/25 21:46
 */
public enum CommandStatus {

    SENT("PROPERTY"),

    DELIVERED("CUSTOM");

    /**
     * 状态
     */
    private final String status;

    /**
     * 构造函数
     *
     * @param status 指令状态
     */
    CommandStatus(String status) {
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
    public static CommandStatus fromString(String text) {
        for (CommandStatus b : CommandStatus.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
