package com.caoliang.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK,id: " + id + "哈哈～";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")          //execution.isolation.thread.timeoutInMilliseconds当前线程的超时时间是3秒钟，但下面方法花费了5秒钟
    })
    public String paymentInfo_TimeOut(Integer id){

        int timeNumber = 3000;
        try {
            Thread.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id: " + id + "哈哈～" + " 耗时: " + timeNumber +" 毫秒";
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " 系统繁忙或者运行报错,请稍后再试，id: " + id + "哭～";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),                      //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),         //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),   //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")        //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {

        if (id < 0) {
            throw new RuntimeException("id 不能为负数");
        }
        String serialNumer = IdUtil.simpleUUID();
        return "线程池：" + Thread.currentThread().getName() + " 调用成功，流水号 " + serialNumer + " 毫秒";
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试，哭... id: " + id;
    }
}
