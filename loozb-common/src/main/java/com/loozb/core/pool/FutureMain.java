package com.loozb.core.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by chuan on 2017-9-17.
 */
public class FutureMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> future = new FutureTask<String>(new RealData("a"));

        ExecutorService executor = ThreadPoolSingleton.getInstance();

        executor.submit(future);

        System.out.println("请求完毕，我要去干其他的事情去了");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //这里会一直阻塞
        String data =  future.get();
        System.out.println("数据=" + data);
    }
}
