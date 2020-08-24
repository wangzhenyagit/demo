package com.bobo.dynamicproxy;

/**
 * @author: wangzhenya create on 2020-08-24 23:59
 */
public class SubjectImpl implements Subject {
    public void hello() {
        System.out.println("real subject hello.");
    }

    public void foo() {
        System.out.println("real subject foo.");
    }
}
