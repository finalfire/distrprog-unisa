package unisa.distrprog.io;

import java.io.*;
public class ExampleBinaryIO {
    public static void copy(InputStream in, OutputStream out) throws IOException {
        int b; // the byte I'm going to read
        while ((b = in.read()) != -1)
            out.write(b);

        in.close();
        out.write("Ok, bye!".getBytes());
    }

    public record Person(String name, int age) {}

    public static void main(String[] args) throws IOException {
        copy(System.in, System.out);
    }
}