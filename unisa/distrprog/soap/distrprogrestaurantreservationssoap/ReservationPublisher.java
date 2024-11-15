package unisa.distrprog.soap.distrprogrestaurantreservationssoap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.ws.Endpoint;

@XmlRootElement(name = "Reservation")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationPublisher {
    public static void main(String[] args) {
        String address = "http://localhost:8080/restaurant";
        Endpoint.publish(address, new ReservationServiceImpl());
        System.out.println("Reservation Web Service is running at " + address + "?wsdl");
    }
}
