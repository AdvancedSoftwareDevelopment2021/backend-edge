package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

public class Response {

    private Long code;

    private String message;

    private Object object;

    public Response(Long code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    @Override
    public String toString() {
        return "code=" + code + ", message=" + message + ", response=" + object.toString();
    }
}
