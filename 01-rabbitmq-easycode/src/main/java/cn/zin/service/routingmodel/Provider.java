package cn.zin.service.routingmodel;

import cn.zin.service.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author:wuchangbao
 * @description:生产者
 * @date:2021/9/5
 */
public class Provider {

    /**
     * 声明交换机的名称
     */
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String msg = "hello direct!";
        // routingKey
        // error两个都可以收到
        // String routingKey = "error";
        // info只有Consumer02能收到
        // String routingKey = "info";
        // warning只有Consumer02能收到
        String routingKey = "warning";
        // 生产者发送消息：交换机名称、路由键、消息的其他属性路由标头等、消息正文
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("-------------send: " + msg);
        channel.close();
        connection.close();
    }

}
