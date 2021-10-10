import jvm.A;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.nio.ByteOrder;

/**
 * @author yinchao
 * @date 2020/4/11 03:16
 */
public class SynchronizedTest {
    private final A a = new A();

    public static void main (String[] args) throws InterruptedException {
        //mainTest();
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        synchronizedTest.testSynchronized3();
    }

    private static void mainTest () throws InterruptedException {
        /**
         *
         * 默认小端存储
         *
         * 小端序：数据的高位字节存放在地址的高端 低位字节存放在地址低端
         * 大端序： 数据的高位字节存放在地址的低端 低位字节存放在地址高端
         *
         * 比如一个整形0x1234567，1是高位数据，7是低位数据。按照小端序放在内存地址的高位，比如 01 放在 0x100，23 就放在 0x101 以此类推。
         *
         * 大端序反之。67 放在 0x100,45 在 0x101, 23 -> 0x102, 01-> 0x103
         */
        System.out.println("默认存储方式:" + ByteOrder.nativeOrder());
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        Thread t0 = new Thread(synchronizedTest::lock);
        t0.start();
        t0.join();
        System.out.println("t0 is over");
        Thread t1 = new Thread(synchronizedTest::lock);
        t1.start();
        t1.join();
        System.out.println("t1 is over");
        synchronizedTest.lock();
        System.out.println("main is over");
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
    public void testSynchronized () {
        // 101 无锁可偏向
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        System.out.println(Integer.toBinaryString(a.hashCode()) + " " + Integer.toHexString(a.hashCode()));
        // 001 无锁不可偏向 -> 不可偏向的偏向锁
        System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
        printCurrentThread();
        synchronized (this.a) {
            // 不可偏向的偏向锁直接升级为 000 轻量锁
            System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
        }
        System.out.println(ClassLayout.parseInstance(a).toPrintable(a));
    }

    @Test
    public void testSynchronized2 () {
        A b = new A();
        System.out.println(ClassLayout.parseInstance(b).toPrintable(b));
        printCurrentThread();
        synchronized (b) {
            // 如果偏向锁延迟为0，则:101 无锁可偏向
            System.out.println(ClassLayout.parseInstance(b).toPrintable(b));
            // 如果偏向锁延迟为400，则:000 偏向锁
        }
        // 偏向锁不会主动撤销，线程id不会主动置零
        System.out.println(ClassLayout.parseInstance(b).toPrintable(b));
    }

    /**
     * 都是重量级锁 10
     */
    @Test
    public void testSynchronized3 () throws InterruptedException {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        Thread t1 = new Thread(() -> {
            synchronized (synchronizedTest.a) {
                System.out.println("============= into lock method =============\n" + ClassLayout.parseInstance(synchronizedTest.a).toPrintable(synchronizedTest.a));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 is over");
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            synchronized (synchronizedTest.a) {
                System.out.println("============= into lock method =============\n" + ClassLayout.parseInstance(synchronizedTest.a).toPrintable(synchronizedTest.a));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 is over");
            }
        });
        t2.start();
        t2.join();
    }

    private void lock () {
        System.out.println("============= into lock method =============\n" + ClassLayout.parseInstance(this.a).toPrintable(this.a));
        printCurrentThread();
        synchronized (this.a) {
            System.out.println(ClassLayout.parseInstance(this.a).toPrintable(this.a));
        }
        System.out.println("======================== out lock method ========================\n" + ClassLayout.parseInstance(this.a).toPrintable(this.a));
    }

    private void printCurrentThread () {
        System.out.println(Thread.currentThread().getName() + " " + Long.toBinaryString(Thread.currentThread().getId()) + " " + Long.toHexString(Thread.currentThread().getId()) + " " + Integer.toBinaryString(Thread.currentThread().hashCode()) + " " + Integer.toHexString(Thread.currentThread().hashCode()));
    }
}
