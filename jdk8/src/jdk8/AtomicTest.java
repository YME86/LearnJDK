package jdk8;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author yinchao
 * @date 2020/4/30 09:50
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndIncrement();
        AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<Integer>(1,1);
        integerAtomicStampedReference.compareAndSet(1,1234,1,2);
        System.out.println(integerAtomicStampedReference.compareAndSet(1, 1234, 1, 5));
        System.out.println(integerAtomicStampedReference.getReference().intValue());
        System.out.println(integerAtomicStampedReference.getStamp());
    }
}
