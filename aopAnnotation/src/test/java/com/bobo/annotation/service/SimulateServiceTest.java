package com.bobo.annotation.service;

import com.bobo.annotation.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SimulateServiceTest {

    @Autowired
    SimulateService simulateService;

    @Test
    public void serve() throws InterruptedException {
        simulateService.serve();
    }
}