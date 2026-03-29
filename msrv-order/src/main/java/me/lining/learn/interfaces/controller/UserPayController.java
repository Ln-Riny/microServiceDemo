package me.lining.learn.interfaces.controller;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lining
 * @date 2026/03/12 22:18
 */
@RestController
@RequestMapping("/pay")
public class UserPayController implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}
