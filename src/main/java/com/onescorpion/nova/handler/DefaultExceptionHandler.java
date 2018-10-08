package com.onescorpion.nova.handler;


import com.onescorpion.nova.exception.DefaultException;
import com.onescorpion.nova.result.Result;
import com.onescorpion.nova.result.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice   //声明此类用于捕获异常
public class DefaultExceptionHandler {

	public static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@ExceptionHandler(value=Exception.class)//要捕获的异常类型
	@ResponseBody // 因为要给接口请求方信息 所以加个这个 以JSON格式返回给请求发起方
	public Result handlerException(Exception e){
		logger.info("exception={}",e);
		if(e instanceof DefaultException){
			DefaultException defaultException = (DefaultException)e;
			return  ResultFactory.error(defaultException.getCode(),defaultException.getMessage());
		}else{
			return  ResultFactory.error();
		}
	}

}
