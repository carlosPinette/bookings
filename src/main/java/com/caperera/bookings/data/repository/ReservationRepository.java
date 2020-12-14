package com.caperera.bookings.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.caperera.bookings.data.entity.Reservation;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByReservationDate(Date reservationDate);
    List<Reservation> findByClientId(long clientId);
}
