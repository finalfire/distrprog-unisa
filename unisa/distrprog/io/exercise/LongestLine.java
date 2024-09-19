package unisa.distrprog.io.exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class LongestLine {
    public static void main(String[] args) {
        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {
            String longest = lines
                    .filter(line -> !line.isEmpty())
                    .max(Comparator.comparing(String::length))
                    .orElseThrow();
            System.out.println("Longest line: " + longest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
