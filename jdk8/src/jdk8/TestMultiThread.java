package jdk8;


import java.util.concurrent.locks.ReentrantLock;

/*
 * @author yinchao
 * @date 2020/4/21 10:27
 */
public class TestMultiThread {
    int target = 1_0000_0000;

    public static void main(String[] args) throws Exception {
        TestMultiThread testMultiThread = new TestMultiThread();
        testMultiThread.start();
    }

    private void start() throws Exception{
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int temp = target;
                long start = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName());
                while (target > 0) {
                    target--;
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("duration="+(System.currentTimeMillis()-start));
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(runnable, "thread-" + i);
            threads[i].start();
        }
        long temp = System.currentTimeMillis();
        int m = 1_0000_0000;
        while(m>0){
            m--;
        }
        System.out.println(System.currentTimeMillis()-temp);
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        System.out.println(target);
    }
}

