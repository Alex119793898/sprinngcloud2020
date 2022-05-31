package com.caoliang.springcloud.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK,id: " + id + "哈哈～";
    }

    public String paymentInfo_TimeOut(Integer id){

        int timeNumber = 3000;
        try {
            Thread.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id: " + id + "哈哈～" + " 耗时: " + timeNumber +" 毫秒";
    }
}
