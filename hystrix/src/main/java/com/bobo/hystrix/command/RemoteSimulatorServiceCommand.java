package com.bobo.hystrix.command;

import com.bobo.hystrix.service.RemoteServiceSimulator;
import com.netflix.hystrix.HystrixCommand;

/**
 * @author: wangzhenya create on 2020-01-27 16:27
 */
public class RemoteSimulatorServiceCommand extends HystrixCommand<String> {

    private RemoteServiceSimulator remoteServiceSimulator;

    public RemoteSimulatorServiceCommand(Setter setter, RemoteServiceSimulator remoteServiceSimulator) {
        super(setter);
        this.remoteServiceSimulator = remoteServiceSimulator;
    }

    @Override
    protected String run() throws Exception {
        return remoteServiceSimulator.execute();
    }
}
