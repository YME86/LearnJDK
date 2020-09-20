import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinchao
 * @date 2020/3/11 15:12
 */
public class HashMapTest {


    @Test
    public void hashMapTestOne() {
        Map<Person, String> map = new HashMap<Person, String>();
        Person person = new Person(1, "person", 0);
        Person theOtherPerson = new Person(1, "theOtherPerson", 1);
//        System.out.println(person==theOtherPerson);
        map.put(person, "123");

        // 重写 equals 方法仅仅判断 对象的 id 是否相等,如果 id 相等,则对象相等
        if ((person.equals(theOtherPerson))) {
            System.out.println(map.get(theOtherPerson));
            // 但是这样,明明对象相等,却获取不到123,出现逻辑问题
            // ==========================================================
            // 解决办法: 重写 equals 时,必须重写 hashcode方法 @see hashMapTestTwo()
        }
    }

    @Test
    public void hashMapTestTwo() {
        Map<TheOtherPerson, String> map = new HashMap<>();
        TheOtherPerson person = new TheOtherPerson(1, "person", 0);
        TheOtherPerson theOtherPerson = new TheOtherPerson(1, "theOtherPerson", 1);
        map.put(person, "123");

        // 重写 equals 方法仅仅判断 对象的 id 是否相等,如果 id 相等,则对象相等
        if (person.equals(theOtherPerson)) {
            System.out.println(map.get(theOtherPerson));
        }
    }
}
