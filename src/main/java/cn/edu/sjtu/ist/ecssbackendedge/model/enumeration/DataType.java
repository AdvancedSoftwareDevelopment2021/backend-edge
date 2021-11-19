package cn.edu.sjtu.ist.ecssbackendedge.model.enumeration;

public enum DataType {

    BOOLEAN("boolean"),

    INTEGER("integer"),

    STRING("string"),

    OBJECT("object");

    /**
     * 类型
     */
    private final String type;

    /**
     * 构造函数
     * @param type 数据类型
     */
    DataType(String type) {
        this.type = type;
    }

    /**
     * 获取协议
     */
    public String getType() {
        return type;
    }

    /**
     * 从字符串表示转为枚举
     * @param text 字符串表示
     */
    public static DataType fromString(String text) {
        for (DataType b : DataType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
