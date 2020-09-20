package jvm;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinchao
 * @date 2020/4/9 18:29
 */
public class Test {

    /**
     * -XX:+PrintTenuringDistribution 每次 Mainor GC 的时候输出年龄
     * -XX:+PrintCommandLineFlags
     * -Xms10m
     * -Xmx10m
     * -XX:NewSize=256k
     * -XX:SurvivorRatio=1
     * -XX:+PrintGCDetails
     * <p>
     * -XX:InitialTenuringThreshold=20
     * -XX:MaxTenuringThreshold=20
     * 通过上面两个参数可以设置新生代到老年代的年龄大小
     * <p>
     * attention: jdk 7 年龄是可以超过15的!
     * 而 JDK 8 设置超过 15 ,例如20,则会出现以下错误:
     * MaxTenuringThreshold of 20 is invalid; must be between 0 and 15
     */
    @org.junit.Test
    public void testMaxAge() {
        List<Test> list = new ArrayList<>();
        while (true) {
            list.add(new Test());
//            Thread.sleep(1000);
        }
    }
}
