package com.demo.boot.web;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ddmc
 * @Create 2019-02-28 16:53
 */
@ControllerAdvice
public class CommonExceptionHandler {


    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandler(Exception e) {
        e.printStackTrace();

        Map<String, Object> result = new HashMap<String, Object>();


        result.put("respCode", "9999");
        if (e.getClass().getCanonicalName().equals(MethodArgumentNotValidException.class.getCanonicalName())) {
            MethodArgumentNotValidException re = (MethodArgumentNotValidException) e;
            StringBuilder sb = new StringBuilder();
            re.getBindingResult().getFieldErrors().forEach(element ->{
                sb.append(element.getField() + "; " + element.getDefaultMessage() + "; ");
            });
            result.put("respMsg", sb.toString());
        }  else {
            result.put("respMsg", "程序出错了！");
        }


        //正常开发中，可创建一个统一响应实体，如CommonResp

        return result;

    }

}