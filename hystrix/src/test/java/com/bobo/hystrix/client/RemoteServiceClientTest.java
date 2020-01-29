package com.bobo.hystrix.client;

import com.bobo.TestBase;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoteServiceClientTest extends TestBase {
    @Autowired
    RemoteServiceClient remoteServiceClient;

    @Test
    public void invokeRemoteServiceWithoutHystrix() throws InterruptedException {
        remoteServiceClient.invokeRemoteServiceWithoutHystrix();
    }

    @Test(expected = HystrixRuntimeException.class)
    public void invokeRemoteServiceWithHystrix() throws InterruptedException {
        remoteServiceClient.invokeRemoteServiceWithHystrix();
    }
}