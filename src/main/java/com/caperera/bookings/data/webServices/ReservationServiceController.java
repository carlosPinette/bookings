package com.caperera.bookings.data.webServices;

import java.util.List;

import com.caperera.bookings.business.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.caperera.bookings.business.domains.VehicleReservation;

@RestController
@RequestMapping(value = "/api")
public class ReservationServiceController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET, value = "/reservations/{date}")
    public List<VehicleReservation> getAllReservations(@PathVariable(value = "date") String date){

        return this.reservationService.getVehicleReservationsForDate(date);
    }
}