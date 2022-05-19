package com.caoliang.springcloud.controller;

import com.caoliang.springcloud.entities.CommonResult;
import com.caoliang.springcloud.entities.Payment;
import com.caoliang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private Long serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value="/create")
    public CommonResult create(@RequestBody Payment payment){
        int i = paymentService.create(payment);

        if(i>0){
            return new CommonResult(200,"插入输入成功"+serverPort,i);
        }else {
            return new CommonResult(400,"插入数据失败",null);
        }
    }

    @GetMapping(value="/getById/{id}")
    public CommonResult create(@PathVariable Long id){
        Payment payment =  paymentService.getPaymentById(id);

        System.out.println(payment);
        if(payment!=null){
            return new CommonResult(200,"查询成功"+serverPort,payment);
        }else {
            return new CommonResult(400,"没有记录："+id,null);
        }
    }

    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return discoveryClient;
    }
}
