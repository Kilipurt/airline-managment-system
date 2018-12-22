package com.model;

import java.util.Date;

public class Filter {
    private Date particularDay;
    private Date dateFrom;
    private Date dateTo;
    private String cityFrom;
    private String cityTo;
    private String planeModel;

    public Filter(Date particularDay, Date dateFrom, Date dateTo, String cityFrom, String cityTo, String planeModel) {
        this.particularDay = particularDay;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.planeModel = planeModel;
    }

    public Date getParticularDay() {
        return particularDay;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public void setParticularDay(Date particularDay) {
        this.particularDay = particularDay;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }
}
