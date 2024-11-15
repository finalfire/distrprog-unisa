package unisa.distrprog.soap.distrprogrestaurantreservationssoap;

import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@WebService(endpointInterface = "unisa.distrprog.soap.distrprogrestaurantreservationssoap.ReservationService")
public class ReservationServiceImpl implements ReservationService {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong();

    @Override
    public List<Reservation> getAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public Reservation getById(Long id) {
        return reservations.get(id);
    }

    @Override
    public Reservation create(Reservation reservation) {
        reservation.setId(nextId.incrementAndGet());
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Reservation deleteById(Long id) {
        return reservations.remove(id);
    }
}
