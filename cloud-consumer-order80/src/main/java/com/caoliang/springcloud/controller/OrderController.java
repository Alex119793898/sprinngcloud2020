package com.caoliang.springcloud.controller;

import com.caoliang.springcloud.entities.CommonResult;
import com.caoliang.springcloud.entities.Payment;
import com.caoliang.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    //public static final String URL = "http://localhost:8001";

    //指定服务提供者的服务名称cloud-payment-service
    public static final String PAYMENT_URL = "http://cloud-payment-service";

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/consumer/create")
    public CommonResult<Payment> create(Payment payment){
        LOGGER.info("sjfklsj");
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/getById/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getById/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getById/" + id, CommonResult.class);

        if(entity.getStatusCode().is2xxSuccessful()){
            LOGGER.info(entity.getStatusCode()+"\t"+entity.getHeaders());
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"操作失败");
        }
    }

    /*
    * 之前的 restTemplate @LoadBalance注解已打开(使用的是ribbon负载均衡)
    * 自己写的 ribbon 的 loadBalancer负载均衡功能暂时注调
    * */
    /*@GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");

        if(instances == null || instances.size() <= 0){
            return null;
        }

        ServiceInstance serviceInstance = loadBalancer.instance(instances);

        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri + "/payment/lb",String.class);
    }*/

    @GetMapping("/consumer/payment/zipkin")
    public String getPaymentZipkin(){
        String result = restTemplate.getForObject( PAYMENT_URL + "/payment/zipkin", String.class);
        return result;
    }
}
