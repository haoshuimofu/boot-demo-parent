package test;

/**
 * @Author ddmc
 * @Create 2019-03-08 15:47
 */
public class Tester {

    public static void main(String[] args) {
        System.out.println("17717929937".substring(7,11));
        System.out.println(testReturn());
    }

    public static String testReturn(){
        try{
            System.out.println(1/0);
            return "normal";

        }catch (Exception e) {
            return "catch";
        } finally {
        }
//        return "finally";

    }
}