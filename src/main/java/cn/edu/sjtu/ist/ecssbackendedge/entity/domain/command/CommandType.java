package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command;

/**
 * @author dyanjun
 * @date 2021/12/25 21:47
 */
public enum CommandType {

    PROPERTY("PROPERTY"),

    CUSTOM("CUSTOM");

    /**
     * 类型
     */
    private final String type;

    /**
     * 构造函数
     *
     * @param type 指令类型
     */
    CommandType(String type) {
        this.type = type;
    }

    /**
     * 获取指令类型
     */
    public String getType() {
        return type;
    }

    /**
     * 从字符串表示转为枚举
     *
     * @param text 字符串表示
     */
    public static CommandType fromString(String text) {
        for (CommandType b : CommandType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
