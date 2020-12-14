package com.caperera.bookings.business.services;

import com.caperera.bookings.data.repository.ReservationRepository;
import com.caperera.bookings.data.repository.VehicleRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.caperera.bookings.business.domains.VehicleReservation;
import com.caperera.bookings.data.entity.Client;
import com.caperera.bookings.data.entity.Reservation;
import com.caperera.bookings.data.entity.Vehicle;
import com.caperera.bookings.data.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private VehicleRepository vehicleRepository;
    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public ReservationService(VehicleRepository vehicleRepository, ClientRepository clientRepository,
            ReservationRepository reservationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
    }
    
    public List<VehicleReservation> getVehicleReservationsForDate(String dateString) {
        Date date = this.createDateFromDateString(dateString);
        Iterable<Vehicle> vehicles = this.vehicleRepository.findAll();
        Map<Long, VehicleReservation> vehicleReservationsMap = new HashMap<>();
        vehicles.forEach(vehicle -> {
            VehicleReservation vehicleReservation = new VehicleReservation();
            vehicleReservation.setVehicleId(vehicle.getId());
            vehicleReservation.setVehicleModel(vehicle.getVehicleModel());
            vehicleReservation.setVehicleType(vehicle.getVehicleType());
            vehicleReservationsMap.put(vehicle.getId(), vehicleReservation);
        });
        Iterable<Reservation> reservations = (Iterable<Reservation>) this.reservationRepository
                .findByReservationDate(date);
        if (reservations != null) {
            reservations.forEach(reservation -> {
                Optional<Client> client = this.clientRepository.findById(reservation.getClientId());
                if (client != null) {
                    VehicleReservation vehicleReservation = vehicleReservationsMap.get(reservation.getId());
                    vehicleReservation.setDate(date);
                    vehicleReservation.setFirstName(client.get().getFirstName());
                    vehicleReservation.setLastName(client.get().getLastName());
                    vehicleReservation.setClientId(client.get().getId());
                }
            });
        }
        List<VehicleReservation> vehicleReservations = new ArrayList<>();
        for(Long vehicleId : vehicleReservationsMap.keySet()){
            vehicleReservations.add(vehicleReservationsMap.get(vehicleId));
        }
        return vehicleReservations;
    }

    private Date createDateFromDateString(String dateString){
        Date date = null;
        if (dateString != null) {
            try {
                date = DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return date;
    }
}
