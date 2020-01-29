package com.bobo.hystrix.command;

import static org.junit.Assert.*;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: wangzhenya create on 2020-01-27 10:39
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        return "Hello " + name + "!";
    }

    public static class UnitTest {
        @Test
        public void testSynchronous() {
            assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        }

        @Test
        public void testAsynchronous() throws ExecutionException, InterruptedException {
            assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());

            Future<String> future = new CommandHelloWorld("World").queue();
            assertEquals("Hello World!", future.get());
        }

        @Test
        public void testObservable1()  {
            Observable<String> observable = new CommandHelloWorld("World").observe();
            assertEquals("Hello World!", observable.toBlocking().single());
        }

        @Test
        public void testObservable2() throws InterruptedException {
            Observable<String> observable = new CommandHelloWorld("World").toObservable();
            observable.subscribe(new Observer<String>() {
                public void onCompleted() {
                    System.out.printf("onCompleted");
                }

                public void onError(Throwable e) {
                    System.out.printf("e");
                }

                public void onNext(String s) {
                    System.out.printf(s);
                }
            });
            Thread.sleep(1000);
        }

        @Test
        public void testObservable3() throws InterruptedException {
            Observable<String> observable = new CommandHelloWorld("World").toObservable();
            observable.subscribe(new Action1<String>() {
                public void call(String s) {
                    System.out.printf(s);
                }
            });
            Thread.sleep(1000);
        }

    }
}