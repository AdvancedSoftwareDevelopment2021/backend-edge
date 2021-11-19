package cn.edu.sjtu.ist.ecssbackendedge.model.enumeration;

public enum Status {

    NONE(""),

    SLEEP("sleeping"),

    RUNNING("running"),

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
     * @param text 字符串表示
     */
    public static Status fromString(String text) {
        for (Status status: Status.values()) {
            if (status.type.equals(text)) {
                return status;
            }
        }
        return NONE;
    }
}
