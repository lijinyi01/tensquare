package com.tensquare.test;

import com.tensquare.rabbit.RabitApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabitApplication.class)
public class ProductTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){

        rabbitTemplate.convertAndSend("itcast","直接模式1");
    }

    @Test
    public void sendMsg1(){

        rabbitTemplate.convertAndSend("chuanzhi","","分裂模式");
    }

    @Test
    public void sendMsg2(){

        rabbitTemplate.convertAndSend("topic84","good.log","主题模式");
    }

    @Test
    public void test(){
        String random = RandomStringUtils.random(6,false,true);
        System.out.println(random);
    }
}
