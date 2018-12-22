package com.service.filter;

import com.model.Flight;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlaneModelFilter extends Criteria {

    @Override
    public List<Flight> meets(List<Flight> flights) {
        if (Criteria.getFilter().getPlaneModel() == null || Criteria.getFilter().getPlaneModel().isEmpty()) {
            return flights;
        }

        return flights.stream().filter(flight ->
                flight.getPlane().getModel().equals(Criteria.getFilter().getPlaneModel())).collect(toList());
    }
}
