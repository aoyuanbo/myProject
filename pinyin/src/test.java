
public class test {
    public static void main(String[] args) throws Exception {
        Class<?> class1 = null;
        Class<?> class2 = null;
        Class<?> class3 = null;
        // һ�����������ʽ
        class1 = Class.forName("test");
        class2 = new test().getClass();
        class3 = test.class;
        
        System.out.println("������   " + class1.getName());
        System.out.println("������   " + class2.getName());
        System.out.println("������   " + class3.getName());
        System.out.println();
    }
}