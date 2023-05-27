package com.driver.repository;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Repository
public class AirportRepository {
    static int max_No_of_terminals=0;
    private Map<String,Airport> airportMap=new HashMap<>();
    private Map<Integer,Flight> flightMap=new HashMap<>();
    private Map<Integer,Passenger> passengerMap=new HashMap<>();
    private Map<Integer, HashSet<Integer>> bookingMap=new HashMap<>();
    public void add_Flight(Flight flight){
        flightMap.put(flight.getFlightId(),flight);
    }
    public void add_Passanger(Passenger passenger){
        passengerMap.put(passenger.getPassengerId(),passenger);
    }
    public void add_Airport(Airport airport){
        airportMap.put(airport.getAirportName(),airport);
        max_No_of_terminals=Math.max(max_No_of_terminals,airport.getNoOfTerminals());
    }

    public String largest_Airport(){
        String airport_name=null;
        int max=0;
        for(String airport:airportMap.keySet()){
            if(airportMap.get(airport).getNoOfTerminals()==max_No_of_terminals){
                if(airport_name==null){
                    airport_name=airport;
                }
                else if(airport_name.compareTo(airport)>0){
                    airport_name=airport;
                }
            }
        }
        return airport_name;
    }

    public String airportName_From_FlightId(Integer flightID){
        if(flightMap.containsKey(flightID)){
            return null;
        }
        for(String airport : airportMap.keySet()){
            if(airportMap.get(airport).getCity().equals(flightMap.get(flightID).getFromCity())){
                return airport;
            }
        }
        return null;
    }

    public double shortest_timeTravel(City fromCity,City toCity){
        double duration=Double.MAX_VALUE;
        for (Integer flight:flightMap.keySet()){
            if(flightMap.get(flight).getFromCity().equals(fromCity) && flightMap.get(flight).getToCity().equals(toCity)){
                duration=Math.min(duration,flightMap.get(flight).getDuration());
            }
        }
        if(duration==Double.MAX_VALUE) return -1;
        return duration;
    }

    public String book_tickets(Integer flightId,Integer passengerId){
        if(!flightMap.containsKey(flightId)) return "FAILURE";
        if(bookingMap.containsKey(flightId)){
            if(bookingMap.get(flightId).size()==flightMap.get(flightId).getMaxCapacity()) return "FAILURE";
            if(bookingMap.get(flightId).contains(passengerId)) return "FAILURE";
            else{
                HashSet<Integer> oldSet=bookingMap.get(flightId);
                oldSet.add(passengerId);
                bookingMap.put(flightId,oldSet);
                return "SUCCESS";
            }
        }
        else{
            HashSet<Integer> newSet=new HashSet<>();
            newSet.add(passengerId);
            bookingMap.put(flightId,newSet);
            return "SUCCESS";
        }
    }

    public String cancel_tickets(Integer flightId,Integer passengerId){
        if(!flightMap.containsKey(flightId)) return "FAILURE";
        if(bookingMap.containsKey(flightId)){
            if(!bookingMap.get(flightId).contains(passengerId)) return "FAILURE";
            else{
                HashSet<Integer> oldSet=bookingMap.get(flightId);
                oldSet.remove(passengerId);
                bookingMap.put(flightId,oldSet);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public int calculate_ticket_price(Integer flightId){
        int no_of_passangers=0;
        if(bookingMap.containsKey(flightId)){
            no_of_passangers=bookingMap.get(flightId).size();
        }
        return 3000+50*no_of_passangers;
    }

    public int calculate_total_revenue(Integer flightId){
        int no_of_passangers=0;
        if(bookingMap.containsKey(flightId)){
            no_of_passangers=bookingMap.get(flightId).size();
        }
        return 3000*no_of_passangers+50*no_of_passangers;
    }

    public int total_passengers(Date date,String airportName){
        int total=0;
        City cityName=airportMap.get(airportName).getCity();
        for (Integer flightID : bookingMap.keySet()){
            if(flightMap.get(flightID).getFlightDate().equals(date)){
                if(flightMap.get(flightID).getFromCity().equals(cityName) || flightMap.get(flightID).getToCity().equals(cityName)){
                    total+=bookingMap.get(flightID).size();
                }
            }
        }
        return total;
    }

    public int passengers_bookings(Integer passengerId){
        int tot=0;
        for(Integer flightId : bookingMap.keySet()){
            if(bookingMap.get(flightId).contains(passengerId)) tot++;
        }
        return tot;
    }
}
