package com.caperera.bookings.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.caperera.bookings.data.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByLastName(String lastName);
}
