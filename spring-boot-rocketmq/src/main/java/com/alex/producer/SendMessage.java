package com.alex.producer;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-04-30 11:53
 */
@Component
public class SendMessage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DefaultMQProducer defaultProducer;

    @Scheduled(fixedDelay   = 10*1000)
    public void sendMessage(){
        JSONObject reqMessage = new JSONObject();
        reqMessage.put("current_time", new Date());

        String shipNo = reqMessage.toJSONString();

        String message = null;
        try {
            message = new String(shipNo.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Message msg = new Message("user-topic", "white", message.getBytes());

        SendResult result = null;
        try {
            result = defaultProducer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消息id:" + result.getMsgId() + ":" + "," + "发送状态:" + result.getSendStatus());
    }
}
