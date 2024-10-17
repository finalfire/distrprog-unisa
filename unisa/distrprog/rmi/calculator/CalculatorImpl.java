package unisa.distrprog.rmi.calculator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    public CalculatorImpl() throws RemoteException {}

    @Override
    public double sum(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double diff(double a, double b) throws RemoteException {
        return a - b;
    }

    @Override
    public double prod(double a, double b) throws RemoteException {
        return a * b;
    }

    @Override
    public double div(double a, double b) throws RemoteException {
        return b != 0.0 ? a / b : 0.0;
    }
}
