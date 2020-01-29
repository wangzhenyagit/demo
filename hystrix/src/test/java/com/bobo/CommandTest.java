package com.bobo;

import com.bobo.hystrix.command.CommandHelloWorld;
import com.bobo.hystrix.command.RemoteSimulatorServiceCommand;
import com.bobo.hystrix.service.RemoteServiceSimulator;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CommandTest extends TestBase {
    @Test
    public void givenInputBobAndDefaultSettings_whenHelloWorldCommandExecuted_thenReturnHelloBob() {
        assertThat(new CommandHelloWorld("Bob").execute(), equalTo("Hello Bob!"));
    }

    @Test
    public void givenSvcTimeoutOf100AndDefaultSettings_whenRemoteSvcExecuted_thenReturnSuccess() {
        HystrixCommand.Setter config = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup1"));
        assertThat(new RemoteSimulatorServiceCommand(config, new RemoteServiceSimulator(100)).execute(),
                equalTo("success"));

    }

    @Test
    public void givenSvcTimeoutOf100AndExecTimeoutOf1000_whenRemoteSvcExecuted_thenReturnSuccess() {
        HystrixCommand.Setter config = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup2"));
        HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
        properties.withExecutionTimeoutInMilliseconds(1000);
        config.andCommandPropertiesDefaults(properties);
        assertThat(new RemoteSimulatorServiceCommand(config, new RemoteServiceSimulator(100)).execute(),
                equalTo("success"));

    }

    @Test(expected = HystrixRuntimeException.class)
    public void givenSvcTimeoutOf5000AndExecTimeoutOf3000_whenRemoteSvcExecuted_thenExpectHre() {
        HystrixCommand.Setter config = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup3"));
        HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
        properties.withExecutionTimeoutInMilliseconds(3000);
        config.andCommandPropertiesDefaults(properties);
        assertThat(new RemoteSimulatorServiceCommand(config, new RemoteServiceSimulator(5000)).execute(),
                equalTo("success"));

    }

    @Test
    public void givenCircuitBreakerSetup_whenRemoteSvcCommandExecuted_thenReturnSuccess() throws InterruptedException {
        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroupCircuitBreaker"));

        HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
        properties.withExecutionTimeoutInMilliseconds(1000);
        properties.withCircuitBreakerSleepWindowInMilliseconds(4000);
        properties.withExecutionIsolationStrategy
                (HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
        properties.withCircuitBreakerEnabled(true);
        properties.withCircuitBreakerRequestVolumeThreshold(1);

        config.andCommandPropertiesDefaults(properties);
        config.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withMaxQueueSize(1)
                .withCoreSize(1)
                .withQueueSizeRejectionThreshold(1));

        assertThat(this.invokeRemoteService(config, 10000), equalTo(null));
        assertThat(this.invokeRemoteService(config, 10000), equalTo(null));
        assertThat(this.invokeRemoteService(config, 10000), equalTo(null));

        Thread.sleep(5000);

        assertThat(new RemoteSimulatorServiceCommand(config, new RemoteServiceSimulator(500)).execute(),
                equalTo("success"));

        assertThat(new RemoteSimulatorServiceCommand(config, new RemoteServiceSimulator(500)).execute(),
                equalTo("success"));

        assertThat(new RemoteSimulatorServiceCommand(config, new RemoteServiceSimulator(500)).execute(),
                equalTo("success"));
    }

    public String invokeRemoteService(HystrixCommand.Setter config, int timeout) {

        String response = null;

        try {
            response = new RemoteSimulatorServiceCommand(config,
                    new RemoteServiceSimulator(timeout)).execute();
        } catch (HystrixRuntimeException ex) {
            System.out.println("ex = " + ex);
        }

        return response;
    }
}