package com.caperera.bookings.data.webServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.caperera.bookings.business.domains.VehicleReservation;
import com.caperera.bookings.business.services.ReservationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @MockBean
    private ReservationService reservationServices;
    @Autowired
    private MockMvc mockMvc;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void getReservations() throws Exception {
        Date date = DATE_FORMAT.parse("2020-12-01");
        List<VehicleReservation> mockReservationList = new ArrayList<>();
        VehicleReservation mockVehicleReservation = new VehicleReservation();
        mockVehicleReservation.setFirstName("John");
        mockVehicleReservation.setLastName("Doe");
        mockVehicleReservation.setDate(date);
        mockVehicleReservation.setClientId(1);
        mockVehicleReservation.setVehicleId(1);
        mockVehicleReservation.setVehicleModel("S500");
        mockVehicleReservation.setVehicleType("Mercedes");
        mockReservationList.add(mockVehicleReservation);

        given(reservationServices.getVehicleReservationsForDate("2020-12-01")).willReturn(mockReservationList);

        this.mockMvc.perform(get("/reservations?date=2020-12-01")).andExpect(status().isOk()).
        andExpect(content().string(containsString("John, Doe")));
    }
}
