package unisa.distrprog.soap.distrprogrestaurantreservationssoap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;
import java.util.List;

@WebService
public interface ReservationService {
    @WebMethod
    public List<Reservation> getAll();

    @WebMethod
    public Reservation getById(@WebParam(name = "id") Long id);

    @WebMethod
    public Reservation create(@WebParam(name = "reservation") Reservation reservation);

    @WebMethod
    public Reservation deleteById(@WebParam(name = "id") Long id);
}
