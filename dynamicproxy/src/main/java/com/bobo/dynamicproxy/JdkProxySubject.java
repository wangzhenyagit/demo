package com.bobo.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: wangzhenya create on 2020-08-25 00:00
 */
public class JdkProxySubject implements InvocationHandler {

    private Subject subject;

    public JdkProxySubject(Subject subject) {
        this.subject = subject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before");
        Object result = method.invoke(subject, args);
        System.out.println("invoke end");
        return result;
    }
}
