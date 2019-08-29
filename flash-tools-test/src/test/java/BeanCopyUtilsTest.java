import lombok.Data;
import org.grant.utils.BeanCopyUtils;
import org.junit.Test;

/**
 * grant
 * 26/8/2019 4:54 PM
 * 描述：
 */
public class BeanCopyUtilsTest {
    @Data
    static class A{
        private String a;
        private int b;
    }

    @Data
    static class B{
        private String a;
        private String b;
    }


    @Test
    public void sa(){
        A a = new A();
        a.setA("123.12");
        B b = new B();
        b.setA("22222");
        b.setB("1234");

        BeanCopyUtils.copyProperties(a, b);
        System.out.println(a);
    }
}
