package com.atguigu.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisDemo1 {
    public static void main(String[] args) {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.140.124",6379);

        //测试
        String value = jedis.ping();
        System.out.println(value);
    }

    //操作String
    @Test
    public void demo1(){

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.140.124",6379);

        //添加
        jedis.set("name","jack");

        //获取
        String name = jedis.get("name");
        System.out.println(name);

        jedis.flushDB();

        //设置多个key-value
        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for (String key : keys){
            System.out.println(key);
        }
        jedis.close();
    }

    //操作List
    @Test
    public void demo2(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.140.124",6379);
        jedis.flushDB();
        jedis.lpush("k1","v1","v2","v2");
        List<String> k1 = jedis.lrange("k1", 0, -1);
        System.out.println(k1);
        jedis.close();
    }

    //操作Set
    @Test
    public void demo3(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.140.124",6379);
        jedis.flushDB();
        jedis.sadd("k1","v1","v2","v2");
        Set<String> k1 = jedis.smembers("k1");
        for (String str : k1){
            System.out.println(str);
        }
        jedis.close();
    }

    //操作Hash
    @Test
    public void demo4(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.140.124",6379);
        jedis.flushDB();
        jedis.hset("k1","id","1");
        String hget = jedis.hget("k1", "id");
        System.out.println(hget);

        Map<String,String> map = new HashMap<String,String>();
        map.put("telphone","13810169999");
        map.put("address","atguigu");
        map.put("email","abc@163.com");
        jedis.hmset("hash2",map);
        List<String> result = jedis.hmget("hash2", "telphone","email");
        for (String element : result) {
            System.out.println(element);
        }
        jedis.close();
    }

    //操作Zset
    @Test
    public void demo5(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.140.124",6379);
        jedis.flushDB();

        jedis.zadd("china",100d,"shanghai");
        jedis.zadd("china",200d,"beijing");
        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);

        jedis.close();
    }

}
