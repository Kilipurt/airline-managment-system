package com.service;

import com.dao.PlaneDAO;
import com.exception.BadRequestException;
import com.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlaneService {

    private PlaneDAO planeDAO;

    @Autowired
    public PlaneService(PlaneDAO planeDAO) {
        this.planeDAO = planeDAO;
    }

    public List<Plane> oldPlanes() throws RuntimeException {
        return planeDAO.selectOldPlanes();
    }

    public List<Plane> regularPlanes(int year) throws RuntimeException, BadRequestException {
        if (year <= 0) {
            throw new BadRequestException("Wrong enter year " + year);
        }

        return planeDAO.selectRegularPlanes(year);
    }

    public Plane save(Plane plane) throws RuntimeException, BadRequestException {
        validatePlane(plane);
        return planeDAO.save(plane);
    }

    public Plane update(Plane plane) throws RuntimeException, BadRequestException {
        if (plane.getId() <= 0) {
            throw new BadRequestException("Wrong enter id " + plane.getId());
        }

        validatePlane(plane);
        return planeDAO.update(plane);
    }

    public void delete(long id) throws RuntimeException, BadRequestException {
        if (id <= 0) {
            throw new BadRequestException("Wrong enter id " + id);
        }

        planeDAO.delete(id);
    }

    public Plane findById(long id) throws RuntimeException, BadRequestException {
        if (id <= 0) {
            throw new BadRequestException("Wrong enter id " + id);
        }

        return planeDAO.findById(id);
    }

    private void validatePlane(Plane plane) throws BadRequestException {
        if (plane.getModel() == null || plane.getModel().isEmpty()) {
            throw new BadRequestException("Please enter plane's model and try again");
        }

        if (plane.getCode() == null || plane.getCode().isEmpty()) {
            throw new BadRequestException("Please enter plane's code and try again");
        }

        if (plane.getYearProduced() == null || plane.getYearProduced().compareTo(new Date()) > 0) {
            throw new BadRequestException("Wrong enter plane's year produced");
        }
    }
}
