package unisa.distrprog.congress;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static final int PORT = 1099;

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(PORT);
            ServerCongress serverCongress = new ServerCongressImpl();
            reg.rebind("rmi://localhost/ServerCongress", serverCongress);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
