package unisa.distrprog.io;

import java.io.*;

public class ExampleFileIO {
    public static void copy(InputStream in, OutputStream out) throws IOException {
        int b; // the byte we're going to read
        while ((b = in.read()) != -1)
            out.write(b);
        in.close();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        // java ExampleFileIo file_in.txt file_out.txt
        FileInputStream fin = new FileInputStream(args[0]);
        FileOutputStream fout = new FileOutputStream(args[1]);
        copy(fin, fout);
    }
}
