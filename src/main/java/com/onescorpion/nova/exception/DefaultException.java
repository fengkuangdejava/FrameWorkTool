package com.onescorpion.nova.exception;

import com.onescorpion.nova.result.ResultEnum;

/**
 * 默认异常
 *
 *@author 李挺 【fengkuangdejava@outlook.com】
 *@date 2018/3/26 11:08
 */
public class DefaultException extends RuntimeException {
    private int code;

    public DefaultException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public int getCode(){
        return this.code;
    }
}
