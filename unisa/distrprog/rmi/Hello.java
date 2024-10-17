package unisa.distrprog.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String sayHello(String who) throws RemoteException;
}
