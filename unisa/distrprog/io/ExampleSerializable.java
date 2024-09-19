package unisa.distrprog.io;

import java.io.*;

class Student implements Serializable {
    public String name;
    public int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "unisa.distrprog.io.Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class ExampleSerializable {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.ser"));
        oos.writeObject(new Student("Foo Bar", 35));
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.ser"));
        Student stud = (Student) ois.readObject();
        System.out.println(stud);
    }
}
