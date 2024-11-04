package unisa.distrprog.soap;

import jakarta.jws.WebService;

@WebService(endpointInterface = "unisa.distrprog.soap.CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public double add(double a, double b) {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) {
        return a * b;
    }

    @Override
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero!");
        }
        return a / b;
    }
}
