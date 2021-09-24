package cn.zin.service.simplequeues;

import cn.zin.service.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author：wuchangbao
 * @description：生产者（简单队列模式中没有交换机，消息直接绑定到队列中。）
 * @date：2021/9/5
 */
public class Provider {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws Exception {
        // 获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        // 从连接中获取一个通道
        Channel channel = connection.createChannel();
        // 创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 需要发送的消息
        String msg = "hello simple!";
        // 直接把消息发送到指定的队列中
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("send msg：" + msg);
        // 关闭
        channel.close();
        connection.close();
    }


}
