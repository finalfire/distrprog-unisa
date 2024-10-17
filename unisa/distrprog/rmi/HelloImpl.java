package unisa.distrprog.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {
    public HelloImpl() throws RemoteException {}

    @Override
    public String sayHello(String who) throws RemoteException {
        return who;
    }
}
