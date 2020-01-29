package com.bobo.hystrix.client;

import com.bobo.hystrix.annotation.HystrixCircuitBreaker;
import com.bobo.hystrix.service.RemoteServiceSimulator;
import org.springframework.stereotype.Component;

/**
 * @author: wangzhenya create on 2020-01-28 17:34
 */
@Component
public class RemoteServiceClient {

    public String invokeRemoteServiceWithoutHystrix() throws InterruptedException {
        return new RemoteServiceSimulator(10000).execute();
    }

    @HystrixCircuitBreaker
    public String invokeRemoteServiceWithHystrix() throws InterruptedException {
        return new RemoteServiceSimulator(10000).execute();
    }
}
