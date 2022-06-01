package com.caoliang.springcloud.controller;

import com.caoliang.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "Consumer_Global_FallbackMethod")
public class OrderHystirxController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/hystrix/timeout/{id}")
    /*@HystrixCommand(fallbackMethod = "consumerTimeOutFallBackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500") ////execution.isolation.thread.timeoutInMilliseconds当前线程的超时时间是1.5秒钟，但下面方法花费了5秒钟
    })*/
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    public String consumerTimeOutFallBackMethod(@PathVariable("id") Integer id){
        return "我是消费者80，对方服务端系统繁忙，请10秒钟后再试或者运行出错请检查自己，哭...";
    }

    public String Consumer_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，哭...";
    }
}
