package cn.arros.server.common;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

/**
 * @Author Verge
 * @Date 2021/10/31 16:11
 * @Version 1.0
 */
public class CommonResult extends HashMap<String, Object> {
    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 CommonResult 对象，使其表示一个空消息。
     */
    public CommonResult() {
    }

    /**
     * 初始化一个新创建的 CommonResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public CommonResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 CommonResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public CommonResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (!ObjectUtils.isEmpty(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CommonResult success()
    {
        return CommonResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static CommonResult success(Object data)
    {
        return CommonResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static CommonResult success(String msg)
    {
        return CommonResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static CommonResult success(String msg, Object data)
    {
        return new CommonResult(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static CommonResult error()
    {
        return CommonResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CommonResult error(String msg)
    {
        return CommonResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static CommonResult error(String msg, Object data)
    {
        return new CommonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CommonResult error(int code, String msg)
    {
        return new CommonResult(code, msg, null);
    }

}
