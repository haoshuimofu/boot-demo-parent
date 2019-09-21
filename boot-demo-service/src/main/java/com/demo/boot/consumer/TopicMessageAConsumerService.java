package com.demo.boot.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.boot.messagebody.TopicMessageABody;
import com.demo.components.rabbitmq.BaseConsumerService;
import com.demo.components.rabbitmq.annotation.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ddmc
 * @Create 2019-05-29 13:48
 */
@ConsumerService
public class TopicMessageAConsumerService extends BaseConsumerService<TopicMessageABody> {

    Logger logger = LoggerFactory.getLogger(TopicMessageAConsumerService.class);

    @Override
    public void handleMessage(TopicMessageABody messageBody) {
        logger.info("Topic消息A: {}", JSON.toJSONString(messageBody));
    }

}