package com.loozb.core.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chuan on 2017-9-17.
 */
public class ThreadPoolSingleton {

    private ThreadPoolSingleton() {

    }

    private static class ThreadPoolSingletonHolder {
        private static ExecutorService instance = Executors.newFixedThreadPool(10);
    }

    public static ExecutorService getInstance() {
        return ThreadPoolSingletonHolder.instance;
    }
}
