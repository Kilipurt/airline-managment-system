package com.controller;

import com.exception.BadRequestException;
import com.model.Plane;
import com.service.PlaneService;
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
public class PlaneController {
    private PlaneService planeService;
    private JsonUtil<Plane> jsonUtil;

    @Autowired
    public PlaneController(PlaneService planeService, JsonUtil<Plane> jsonUtil) {
        this.jsonUtil = jsonUtil;
        this.planeService = planeService;
        this.jsonUtil.setTypeParameterOfClass(Plane.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/plane/save", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) {
        try {
            Plane plane = jsonUtil.getObject(jsonUtil.getJsonString(req));
            planeService.save(plane);
            return "ok";
        } catch (IOException e) {
            return "500: Can't read request";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/plane/update", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        try {
            Plane plane = jsonUtil.getObject(jsonUtil.getJsonString(req));
            planeService.update(plane);
            return "ok";
        } catch (IOException e) {
            return "500: Can't read request";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/plane/delete", params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String delete(
            @RequestParam(value = "id") String id
    ) {
        try {
            planeService.delete(Long.parseLong(id));
            return "ok";
        } catch (NumberFormatException e) {
            return "400: Wrong enter id. Please use only numbers";
        } catch (BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plane/find", params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String find(@RequestParam(value = "id") String id) {
        try {
            return planeService.findById(Long.parseLong(id)).toString();
        } catch (NumberFormatException e) {
            return "400: Wrong enter id " + id;
        } catch (RuntimeException | BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plane/old", produces = "text/plain")
    public @ResponseBody
    String oldPlanes() {
        try {
            return planeService.oldPlanes().toString();
        } catch (RuntimeException e) {
            return "400: " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plane/regular", params = {"year"}, produces = "text/plain")
    public @ResponseBody
    String regularPlanes(@RequestParam(value = "year") String year) {
        try {
            return planeService.regularPlanes(Integer.parseInt(year)).toString();
        } catch (NumberFormatException e) {
            return "400: " + "Wrong enter year " + year;
        } catch (RuntimeException | BadRequestException e) {
            return "400: " + e.getMessage();
        }
    }
}
