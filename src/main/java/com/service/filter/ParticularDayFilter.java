package com.service.filter;

import com.model.Flight;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ParticularDayFilter extends Criteria {

    @Override
    public List<Flight> meets(List<Flight> flights) {
        if (Criteria.getFilter().getParticularDay() == null) {
            return flights;
        }

        return flights.stream().filter(flight ->
                flight.getDateFlight().compareTo(Criteria.getFilter().getParticularDay()) == 0).collect(toList());
    }
}
