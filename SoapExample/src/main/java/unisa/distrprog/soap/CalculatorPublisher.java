package unisa.distrprog.soap;

import jakarta.xml.ws.Endpoint;

public class CalculatorPublisher {
    public static void main(String[] args) {
        String address = "http://localhost:8080/calculator";
        Endpoint.publish(address, new CalculatorServiceImpl());
        System.out.println("Calculator Web Service is running at: " + address + "?wsdl");
    }
}
