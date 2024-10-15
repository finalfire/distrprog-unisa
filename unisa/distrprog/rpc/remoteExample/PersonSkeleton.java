package unisa.distrprog.rpc.remoteExample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PersonSkeleton extends Thread {
    PersonServer server;

    public PersonSkeleton(PersonServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        String method;

        try {
            ServerSocket serverSocket = new ServerSocket(33333);
            socket = serverSocket.accept();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            boolean valid = true;
            while (valid) {
                method = (String) in.readObject();
                switch (method) {
                    case "getName" -> {
                        out.writeObject(this.server.getName());
                        out.flush();
                    }
                    case "getPlaceOfBirth" -> {
                        out.writeObject(this.server.getPlaceOfBirth());
                        out.flush();
                    }
                    case "getDateOfBirth" -> {
                        out.writeInt(this.server.getDateOfBirth());
                        out.flush();
                    }
                    case "getYear" -> {
                        int year = in.readInt();
                        out.writeInt(this.server.year(year));
                        out.flush();
                    }
                    default -> valid = false;
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PersonServer person = new PersonServer("Francesco", "Belvedere Marittimo", 1989);
        PersonSkeleton skeleton = new PersonSkeleton(person);
        skeleton.start();
    }
}
