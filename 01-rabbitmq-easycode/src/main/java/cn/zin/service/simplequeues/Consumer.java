package cn.zin.service.simplequeues;

import cn.zin.service.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author：wuchangbao
 * @description：消费者
 * @date：2021/9/5
 */
public class Consumer {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws Exception {
        // 获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        // 从连接中获取一个通道
        Channel channel = connection.createChannel();
        // 创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("receive：" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }


}
