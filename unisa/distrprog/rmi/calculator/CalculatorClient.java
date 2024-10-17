package unisa.distrprog.rmi.calculator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String message;

        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            Calculator calc = (Calculator) reg.lookup("rmi://localhost/CalculatorServer");

            while (true) {
                message = in.nextLine();
                String[] elements = message.split(" ");

                if (elements.length == 1 && elements[0].equals("exit"))
                    break;

                if (elements.length == 3) {
                    double a = Double.parseDouble(elements[1]);
                    double b = Double.parseDouble(elements[2]);

                    double res = switch (elements[0]) {
                        case "add" -> calc.sum(a, b);
                        case "diff" -> calc.diff(a, b);
                        case "prod" -> calc.prod(a, b);
                        case "div" -> calc.div(a, b);
                        default -> 0.0;
                    };

                    System.out.println(res);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {  // lookup
            e.printStackTrace();
        }
    }
}
