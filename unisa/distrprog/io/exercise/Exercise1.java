package unisa.distrprog.io.exercise;

import java.io.*;

public class Exercise1 {
    // How do I read a set of integers (in binary) from a file using a buffered input?
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedInputStream br = new BufferedInputStream(new FileInputStream("random_integers_255.bin"));
        int b;
        while ((b = br.read()) != -1) {
            System.out.println(b);
        }
    }
}
