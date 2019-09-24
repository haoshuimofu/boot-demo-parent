package com.demo.boot.web.controller;

import com.demo.boot.base.JsonResult;
import com.demo.boot.messagebody.TopicMessageABody;
import com.demo.components.rabbitmq.RabbitmqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ddmc
 * @date 2019/9/24 14:22
 */
@RestController
@RequestMapping("mq/test")
public class RabbitmqTestController {

    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @RequestMapping("send")
    public JsonResult testSendMsssage(HttpServletRequest request) {
        TopicMessageABody messageABody = new TopicMessageABody();
        messageABody.setSender(request.getRemoteAddr());
        rabbitmqProducer.send(messageABody);
        return JsonResult.success(request.getRemoteHost());
    }

}