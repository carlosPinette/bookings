package com.caperera.bookings.data.webServices;

import java.util.List;

import com.caperera.bookings.business.domains.VehicleReservation;
import com.caperera.bookings.business.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

    

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model) {
        List<VehicleReservation> vehicleReservationList = this.reservationService.getVehicleReservationsForDate(dateString);
        model.addAttribute("vehicleReservations", vehicleReservationList);
        return "reservations";
    }
}
