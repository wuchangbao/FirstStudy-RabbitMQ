package cn.zin.service.routingmodel;

import cn.zin.service.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author：wuchangbao
 * @description：消费者1
 * @date：2021/9/5
 */
public class Consumer01 {

    private final static String QUEUE_NAME = "test_queue_direct_01";
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机【声明了一个队列，该队列的bindingkey为error。】
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] receive msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done ");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }


}
