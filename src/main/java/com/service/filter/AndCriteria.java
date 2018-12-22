package com.service.filter;

import com.model.Flight;

import java.util.List;

public class AndCriteria extends Criteria {

    private List<Criteria> criteriaList;

    public AndCriteria(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public List<Flight> meets(List<Flight> flights) {
        List<Flight> res = flights;

        for (Criteria criteria : criteriaList) {
            res.retainAll(criteria.meets(flights));
        }

        return res;
    }
}
