package unisa.distrprog.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloServer {
    public static void main(String[] args) {
        try {
            // 1099 is the default TCP port for the registry
            Registry reg = LocateRegistry.createRegistry(1099);
            HelloImpl obj = new HelloImpl();
            reg.rebind("rmi://localhost/HelloServer", obj);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
