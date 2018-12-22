package com.service.filter;

import com.model.Flight;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DatePeriodFilter extends Criteria {

    @Override
    public List<Flight> meets(List<Flight> flights) {
        if (Criteria.getFilter().getDateFrom() == null || Criteria.getFilter().getDateTo() == null) {
            return flights;
        }

        return flights.stream().filter(flight ->
                (flight.getDateFlight().compareTo(Criteria.getFilter().getDateFrom()) >= 0 &&
                        flight.getDateFlight().compareTo(Criteria.getFilter().getDateTo()) <= 0)).collect(toList());
    }
}
