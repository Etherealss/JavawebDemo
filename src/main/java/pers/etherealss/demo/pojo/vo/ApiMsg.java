package pers.etherealss.demo.pojo.vo;

import lombok.Getter;
import lombok.ToString;
import pers.etherealss.demo.common.enums.ApiInfo;

import java.io.Serializable;

/**
 * @author wtk
 * @description 统一的接口信息包，用于前后端交互
 * @date 2021-08-12
 */
@Getter
@ToString
public class ApiMsg<T> implements Serializable {
    /** 结果码，用于判断响应情况 */
    private int code;

    /** 响应情况说明，如“用户密码错误” */
    private String message;

    /** 响应的数据，可能为空 */
    private T data;

    public static <T> ApiMsg<T> ok() {
        return new ApiMsg<>(ApiInfo.OK);
    }

    public static <T> ApiMsg<T> ok(T data) {
        return new ApiMsg<T>(ApiInfo.OK).setData(data);
    }

    public static <T> ApiMsg<T> ok(String message) {
        return new ApiMsg<>(ApiInfo.OK, message);
    }

    /**
     * 用户未登录
     * @param <T>
     * @return
     */
    public static <T> ApiMsg<T> userUnlogged() {
        return new ApiMsg<>(ApiInfo.UNAUTHORIZED);
    }

    public static <T> ApiMsg<T> exception() {
        return new ApiMsg<>();
    }

    public static <T> ApiMsg<T> exception(String message) {
        return new ApiMsg<>(ApiInfo.SERVER_ERROR, message);
    }

    /**
     * 出现异常
     * @param e
     * @param <T>
     * @return
     */
    public static <T> ApiMsg<T> exception(Throwable e) {
        return new ApiMsg<>(ApiInfo.SERVER_ERROR, e.getMessage());
    }

    /**
     * 找不到错误
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ApiMsg<T> notFound(String message) {
        return new ApiMsg<>(ApiInfo.NOT_FOUND, message);
    }

    /**
     * 参数错误
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ApiMsg<T> paramsError(String message) {
        return new ApiMsg<>(ApiInfo.ERROR_PARAM, message);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ApiMsg<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiMsg<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 默认访问成功 OK
     */
    public ApiMsg() {
        this(ApiInfo.OK);
    }

    /**
     * 默认访问成功 OK
     */
    public ApiMsg(T data) {
        this(ApiInfo.OK);
        this.data = data;
    }

    public ApiMsg(ApiInfo apiInfo) {
        this.code = apiInfo.getCode();
        this.message = apiInfo.getMessage();
    }


    public ApiMsg(ApiInfo apiInfo, String description) {
        this.code = apiInfo.getCode();
        this.message = apiInfo.getMessage() + ": " + description;
    }

    public ApiMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 使用非自定义的其他异常生成ApiMsg
     * 手动拼接ApiInfo的说明和自定义描述
     * @param throwable
     */
    public ApiMsg(Throwable throwable) {
        this.code = ApiInfo.SERVER_ERROR.getCode();
        String message = ApiInfo.SERVER_ERROR.getMessage();
        if (throwable.getMessage().length() > 0) {
            message += (": " + throwable.getMessage());
        }
        this.message = message;
    }
}
