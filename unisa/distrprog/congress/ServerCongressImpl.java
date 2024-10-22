package unisa.distrprog.congress;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerCongressImpl extends UnicastRemoteObject implements ServerCongress {
    private Program program;

    public ServerCongressImpl() throws RemoteException {
        // TODO: initialize the field program;
        // check if a serialization of the program has been already saved
        // if so, load it
        // else, create a new one
    }

    @Override
    public StatusMessage register(int day, int sessionNumber) throws RemoteException {
        // TODO: this.program.addSpeaker(day, sessionNumber)
        return StatusMessage.OK_REGISTERED;
    }

    @Override
    public String getProgram(int day) throws RemoteException {
        // TODO: this.program.getDay(day)
        return "Hello, world!";
    }
}
