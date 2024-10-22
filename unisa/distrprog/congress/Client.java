package unisa.distrprog.congress;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry(Server.PORT);
            ServerCongress congress = (ServerCongress) reg.lookup("rmi://localhost/ServerCongress");

            System.out.println(congress.register(10, 20));
            System.out.println(congress.getProgram(127));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
