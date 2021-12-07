package cn.edu.sjtu.ist.ecssbackendedge.model.process;

public enum ElementType {
    /**
     * device key in bpmn extension
     */
    DEVICE_KEY("deviceId");

    private String key;

    ElementType(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
