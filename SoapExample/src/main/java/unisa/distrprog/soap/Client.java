package unisa.distrprog.soap;

import javax.xml.namespace.QName;
import java.net.URL;
import jakarta.xml.ws.Service;

public class Client {
    public static void main(String[] args) {
        try {
            // The URL to your WSDL
            URL url = new URL("http://localhost:8080/calculator?wsdl");

            // The QName represents the service name. You can find these values in the WSDL file.
            // First parameter is the target namespace (it is usually the full package name)
            // Second parameter is the service name (usually the Impl class with suffix Service)
            QName qname = new QName("http://soap.distrprog.unisa/", "CalculatorServiceImplService");

            // Create a service
            Service service = Service.create(url, qname);

            // Get the service interface (port)
            CalculatorService calculator = service.getPort(CalculatorService.class);

            // Test the service methods
            System.out.println("Testing Calculator Web Service");
            System.out.println("----------------------------");

            // Test addition
            double result1 = calculator.add(10, 5);
            System.out.println("10 + 5 = " + result1);

            // Test subtraction
            double result2 = calculator.subtract(10, 5);
            System.out.println("10 - 5 = " + result2);

            // Test multiplication
            double result3 = calculator.multiply(10, 5);
            System.out.println("10 * 5 = " + result3);

            // Test division
            double result4 = calculator.divide(10, 5);
            System.out.println("10 / 5 = " + result4);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
