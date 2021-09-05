package cn.zin.service;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author:wuchangbao
 * @description: 获取Rabbitmq的连接工具
 * @date:2021/9/5
 */
public class ConnectionUtils {

    /**
     * 获取Rabbitmq的连接
     *
     * @return 连接对象
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        // 定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("139.196.254.249");
        // AMQP 5672
        factory.setPort(5672);
        // vhost
        factory.setVirtualHost("/");
        // 用户名
        factory.setUsername("guest");
        // 密码
        factory.setPassword("guest");
        return factory.newConnection();
    }

}
