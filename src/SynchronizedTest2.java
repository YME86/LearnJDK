import jvm.A;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author yinchao
 * @date 2020/4/11 03:16
 */
public class SynchronizedTest2 {
    private final A a = new A();


    /**
     * -XX:BiasedLockingStartupDelay=0 默认对象 101
     * 反之默认 = 4000, 则默认对象 001
     * 延迟4秒才开启偏向锁这样的好处是,如果启动项目可能调用main方法,可能会有多线程,可能会同步main函数,那么就可能会有偏向锁的撤销和升级,那么就会有性能损耗,会浪费时间,如果延迟4秒,那么默认里面的锁都是轻量锁,确保没有这个多余的性能开销,当JVM主要加载的加载完了,再开启偏向锁,在一定程度上确保用户线程是默认偏向锁的,而一般的项目启动不止4秒,所以JDK可以这么设置,而4秒之后的用户线程加载的锁,就是默认的101的偏向锁了
     * 但是对于这个简单的demo,4秒中我们程序已经跑完了,所以是默认的 001 是不可偏向的偏向锁
     * 为什么是 001 而不是 000?
     * 1. 001 虽然是不可偏向的偏向锁,但是没有"锁的撤销和升级"这个过程
     * 2. 而是可以直接升级为轻量锁
     */
    public static void main (String[] args) throws InterruptedException {
        SynchronizedTest2 synchronizedTest2 = new SynchronizedTest2();
        // 101 无锁可偏向
        System.out.println(ClassLayout.parseInstance(synchronizedTest2.a).toPrintable(synchronizedTest2.a));
        synchronizedTest2.printCurrentThread();
        // attention: 应该是偏向锁，但是实际上是从可偏向的偏向锁直接升级为 000 轻量锁
        //  要设置 -XX:BiasedLockingStartupDelay=0 使默认延迟开启偏向锁的时间为0，即从一开始就开启偏向锁的支持
        synchronized (synchronizedTest2.a) {
            // 101 已偏向
            System.out.println(ClassLayout.parseInstance(synchronizedTest2.a).toPrintable(synchronizedTest2.a));
        }
        // attention: 101 已偏向
        //  退出代码块偏向锁不会被撤销
        System.out.println(ClassLayout.parseInstance(synchronizedTest2.a).toPrintable(synchronizedTest2.a));
    }


    private void printCurrentThread () {
        System.out.println(Thread.currentThread().getName() + " " + Long.toBinaryString(Thread.currentThread().getId()) + " " + Long.toHexString(Thread.currentThread().getId()) + " " + Integer.toBinaryString(Thread.currentThread().hashCode()) + " " + Integer.toHexString(Thread.currentThread().hashCode()));
    }
    /**
     *
     * 运行结果：
     *
     jvm.A object internals:
     OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
     4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     8     4        (object header)                           46 c1 00 f8 (01000110 11000001 00000000 11111000) (-134168250)
     12     4    int A.a                                       0
     Instance size: 16 bytes
     Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

     main 1 1 1101101000111100111011010000010 6d1e7682
     jvm.A object internals:
     OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     0     4        (object header)                           05 d0 00 cc (00000101 11010000 00000000 11001100) (-872361979)
     4     4        (object header)                           80 7f 00 00 (10000000 01111111 00000000 00000000) (32640)
     8     4        (object header)                           46 c1 00 f8 (01000110 11000001 00000000 11111000) (-134168250)
     12     4    int A.a                                       0
     Instance size: 16 bytes
     Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

     jvm.A object internals:
     OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     0     4        (object header)                           05 d0 00 cc (00000101 11010000 00000000 11001100) (-872361979)
     4     4        (object header)                           80 7f 00 00 (10000000 01111111 00000000 00000000) (32640)
     8     4        (object header)                           46 c1 00 f8 (01000110 11000001 00000000 11111000) (-134168250)
     12     4    int A.a                                       0
     Instance size: 16 bytes
     Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

     进程已结束,退出代码0
     */
}
