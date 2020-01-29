package com.bobo.hystrix.service;

/**
 * @author: wangzhenya create on 2020-01-27 16:23
 */
public class RemoteServiceSimulator {
    private long wait;

    public RemoteServiceSimulator(long wait) {
        this.wait = wait;
    }

    public String execute() throws InterruptedException {
        Thread.sleep(wait);
        return "success";
    }
}
