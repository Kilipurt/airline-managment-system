package com.service;

import com.dao.PassengerDAO;
import com.exception.BadRequestException;
import com.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PassengerService {

    private PassengerDAO passengerDAO;

    @Autowired
    public PassengerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    public List<Passenger> regularPassengers(int year) throws RuntimeException, BadRequestException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (year > calendar.get(Calendar.YEAR) || year <= 0) {
            throw new BadRequestException("Wrong enter year " + year);
        }

        return passengerDAO.selectRegularPassengers(year);
    }

    public Passenger save(Passenger passenger) throws RuntimeException, BadRequestException {
        validatePassenger(passenger);
        return passengerDAO.save(passenger);
    }

    public Passenger update(Passenger passenger) throws RuntimeException, BadRequestException {
        if (passenger.getId() <= 0) {
            throw new BadRequestException("Wrong enter id " + passenger.getId());
        }

        validatePassenger(passenger);
        return passengerDAO.update(passenger);
    }

    public void delete(long id) throws RuntimeException, BadRequestException {
        if (id <= 0) {
            throw new BadRequestException("Wrong enter id " + id);
        }

        passengerDAO.delete(id);
    }

    public Passenger findById(long id) throws RuntimeException, BadRequestException {
        if (id <= 0) {
            throw new BadRequestException("Wrong enter id " + id);
        }

        return passengerDAO.findById(id);
    }

    private void validatePassenger(Passenger passenger) throws BadRequestException {
        if (passenger.getDateOfBirth() != null && passenger.getDateOfBirth().compareTo(new Date()) > 0) {
            throw new BadRequestException("Wrong enter date of birth");
        }

        if (passenger.getPassportCode() == null || passenger.getPassportCode().isEmpty()) {
            throw new BadRequestException("Wrong enter passport code");
        }
    }
}
