package unisa.distrprog.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class HelloClient {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            Hello hello = (Hello) reg.lookup("rmi://localhost/HelloServer");
            System.out.println("Hello, " + hello.sayHello("Francesco"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
