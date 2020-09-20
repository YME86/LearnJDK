import jvm.A;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author yinchao
 * @date 2020/4/11 03:16
 */
public class SynchronizedTest {
    private A a = new A();

    public static void main(String[] args) {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        synchronizedTest.lock();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronizedTest.lock();
            }
        };
        t1.start();
    }

    /**
     * -XX:BiasedLockingStartupDelay=0 默认对象 101
     * 反之默认 = 4000, 则默认对象 001
     * 延迟4秒才开启偏向锁这样的好处是,如果启动项目可能调用main方法,可能会有多线程,可能会同步main函数,那么就可能会有偏向锁的撤销和升级,那么就会有性能损耗,会浪费时间,如果延迟4秒,那么默认里面的锁都是轻量锁,确保没有这个多余的性能开销,当JVM主要加载的加载完了,再开启偏向锁,在一定程度上确保用户线程是默认偏向锁的,而一般的项目启动不止4秒,所以JDK可以这么设置,而4秒之后的用户线程加载的锁,就是默认的101的偏向锁了
     * 但是对于这个简单的demo,4秒中我们程序已经跑完了,所以是默认的 001 是不可偏向的偏向锁
     * 为什么是 001 而不是 000?
     * 1. 001 虽然是不可偏向的偏向锁,但是没有"锁的撤销和升级"这个过程
     * 2. 而是可以直接升级为轻量锁
     */
    @org.junit.Test
    public void testSynchronized() {
        // 101 无锁可偏向
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        System.out.println(Integer.toHexString(a.hashCode()));
        // 001 无锁不可偏向 -> 不可偏向的偏向锁
        System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
        synchronized (a) {
            System.out.println(Integer.toHexString(Thread.currentThread().hashCode()));
            // 不可偏向的偏向所直接升级为 000 轻量锁
            System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
        }
    }

    @Test
    public void testSynchronized2() {
        A a = new A();
        synchronized (a) {
            // 101 无锁可偏向
            System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
        }
    }

    @Test
    public void testSynchronized3() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                lock();
            }
        };
        t1.start();
        try {
            t1.join();
        } catch (Exception e) {
        }
        Thread t2 = new Thread() {
            @Override
            public void run() {
                lock();
            }
        };
        t2.start();
    }

    private void lock() {
        synchronized (a) {
            System.out.println(Thread.currentThread().getName());
            System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
            }
        }
    }
}
