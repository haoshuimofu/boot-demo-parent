package com.demo.boot.web;

import com.demo.boot.base.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author ddmc
 * @Create 2019-02-28 16:53
 */
@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);


    /**
     * 参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        response.setStatus(500);
        System.out.println(response.getStatus());
        logger.error("参数异常", e);
        List<String> errorMsgList = new ArrayList<>();
        StringBuilder msgBuilder = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(element -> {
            msgBuilder.append(element.getField()).append(": ");
            msgBuilder.append((element.getArguments().length > 1 ? String.format(element.getDefaultMessage(), Arrays.copyOfRange(element.getArguments(), 1, element.getArguments().length)) : element.getDefaultMessage()));
            errorMsgList.add(msgBuilder.toString());
            msgBuilder.setLength(0);
        });
        return JsonResult.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), StringUtils.join(errorMsgList, "; "));
    }

    /**
     * 参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult exceptionHandler(Exception e) {
        e.printStackTrace();
        return JsonResult.fail("9999", "服务走神了，请稍后重试！");
    }

}