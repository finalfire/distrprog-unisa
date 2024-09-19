package unisa.distrprog.io;

import java.io.*;

public class ExampleByteArrayIO {
    public static void copy(InputStream in, OutputStream out) throws IOException {
        int b; // the byte we're going to read
        while ((b = in.read()) != -1)
            out.write(b);
        in.close();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        byte[] myBuff = {'H', 'e', 'l', 'l', 'o', '!', '\n'};
        ByteArrayInputStream bin = new ByteArrayInputStream(myBuff);
        copy(bin, System.out);
    }
}