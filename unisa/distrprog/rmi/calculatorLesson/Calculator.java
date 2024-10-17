package unisa.distrprog.rmi.calculatorLesson;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    public double sum(double a, double b) throws RemoteException;
    public double diff(double a, double b) throws RemoteException;
    public double prod(double a, double b) throws RemoteException;
    public double div(double a, double b) throws RemoteException;
}
