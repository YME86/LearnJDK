package learn.volatileP;

/**
 * @author yinchao
 * @date 2020/12/18 16:11
 */
public class TestVolatile {
    int a = 1;
    volatile int b = 2;
    int c = 3;

    public static void main (String[] args) {
        TestVolatile testVolatile = new TestVolatile();
        testVolatile.test();
    }
    private void test(){
        new Thread(()-> System.out.println(a+" " +b+ " "+c)).start();
        new Thread(()->{
            a = 0;
            b = 1;
            c = 2;
        }).start();
        new Thread(()-> System.out.println(a+" " +b+ " "+c)).start();
    }
}
