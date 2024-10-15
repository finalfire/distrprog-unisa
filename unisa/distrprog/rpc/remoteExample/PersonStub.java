package unisa.distrprog.rpc.remoteExample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class PersonStub implements Person {
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public PersonStub(String hostname) throws Throwable {
        this.socket = new Socket(hostname, 33333);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public String getName() throws Throwable {
        out.writeObject("getName");
        out.flush();
        return (String) in.readObject();
    }

    @Override
    public String getPlaceOfBirth() throws Throwable {
        out.writeObject("getPlaceOfBirth");
        out.flush();
        return (String) in.readObject();
    }

    @Override
    public int getDateOfBirth() throws Throwable {
        out.writeObject("getDateOfBirth");
        out.flush();
        return in.readInt();
    }

    @Override
    public int year(int year) throws Throwable {
        out.writeObject("year");
        out.writeInt(year);
        out.flush();
        return in.readInt();
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
