package unisa.distrprog.rmi.calculatorLesson;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static final int PORT = 1099;
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(PORT);
            Calculator calculator = new CalculatorImpl();
            reg.rebind("rmi://localhost/Calculator", calculator);  // rmi://localhost/Calculator
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
