package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value="tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    //  pathvoriable id 要写，其他只要{id}=String id 可以不写
    @RequestMapping(value="/label/{id}",method = RequestMethod.GET)
     public Result findById(@PathVariable("id") String id);



}
