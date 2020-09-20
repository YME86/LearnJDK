package jdk7;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author yinchao
 * @date 2020/4/21 10:27
 */
public class TestMultiThread {
    volatile int target = 1000000000;

    public static void main(String[] args) {
        TestMultiThread testMultiThread = new TestMultiThread();
        testMultiThread.start();
    }

    private void start() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (target < 10_0000_0000) {
                    target--;
                }
            }
        };

        Thread thread = null;
        for (int i = 0; i < 10; i++) {
            thread = new Thread(runnable);
            thread.start();
        }
        if (thread != null) {
            System.out.println(target);
        }
    }
}

