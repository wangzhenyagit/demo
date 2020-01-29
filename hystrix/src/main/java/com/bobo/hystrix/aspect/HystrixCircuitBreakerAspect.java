package com.bobo.hystrix.aspect;

import com.netflix.hystrix.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: wangzhenya create on 2020-01-28 17:00
 */
@Component
@Aspect
public class HystrixCircuitBreakerAspect {

    private HystrixCommand.Setter config;

    @Around("@annotation(com.bobo.hystrix.annotation.HystrixCircuitBreaker)")
    public Object hystrixCommandExecution(ProceedingJoinPoint proceedingJoinPoint) {
        return new RemoteServiceCommand(config, proceedingJoinPoint).execute();
    }

    @PostConstruct
    public void setup() {
        config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroupKey"));
        config = config.andCommandKey(HystrixCommandKey.Factory.asKey("MyCommandKey"));

        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter();
        commandProperties.withExecutionTimeoutInMilliseconds(5000);
        commandProperties.withCircuitBreakerSleepWindowInMilliseconds(4000);

        HystrixThreadPoolProperties.Setter threadPollProperties = HystrixThreadPoolProperties.Setter();
        threadPollProperties.withCoreSize(1).withMaximumSize(1).withMaxQueueSize(1);

        config.andCommandPropertiesDefaults(commandProperties);
        config.andThreadPoolPropertiesDefaults(threadPollProperties);
    }

    public static class RemoteServiceCommand extends HystrixCommand<String> {

        private ProceedingJoinPoint proceedingJoinPoint;

        protected RemoteServiceCommand(Setter setter, ProceedingJoinPoint proceedingJoinPoint) {
            super(setter);
        }

        @Override
        protected String run() throws Exception {
            try {
                return (String)proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throw new Exception(throwable);
            }
        }
    }

}
