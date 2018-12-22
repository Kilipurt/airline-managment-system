package com.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "FLIGHT")
public class Flight {
    private Long id;
    private Plane plane;
    //@JsonSerialize(using = DateSerializer.class)
    private Date dateFlight;
    private String cityFrom;
    private String cityTo;
    private List<Passenger> passengers;

    @Id
    @SequenceGenerator(name = "FLIGHT_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FLIGHT_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLANE")
    public Plane getPlane() {
        return plane;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    @Column(name = "DATE_FLIGHT")
    public Date getDateFlight() {
        return dateFlight;
    }

    @Column(name = "CITY_FROM")
    public String getCityFrom() {
        return cityFrom;
    }

    @Column(name = "CITY_TO")
    public String getCityTo() {
        return cityTo;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FLIGHT_PASSENGER",
            joinColumns = @JoinColumn(name = "FLIGHT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PASSENGER_ID"))
    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setDateFlight(Date dateFlight) {
        this.dateFlight = dateFlight;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", plane=" + plane +
                //", passengers=" + passengers +
                ", dateFlight=" + dateFlight +
                ", cityFrom='" + cityFrom + '\'' +
                ", cityTo='" + cityTo + '\'' +
                '}';
    }
}
