package unisa.distrprog.congress;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerCongress extends Remote {
    enum StatusMessage {
        ERROR_DAY_FULL,         // indicates that the day is full
        ERROR_DAY_INVALID,      // indicates that the day is not valid
        ERROR_SESSION_INVALID,  // indicates that the session is not valid
        OK_REGISTERED          // registration is ok
    }

    public StatusMessage register(int day, int sessionNumber) throws RemoteException;
    public String getProgram(int day) throws RemoteException;
}
