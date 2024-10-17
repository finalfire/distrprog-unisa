package unisa.distrprog.rmi.calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    double sum(double a, double b) throws RemoteException;
    double diff(double a, double b) throws RemoteException;
    double prod(double a, double b) throws RemoteException;
    double div(double a, double b) throws RemoteException;
}
