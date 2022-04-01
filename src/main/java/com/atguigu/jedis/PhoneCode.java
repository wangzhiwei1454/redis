package com.atguigu.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {

    public static void main(String[] args) {
        //模拟验证码发送
        verifyCode("19939963265");

        //getRedisCode("19939963265","864191");
    }

    //3 验证码校验
    public static void  getRedisCode(String phone,String code){
        //从redis中获取验证码
        Jedis jedis = new Jedis("192.168.140.124",6379);
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        //判断
        if (redisCode.equals(code)){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }

        jedis.close();
    }

    //2 每个手机号每天只能发送三次，验证码放在redis中，设置过期时间120s
    public static void verifyCode(String phone){
        //连接redis
        Jedis jedis = new Jedis("192.168.140.124",6379);
        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        //每个手机只能发送三次
        String count = jedis.get(countKey);
        if (count == null){
            //没有发送次数，第一次发送，设置发送次数1
            jedis.setex(countKey,24*60*60,"1");
        } else if (Integer.parseInt(count) <= 2){
            //发送次数+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2){
            System.out.println("今天发送次数已超过三次");
            jedis.close();
            return;
        }

        //发送验证码放到redis中
        String vCode = getCode();
        jedis.setex(codeKey,120,vCode);
        jedis.close();
    }

    //1 生成六位验证码
    public static String getCode(){
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code.append(rand);
        }
        return code.toString();
    }
}
