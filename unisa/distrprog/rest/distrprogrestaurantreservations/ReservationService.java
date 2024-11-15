package unisa.distrprog.rest.distrprogrestaurantreservations;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    private final Map<Long, Reservation> reservationStore = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong();

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationStore.values());
    }

    public Optional<Reservation> getReservationById(Long id) {
        return Optional.ofNullable(reservationStore.get(id));
    }

    public Reservation createReservation(Reservation reservation) {
        reservation.setId(nextId.incrementAndGet());
        reservationStore.put(reservation.getId(), reservation);
        return reservation;
    }

    public void deleteReservationById(Long id) {
        reservationStore.remove(id);
    }
}
