package com.caoliang.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
}
