package com.alex.cusumer;

import com.alex.config.MessageEvent;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述:
 *
 * @author hany
 * @create 2019-04-30 11:53
 */
@Component
public class ReveiveMessage {

    @EventListener(condition = "#event.msgs[0].topic=='user-topic' && #event.msgs[0].tags=='white'")
    public void rocketmqMsgListener(MessageEvent event) {
        try {
            List<MessageExt> msgs = event.getMsgs();
            for (MessageExt msg : msgs) {
                String shipStr = new String(msg.getBody());
                System.err.println("消费消息:" + shipStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
