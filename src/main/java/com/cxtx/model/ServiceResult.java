package com.cxtx.model;

/**
 * Created by jinchuyang on 16/10/19.
 */
public class ServiceResult {

    public final static ServiceResult SERVICE_RESULT_500 = fail("");

    public final static ServiceResult SERVICE_RESULT_EMPTY_200 = success("");

    public static ServiceResult success(Object data) {
        return new ServiceResult(200, data);
    }

    public static ServiceResult fail(int code, Object data) {
        return new ServiceResult(code, data);
    }

    public static ServiceResult fail(Object data) {
        return fail(500, data);
    }

    private int code;

    private Object data;

    public ServiceResult() {

    }

    public ServiceResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
