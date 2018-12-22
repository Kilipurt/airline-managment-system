package com.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PASSENGER")
public class Passenger {
    private Long id;
    private String lastName;
    private String nationality;
    private Date dateOfBirth;
    private String passportCode;
    private List<Flight> flights;

    @Id
    @SequenceGenerator(name = "PASSENGER_SEQ", sequenceName = "PASSENGER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSENGER_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "NATIONALITY")
    public String getNationality() {
        return nationality;
    }

    @Column(name = "DATE_OF_BIRTH")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(name = "PASSPORT_CODE")
    public String getPassportCode() {
        return passportCode;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FLIGHT_PASSENGER",
            joinColumns = @JoinColumn(name = "PASSENGER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FLIGHT_ID"))
    public List<Flight> getFlights() {
        return flights;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassportCode(String passportCode) {
        this.passportCode = passportCode;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", passportCode='" + passportCode + '\'' +
                //", flights=" + flights +
                '}';
    }
}
