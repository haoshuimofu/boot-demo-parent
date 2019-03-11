package test;

/**
 * @Author ddmc
 * @Create 2019-03-11 10:13
 */
public class DuoTaiTest {

    public static void main(String[] args) {
        Student s = new Student();
        s.setName("s");

        s.introduce();
        System.out.println(s.name);
    }

}


class Person {
    public String name;
    public int age = 0;

    public void introduce() {
        System.out.println("Hi, I'm Person: " + name+", Age: " + age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Student extends Person{

    public String name;
    public int age = 18;


    @Override
    public void introduce() {
        System.out.println("Hi, I'm Student: " + name+", Age: " + age);
    }
}

class Teacher extends Person{

    @Override
    public void introduce() {
        System.out.println("Hi, I'm Teacher: " + super.getName());
    }
}