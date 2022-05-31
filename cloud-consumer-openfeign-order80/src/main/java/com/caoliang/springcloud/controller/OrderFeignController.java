package com.caoliang.springcloud.controller;


import com.caoliang.springcloud.entities.CommonResult;
import com.caoliang.springcloud.entities.Payment;
import com.caoliang.springcloud.service.PaymentFeignService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@EnableFeignClients
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/getById/{id}")
    public CommonResult<Payment> getById(@PathVariable("id") Long id){
        System.out.println(id);
        return paymentFeignService.getById(id);
    }

    @GetMapping("/consumer/feign/timeout")
    public String payMentFeignTimeout(){
        //openFeign-ribbon 客户端默认等待1秒钟
        return paymentFeignService.payMentFeignTimeout();
    }
}
