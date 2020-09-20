package jdk8;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yinchao
 * @date 2020/4/20 22:18
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        try {
            // lock
            reentrantLock.lock();
            try {
                // tryLock
                // 重入
                boolean result = reentrantLock.tryLock(1, TimeUnit.SECONDS);
                // 如果锁成功
                if(result){
                    System.out.println("locked");
                }else{
                    return ;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
        try {
            // 交替执行
            reentrantLock.lock();
            System.out.println("1");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }
}
