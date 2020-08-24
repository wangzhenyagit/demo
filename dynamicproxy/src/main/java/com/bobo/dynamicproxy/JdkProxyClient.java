package com.bobo.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * @author: wangzhenya create on 2020-08-25 00:04
 */
public class JdkProxyClient {
    public static void main(String[] args) {
        Subject subject = (Subject) Proxy.newProxyInstance(JdkProxyClient.class.getClassLoader(),
                new Class[]{Subject.class}, new JdkProxySubject(new SubjectImpl()));
        subject.hello();
        subject.foo();
    }
}
