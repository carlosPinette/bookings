package com.caperera.bookings.data.webServices;

import com.caperera.bookings.data.repository.ClientRepository;
import com.caperera.bookings.data.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.caperera.bookings.data.entity.Client;
import java.util.*;


@RestController
@RequestMapping(value = "/rest")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    
    @RequestMapping(method = RequestMethod.GET, value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> findAll(){
        List<Client> clientList = (List<Client>) this.clientRepository.findAll();
        if(clientList.isEmpty()){
            return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Client>>(clientList, HttpStatus.OK);
    }

    @RequestMapping(value = "client/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> findClientByLastName(@PathVariable("id") String lastName){
        Client client = this.clientRepository.findByLastName(lastName);
        if(client == null){
            return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "client", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> deleteAll(){
        this.reservationRepository.deleteAll();
        this.clientRepository.deleteAll();
        return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "client/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> deleteClientById(@PathVariable("id") long id){
        Optional<Client> client = this.clientRepository.findById(id);
        if(client == null){
            return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
        }
        this.reservationRepository.deleteAll(this.reservationRepository.findByClientId(id));
        this.clientRepository.deleteById(id);
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "client/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> updateClient(@PathVariable("id")String lastName, @RequestBody Client client){
        Client oldClient = this.clientRepository.findByLastName(lastName);
        if(oldClient == null){
            return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
        }
        oldClient.setAddress(client.getAddress());
        oldClient.setEmailAddress(client.getEmailAddress());
        oldClient.setFirstName(client.getFirstName());
        oldClient.setLastName(client.getLastName());
        oldClient.setPhoneNumber(client.getPhoneNumber());
        oldClient.setState(client.getState());
        oldClient.setCountry(client.getCountry());
        this.clientRepository.save(oldClient);
        return new ResponseEntity<Client>(oldClient, HttpStatus.OK);
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> createClient(@RequestBody Client client, UriComponentsBuilder ucBuilder){
        if(this.clientRepository.existsById(client.getId  ())){
            return new ResponseEntity<Client>(HttpStatus.CONFLICT);
        }
        this.clientRepository.save(client);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }

}
