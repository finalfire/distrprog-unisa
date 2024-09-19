package unisa.distrprog.thread;

class A extends Thread {
    public A (String name) { super(name); }

    @Override
    public void run() {
        System.out.println("Hello, I am " + getName());
    }
}

class B implements Runnable {
    public String name;
    public B (String name) { this.name = name; }
    public void run() {
        System.out.println("Hello, I am " + this.name);
    }
}

public class ExampleThreads {
    public static void main(String[] args) {
        B b = new B("Francesco");
        Thread t = new Thread(b);
        t.start();
    }
}