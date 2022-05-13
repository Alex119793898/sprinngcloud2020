package com.caoliang.springcloud.controller;

import com.caoliang.springcloud.entities.CommonResult;
import com.caoliang.springcloud.entities.Payment;
import com.caoliang.springcloud.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value="/create")
    public CommonResult create(@RequestBody Payment payment){
        int i = paymentService.create(payment);

        if(i>0){
            return new CommonResult(200,"插入输入成功",i);
        }else {
            return new CommonResult(400,"插入数据失败",null);
        }
    }

    @GetMapping(value="/getById/{id}")
    public CommonResult create(@PathVariable Long id){
        Payment payment =  paymentService.getPaymentById(id);

        System.out.println(payment);
        if(payment!=null){
            return new CommonResult(200,"查询成功",payment);
        }else {
            return new CommonResult(400,"没有记录："+id,null);
        }
    }
}
