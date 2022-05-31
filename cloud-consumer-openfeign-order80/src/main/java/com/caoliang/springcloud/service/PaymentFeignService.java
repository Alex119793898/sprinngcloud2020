package com.caoliang.springcloud.service;

import com.caoliang.springcloud.entities.CommonResult;
import com.caoliang.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {

    @GetMapping(value="/payment/getById/{id}")
    public CommonResult<Payment> getById(@PathVariable("id") Long id);

    @GetMapping("/payment/feign/timeout")
    public String payMentFeignTimeout();
}
