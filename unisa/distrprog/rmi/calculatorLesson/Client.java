package unisa.distrprog.rmi.calculatorLesson;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            System.out.println("List of remote objects:");
            for (String s : reg.list())
                System.out.println(s);
            Calculator c = (Calculator) reg.lookup("rmi://localhost/Calculator");
            System.out.println(c.sum(10, 20));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
