package com.demo.boot.base;

import java.io.Serializable;

/**
 * s
 *
 * @Author ddmc
 * @Create 2019-03-06 13:42
 */
public class JsonResult<T> implements Serializable {

    private static final String SUCCESS_CODE = "0";

    private boolean success;
    private String code;
    private String message;
    private T data;

    public JsonResult() {
    }

    public JsonResult(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(true, SUCCESS_CODE, null, null);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(true, SUCCESS_CODE, null, data);
    }

    public static <T> JsonResult<T> fail(String code, String message) {
        return new JsonResult<>(false, code, message, null);
    }

    public static <T> JsonResult<T> fail(String code, String message, T data) {
        return new JsonResult<>(false, code, message, data);
    }

    public static String getSuccessCode() {
        return SUCCESS_CODE;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}