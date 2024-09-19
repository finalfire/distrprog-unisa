package unisa.distrprog.io.exercise;

import java.io.*;

public class Exercise2 {
    // How do I read a set of integers from a file using a buffered input?
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream("random_integers.txt"));
        int b;
        while ((b = bin.read()) != -1) {
            System.out.println(b);
        }
    }
}
