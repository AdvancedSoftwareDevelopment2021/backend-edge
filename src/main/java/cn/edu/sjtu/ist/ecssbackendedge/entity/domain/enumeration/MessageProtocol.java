package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration;

public enum MessageProtocol {

    /**
     * http
     */
    HTTP("http"),

    /**
     * websocket
     */
    WEBSOCKET("websocket"),

    /**
     * canbus
     */
    CANBUS("canbus"),

    /**
     * zigbee
     */
    ZIGBEE("zigbee"),

    /**
     * modbus
     */
    MODBUS("modbus"),

    /**
     * self-defined protocol
     */
    SELF_DEFINED_PROTOCOL("自定义协议");

    /**
     * 类型
     */
    private final String protocol;

    /**
     * 构造函数
     *
     * @param protocol 协议名称
     */
    MessageProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 获取协议
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * 从字符串转为文本
     *
     * @param text 文本
     */
    public static MessageProtocol fromString(String text) {
        for (MessageProtocol b : MessageProtocol.values()) {
            if (b.protocol.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}