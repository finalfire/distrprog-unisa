package unisa.distrprog.rmi.calculator;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            CalculatorImpl calc = new CalculatorImpl();
            reg.rebind("rmi://localhost/CalculatorServer", calc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
