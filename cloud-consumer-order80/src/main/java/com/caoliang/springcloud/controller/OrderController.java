package com.caoliang.springcloud.controller;

import com.caoliang.springcloud.entities.CommonResult;
import com.caoliang.springcloud.entities.Payment;
import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    public static final String URL = "http://localhost:8001";

    @GetMapping("/consumer/create")
    public CommonResult<Payment> create(Payment payment){
        Log.info("sjfklsj");
        return restTemplate.postForObject(URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/comsumer/getById/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(URL + "/payment/getById/" + id, CommonResult.class);
    }
}
