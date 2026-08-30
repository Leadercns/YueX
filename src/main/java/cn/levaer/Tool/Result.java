package cn.levaer.Tool;

import lombok.Data;

@Data
public class Result {

    private int code;
    private String msg;
    private Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public static Result success(String msg,Object data) {
        return new Result(200, msg, data);
    }

    public static Result error(String message) {
        return new Result(201, message, null);
    }


}
