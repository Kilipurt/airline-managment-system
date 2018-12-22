package com.service;

import com.dao.FlightDAO;
import com.exception.BadRequestException;
import com.model.Filter;
import com.model.Flight;
import com.service.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightService {
    private FlightDAO flightDAO;

    @Autowired
    public FlightService(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    public List<Flight> flightsByDate(Filter filter) throws RuntimeException, BadRequestException {
        Criteria.setFilter(filter);

        AndCriteria searchCriteria = new AndCriteria(Arrays.asList(
                new ParticularDayFilter(),
                new DatePeriodFilter(),
                new ArrivalCityFilter(),
                new DepartureCityFilter(),
                new PlaneModelFilter()
        ));

        return searchCriteria.meets(initFlightsForFiltration());
    }

    public Map<String, List<Flight>> mostPopularTo() throws RuntimeException {
        return flightDAO.selectMostPopularTo();
    }

    public Map<String, List<Flight>> mostPopularFrom() throws RuntimeException {
        return flightDAO.selectMostPopularFrom();
    }

    public Flight save(Flight flight) throws RuntimeException, BadRequestException {
        validateFlight(flight);
        return flightDAO.save(flight);
    }

    public Flight update(Flight flight) throws RuntimeException, BadRequestException {
        if (flight.getId() <= 0) {
            throw new BadRequestException("Wrong enter id " + flight.getId());
        }

        validateFlight(flight);
        return flightDAO.update(flight);
    }

    public void delete(long id) throws RuntimeException, BadRequestException {
        if (id <= 0) {
            throw new BadRequestException("Wrong enter id " + id);
        }

        flightDAO.delete(id);
    }

    public Flight findById(long id) throws RuntimeException, BadRequestException {
        if (id <= 0) {
            throw new BadRequestException("Wrong enter id " + id);
        }

        return flightDAO.findById(id);
    }

    private void validateFlight(Flight flight) throws BadRequestException {
        if (flight.getDateFlight() == null || flight.getDateFlight().compareTo(new Date()) < 0) {
            throw new BadRequestException("Wrong enter date " + flight.getDateFlight());
        }

        if (flight.getCityFrom() == null || flight.getCityFrom().isEmpty()) {
            throw new BadRequestException("Wrong enter city of departure " + flight.getCityFrom());
        }

        if (flight.getCityTo() == null || flight.getCityTo().isEmpty()) {
            throw new BadRequestException("Wrong enter city of arrival " + flight.getCityTo());
        }
    }

    private List<Flight> initFlightsForFiltration() throws BadRequestException {
        if (Criteria.getFilter().getParticularDay() != null) {
            return flightDAO.selectByParticularDay(Criteria.getFilter().getParticularDay());
        }

        if (Criteria.getFilter().getDateFrom() != null && Criteria.getFilter().getDateTo() != null) {
            return flightDAO.selectByDatePeriod(Criteria.getFilter().getDateFrom(), Criteria.getFilter().getDateTo());
        }

        if (Criteria.getFilter().getCityTo() != null && !Criteria.getFilter().getCityTo().isEmpty()) {
            return flightDAO.selectByCityTo(Criteria.getFilter().getCityTo());
        }

        if (Criteria.getFilter().getCityFrom() != null && !Criteria.getFilter().getCityFrom().isEmpty()) {
            return flightDAO.selectByCityFrom(Criteria.getFilter().getCityFrom());
        }

        if (Criteria.getFilter().getPlaneModel() != null && !Criteria.getFilter().getPlaneModel().isEmpty()) {
            return flightDAO.selectByPlaneModel(Criteria.getFilter().getPlaneModel());
        } else {
            throw new BadRequestException("Filter is empty");
        }
    }
}
