package com.bobo.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author: wangzhenya create on 2020-01-29 19:49
 */
public class CommandHelloFailure extends HystrixCommand<String> {
    private String name;

    protected CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("CommandHelloFailureGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        throw new RuntimeException("Always fails.");
    }

    @Override
    protected String getFallback() {
        return "Hello failure " + name + "!";
    }

    public static class UnitTest {
        @Test
        public void test() {
            assertEquals("Hello failure World!", new CommandHelloFailure("World").execute());
        }
    }
}
