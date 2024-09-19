package unisa.distrprog.io;

import java.io.*;

public class ExampleCharStreamIO {
    public static void copy(Reader in, Writer out) throws IOException {
        int c;
        while ((c = in.read()) != '\n')  // scanner stdin
            out.write(c); // system.out.println(...)
        in.close();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        InputStreamReader r =  new InputStreamReader(System.in, "UTF-8");
        OutputStreamWriter w = new OutputStreamWriter(System.out, "UTF-8");
        copy(r, w);
    }
}
