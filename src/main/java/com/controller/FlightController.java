package com.controller;

import com.exception.BadRequestException;
import com.model.Filter;
import com.model.Flight;
import com.service.FlightService;
import com.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class FlightController {

    private FlightService flightService;
    private JsonUtil<Flight> jsonUtil;

    @Autowired
    public FlightController(FlightService flightService, JsonUtil<Flight> jsonUtil) {
        this.flightService = flightService;
        this.jsonUtil = jsonUtil;
        jsonUtil.setTypeParameterOfClass(Flight.class);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/flight/mostPopularTo", produces = "text/plain")
    public @ResponseBody
    String mostPopularTo() {
        try {
            Map<String, List<Flight>> popularTo = flightService.mostPopularTo();
            Set<String> citiesTo = popularTo.keySet();

            StringBuilder popularToString = new StringBuilder();
            for (String city : citiesTo) {
                popularToString.append(city).append(": ").append(popularTo.get(city));
            }

            return popularToString.toString();
        } catch (RuntimeException e) {
            return "500: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/flight/mostPopularFrom", produces = "text/plain")
    public @ResponseBody
    String mostPopularFrom() {
        try {
            Map<String, List<Flight>> popularFrom = flightService.mostPopularFrom();
            Set<String> citiesFrom = popularFrom.keySet();

            StringBuilder popularFromString = new StringBuilder();
            for (String city : citiesFrom) {
                popularFromString.append(city).append(": ").append(popularFrom.get(city));
            }

            return popularFromString.toString();
        } catch (RuntimeException e) {
            return "500: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/flight/flightsByDate",
            params = {"particularDay", "dateFrom", "dateTo", "cityFrom", "cityTo", "planeModel"},
            produces = "text/plain")
    public @ResponseBody
    String flightsByDate(@RequestParam(value = "particularDay") String particularDay,
                         @RequestParam(value = "dateFrom") String dateFrom,
                         @RequestParam(value = "dateTo") String dateTo,
                         @RequestParam(value = "cityFrom") String cityFrom,
                         @RequestParam(value = "cityTo") String cityTo,
                         @RequestParam(value = "planeModel") String planeModel) {
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

            Date particularDayDate = null;
            if (!particularDay.isEmpty()) {
                particularDayDate = format.parse(particularDay);
            }

            Date dateFromDate = null;
            if (!dateFrom.isEmpty()) {
                dateFromDate = format.parse(dateFrom);
            }

            Date dateToDate = null;
            if (!dateTo.isEmpty()) {
                dateToDate = format.parse(dateTo);
            }

            Filter filter = new Filter(particularDayDate, dateFromDate, dateToDate, cityFrom, cityTo, planeModel);
            return flightService.flightsByDate(filter).toString();
        } catch (RuntimeException e) {
            return "500: " + e.getMessage();
        } catch (ParseException e) {
            return "400: Wrong enter date";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/flight/save", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) {
        try {
            Flight flight = jsonUtil.getObject(jsonUtil.getJsonString(req));
            flightService.save(flight);
            return "ok";
        } catch (RuntimeException | IOException e) {
            return "500 : " + e.getMessage();
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/flight/update", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        try {
            Flight flight = jsonUtil.getObject(jsonUtil.getJsonString(req));
            flightService.update(flight);
            return "ok";
        } catch (RuntimeException e) {
            return "500 : " + e.getMessage();
        } catch (IOException e) {
            return "500: Can't read request";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/flight/delete", params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String delete(
            @RequestParam(value = "id") String id
    ) {
        try {
            flightService.delete(Long.parseLong(id));
            return "ok";
        } catch (NumberFormatException e) {
            return "400: Wrong enter id. Please use only numbers";
        } catch (RuntimeException e) {
            return "500 : " + e.getMessage();
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/flight/find", params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String find(@RequestParam(value = "id") String id) {
        try {
            return flightService.findById(Long.parseLong(id)).toString();
        } catch (NumberFormatException e) {
            return "400: Wrong enter id " + id;
        } catch (RuntimeException e) {
            return "500 : " + e.getMessage();
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }
}
