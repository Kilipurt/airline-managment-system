package com.service.filter;

import com.model.Filter;
import com.model.Flight;

import java.util.List;

public abstract class Criteria {
    private static Filter filter;

    public static Filter getFilter() {
        return filter;
    }

    public static void setFilter(Filter filter) {
        Criteria.filter = filter;
    }

    public abstract List<Flight> meets(List<Flight> flights);
}
