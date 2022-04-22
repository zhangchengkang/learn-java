package com.ksf.res;


import java.util.Objects;

public class WebResponse<T> {

    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "成功";

    public static final int ERROR_CODE = 1;

    private int code;
    private String msg;
    private T data;

    public static <T> WebResponse<T> success(T data) {
        WebResponse<T> response = new WebResponse<>();
        response.setCode(SUCCESS_CODE);
        response.setMsg(SUCCESS_MSG);
        response.setData(data);
        return response;
    }

    public static <T> WebResponse<T> error(String errorMessage) {
        return WebResponse.error(ERROR_CODE, errorMessage);
    }

    public static <T> WebResponse<T> error(int errorCode, String errorMessage) {
        WebResponse<T> response = new WebResponse<>();
        response.setCode(errorCode);
        response.setMsg(errorMessage);
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebResponse<?> that = (WebResponse<?>) o;

        if (code != that.code) return false;
        if (!Objects.equals(msg, that.msg)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WebResponse{" +
                "code=" + code +
                ", error='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
