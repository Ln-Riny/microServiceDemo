package me.lining.learn.interfaces.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import me.lining.learn.domain.service.RpcOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * @author lining
 * @date 2026/02/28 23:57
 */
@RestController
@RequestMapping("/order")
@RocketMQMessageListener(topic = "TestTopic", consumerGroup = "demo-consumer-group")
public class OrderController implements RocketMQListener<String> {

    @Autowired
    private RpcOrderService rpcOrderService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 限流：1秒最多2个请求
    @SentinelResource(value = "createOrder",
            blockHandler = "createOrderBlockHandler",
            fallback = "createOrderFallback") // 降级方法
    @GetMapping("/create/{userId}")
    public String createOrder(@PathVariable Long userId) throws InterruptedException {
        Thread.sleep(600);
        String user = rpcOrderService.getUserById(userId);
        return "创建订单成功，" + user;
    }

    @GetMapping("/rocketmq/send")
    public void sendMessage(@RequestParam String topic, @RequestParam String message) {
        try {
            // 发送同步消息（最常用）
            rocketMQTemplate.convertAndSend(topic, message);
            System.out.println("消息发送成功：" + message);
        } catch (Exception e) {
            System.err.println("消息发送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println("消费者接收到消息：" + message);
        // 此处可添加业务逻辑（如入库、调用接口等）
    }

    // 限流降级方法（参数要和原方法一致，最后加BlockException）
    public String createOrderBlockHandler(Long userId, BlockException e) {
        if (e instanceof BlockException) {
            return "【异常降级】服务调用失败，请稍后再试！用户ID：" + userId;
        } else if (e instanceof FlowException) {
            return "【限流触发】服务繁忙，QPS已达上限，请稍后再试！用户ID：" + userId;
        }
        return "ok";
    }

    // 启动时加载QPS限流规则（@PostConstruct：容器初始化后执行）
    @PostConstruct
    public void initFlowRules() {
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("createOrder");          // 关联资源名
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS); // 限流维度：QPS（可选FLOW_GRADE_THREAD：线程数）
//        rule.setCount(1);                          // QPS阈值：1秒最多2个请求
//        rule.setLimitApp("default");               // 针对来源（default=所有来源）
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);          // 加载规则
//        System.out.println("Sentinel QPS限流规则加载成功：createOrder，QPS=2");

//
        List<DegradeRule> degradeRules = new ArrayList<>();

        // 1. 配置慢调用比例降级规则
        DegradeRule slowCallRule = new DegradeRule();
        slowCallRule.setResource("createOrder");                // 关联资源名
        slowCallRule.setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType()); // 降级类型：慢调用比例
        slowCallRule.setCount(500);                             // 慢调用RT阈值：500ms
        slowCallRule.setSlowRatioThreshold(0.5);                // 慢调用比例阈值：50%
        slowCallRule.setMinRequestAmount(1);                    // 最小请求数：5
        slowCallRule.setTimeWindow(5);                            // 熔断时长：5秒
        degradeRules.add(slowCallRule);

//        // 2. 配置异常比例降级规则
//        DegradeRule exceptionRatioRule = new DegradeRule();
//        exceptionRatioRule.setResource("createOrder");
//        exceptionRatioRule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType()); // 降级类型：异常比例
//        exceptionRatioRule.setCount(0.3);                        // 异常比例阈值：30%
//        exceptionRatioRule.setMinRequestAmount(5);               // 最小请求数：5
//        exceptionRatioRule.setTimeWindow(5);                     // 熔断时长：5秒
//        degradeRules.add(exceptionRatioRule);
//
//        // 3. 配置异常数降级规则
//        DegradeRule exceptionCountRule = new DegradeRule();
//        exceptionCountRule.setResource("createOrder");
//        exceptionCountRule.setGrade(CircuitBreakerStrategy.ERROR_COUNT.getType()); // 降级类型：异常数
//        exceptionCountRule.setCount(10);                         // 异常数阈值：10
//        exceptionCountRule.setTimeWindow(5);                     // 熔断时长：5秒
//        exceptionCountRule.setStatIntervalMs(60000);             // 统计时长：60秒（60000ms）
//        degradeRules.add(exceptionCountRule);

        // 加载降级规则
        DegradeRuleManager.loadRules(degradeRules);
        System.out.println("Sentinel降级规则加载成功：createOrder");
    }
}
