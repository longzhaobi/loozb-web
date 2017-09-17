package com.loozb.core.pool;

import java.util.concurrent.Callable;

/**
 * Created by chuan on 2017-9-17.
 */
public class RealData implements Callable<String> {
    private String para;
    public RealData(String para) {
        this.para = para;
    }

    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){

            }
        }
        return sb.toString();
    }
}
