package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Service
public class AirportService {
    @Autowired
    public AirportRepository airportRepository;
    public void addAirport_Service( Airport airport){

        airportRepository.add_Airport(airport);

    }

    public String getLargestAirportName_Service(){

        return airportRepository.largest_Airport();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities_Service(City fromCity, City toCity){

        return airportRepository.shortest_timeTravel(fromCity,toCity);
    }

    public int getNumberOfPeopleOn_Service( Date date,String airportName){

        return airportRepository.total_passengers(date,airportName);
    }

    public int calculateFlightFare_Service(Integer flightId){

        return airportRepository.calculate_ticket_price(flightId);

    }


    public String bookATicket_Service(Integer flightId, Integer passengerId){

        return airportRepository.book_tickets(flightId,passengerId);
    }

    public String cancelATicket_Service(Integer flightId,Integer passengerId){

        return airportRepository.cancel_tickets(flightId,passengerId);
    }


    public int countOfBookingsDoneByPassengerAllCombined_Service(Integer passengerId){
        return airportRepository.passengers_bookings(passengerId);
    }

    public void addFlight_Service( Flight flight){

        airportRepository.add_Flight(flight);

    }


    public String getAirportNameFromFlightId_Service(Integer flightId){

        return airportRepository.airportName_From_FlightId(flightId);
    }
    public int calculateRevenueOfAFlight_Service(Integer flightId){


        return airportRepository.calculate_total_revenue(flightId);
    }

    public void addPassenger_Service(Passenger passenger){

        airportRepository.add_Passanger(passenger);

    }
}
