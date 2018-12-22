package com.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PLANE")
public class Plane {
    private Long id;
    private String model;
    private String code;
    private Date yearProduced;
    private Double avgFuelConsumption;

    @Id
    @SequenceGenerator(name = "PLANE_SEQ", sequenceName = "PLANE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANE_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    @Column(name = "YEAR_PRODUCED")
    public Date getYearProduced() {
        return yearProduced;
    }

    @Column(name = "AVG_FUEL_CONSUMPTION")
    public Double getAvgFuelConsumption() {
        return avgFuelConsumption;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setYearProduced(Date yearProduced) {
        this.yearProduced = yearProduced;
    }

    public void setAvgFuelConsumption(Double avgFuelConsumption) {
        this.avgFuelConsumption = avgFuelConsumption;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", code='" + code + '\'' +
                ", yearProduced=" + yearProduced +
                ", avgFuelConsumption=" + avgFuelConsumption +
                '}';
    }
}
