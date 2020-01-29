package com.bobo.annotation.service;

import com.bobo.annotation.annotation.LogExecutionTime;
import org.springframework.stereotype.Service;

/**
 * @author: wangzhenya create on 2020-01-28 15:55
 */
@Service
public class SimulateService {
    @LogExecutionTime
    public void serve() throws InterruptedException {
        Thread.sleep(2000L);
    }
}
