package com.onescorpion.nova.result;

/**
 * 返回信息枚举类 用于统一管理返回状态码和异常提示信息
 *
 *@author 李挺 【fengkuangdejava@outlook.com】
 *@date 2018/10/8 10:20
 */
public enum ResultEnum {
    SUCCESS(200,"success"),
    UNKNOWN_ERROR(300,"未知错误"),
    FILE_NOT_FOUND(301,"找不到文件"),
    HTTP_SUCCESS(201,"请求第三方服务成功"),
    SERVICE_CONNECT_ERROR(302,"连接第三方服务失败");

    private  int code;
    private  String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
