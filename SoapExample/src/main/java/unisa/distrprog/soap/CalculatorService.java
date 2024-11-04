package unisa.distrprog.soap;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

@WebService
public interface CalculatorService {
    @WebMethod
    public double add(@WebParam(name = "a") double a, @WebParam(name = "b") double b);

    @WebMethod
    public double subtract(@WebParam(name = "a") double a, @WebParam(name = "b") double b);

    @WebMethod
    public double multiply(@WebParam(name = "a") double a, @WebParam(name = "b") double b);

    @WebMethod
    public double divide(@WebParam(name = "a") double a, @WebParam(name = "b") double b);
}
