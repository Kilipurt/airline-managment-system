package com.controller;

import com.exception.BadRequestException;
import com.model.Passenger;
import com.service.PassengerService;
import com.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class PassengerController {
    private PassengerService passengerService;
    private JsonUtil<Passenger> jsonUtil;

    @Autowired
    public PassengerController(PassengerService passengerService, JsonUtil<Passenger> jsonUtil) {
        this.passengerService = passengerService;
        this.jsonUtil = jsonUtil;
        this.jsonUtil.setTypeParameterOfClass(Passenger.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/passenger/save", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) {
        try {
            Passenger passenger = jsonUtil.getObject(jsonUtil.getJsonString(req));
            passengerService.save(passenger);
            return "ok";
        } catch (IOException e) {
            return "500: Can't read request";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/passenger/update", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        try {
            Passenger passenger = jsonUtil.getObject(jsonUtil.getJsonString(req));
            passengerService.update(passenger);
            return "ok";
        } catch (IOException e) {
            return "500: Can't read request";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/passenger/delete", params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String delete(
            @RequestParam(value = "id") String id
    ) {
        try {
            passengerService.delete(Long.parseLong(id));
            return "ok";
        } catch (NumberFormatException e) {
            return "400: Wrong enter id. Please use only numbers";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/passenger/find", params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String find(@RequestParam(value = "id") String id) {
        try {
            return passengerService.findById(Long.parseLong(id)).toString();
        } catch (NumberFormatException e) {
            return "400: Wrong enter id " + id;
        } catch (RuntimeException | BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/passenger/regular", params = {"year"}, produces = "text/plain")
    public @ResponseBody
    String regularPassengers(@RequestParam(value = "year") String year) {
        try {
            return passengerService.regularPassengers(Integer.parseInt(year)).toString();
        } catch (NumberFormatException e) {
            return "400: " + "Wrong enter year " + year;
        } catch (RuntimeException | BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }
}
