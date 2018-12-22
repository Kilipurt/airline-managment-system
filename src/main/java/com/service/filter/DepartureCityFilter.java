package com.service.filter;

import com.model.Flight;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DepartureCityFilter extends Criteria {

    @Override
    public List<Flight> meets(List<Flight> flights) {
        if (Criteria.getFilter().getCityFrom() == null || Criteria.getFilter().getCityFrom().isEmpty()) {
            return flights;
        }

        return flights.stream().filter(flight ->
                (flight.getCityFrom().equals(Criteria.getFilter().getCityFrom()))).collect(toList());
    }
}
