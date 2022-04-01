package com.atguigu.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 演示redis集群操作
 */
public class RedisClusterDemo {
    public static void main(String[] args) {
        //创建对象
        //使用集群中其他端口号也可以
        HostAndPort hostAndPort = new HostAndPort("192.168.140.124", 6379);
        //HostAndPort hostAndPort = new HostAndPort("192.168.140.124", 6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);

        jedisCluster.set("b1","value1");
        System.out.println(jedisCluster.get("b1"));

        jedisCluster.close();
    }
}
