package com.service.filter;

import com.model.Flight;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ArrivalCityFilter extends Criteria {

    @Override
    public List<Flight> meets(List<Flight> flights) {
        if (Criteria.getFilter().getCityTo() == null || Criteria.getFilter().getCityTo().isEmpty()) {
            return flights;
        }

        return flights.stream().filter(flight ->
                flight.getCityTo().equals(Criteria.getFilter().getCityTo())).collect(toList());
    }
}
