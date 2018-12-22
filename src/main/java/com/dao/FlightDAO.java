package com.dao;

import com.model.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class FlightDAO extends GeneralDAO<Flight> {

    private static final String MOST_POPULAR_CITY_TO_QUERY =
            "SELECT * FROM " +
                    "(SELECT F.CITY_TO FROM FLIGHT F, FLIGHT_PASSENGER FL_P " +
                    "WHERE F.ID = FL_P.FLIGHT_ID " +
                    "GROUP BY F.CITY_TO " +
                    "ORDER BY COUNT(FL_P.FLIGHT_ID) DESC)" +
                    "WHERE ROWNUM < 11";

    private static final String FLIGHTS_BY_MOST_POPULAR_CITY_TO_QUERY =
            "SELECT * FROM " +
                    "(SELECT F.* FROM FLIGHT F, FLIGHT_PASSENGER FL_P " +
                    "WHERE F.CITY_TO = :CITY " +
                    "AND F.ID = FL_P.FLIGHT_ID " +
                    "GROUP BY F.ID, F.PLANE, F.DATE_FLIGHT, F.CITY_FROM, F.CITY_TO " +
                    "ORDER BY COUNT(FL_P.FLIGHT_ID) DESC)" +
                    "WHERE ROWNUM < 11";

    private static final String MOST_POPULAR_CITY_FROM_QUERY =
            "SELECT * FROM " +
                    "(SELECT F.CITY_FROM FROM FLIGHT F, FLIGHT_PASSENGER FL_P " +
                    "WHERE F.ID = FL_P.FLIGHT_ID " +
                    "GROUP BY F.CITY_FROM " +
                    "ORDER BY COUNT(FL_P.FLIGHT_ID) DESC)" +
                    "WHERE ROWNUM < 11";

    private static final String FLIGHTS_BY_MOST_POPULAR_CITY_FROM_QUERY =
            "SELECT * FROM" +
                    "(SELECT F.* FROM FLIGHT F, FLIGHT_PASSENGER FL_P " +
                    "WHERE F.CITY_FROM = :CITY " +
                    "AND F.ID = FL_P.FLIGHT_ID " +
                    "GROUP BY F.ID, F.PLANE, F.DATE_FLIGHT, F.CITY_FROM, F.CITY_TO " +
                    "ORDER BY COUNT(FL_P.FLIGHT_ID) DESC)" +
                    "WHERE ROWNUM < 11";

    private static final String SELECT_BY_PARTICULAR_DAY =
            "SELECT * FROM FLIGHT F WHERE TRUNC(F.DATE_FLIGHT) = :DATE";

    private static final String SELECT_BY_DATE_PERIOD =
            "SELECT * FROM FLIGHT F WHERE F.DATE_FLIGHT <= :DATE_TO AND " +
                    "F.DATE_FLIGHT >= :DATE_FROM";

    private static final String SELECT_BY_CITY_TO =
            "SELECT * FROM FLIGHT F WHERE F.CITY_TO = :CITY";

    private static final String SELECT_BY_CITY_FROM =
            "SELECT * FROM FLIGHT F WHERE F.CITY_FROM = :CITY";

    private static final String SELECT_BY_PLANE_MODEL =
            "SELECT F.* FROM FLIGHT F, PLANE P " +
                    "WHERE F.PLANE = P.ID AND " +
                    "P.MODEL = :MODEL";

    @PersistenceContext
    private EntityManager entityManager;

    public FlightDAO() {
        setEntityManager(entityManager);
        setTypeParameterOfClass(Flight.class);
    }

    public List<Flight> selectByParticularDay(Date particularDate) throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(SELECT_BY_PARTICULAR_DAY, Flight.class);
            query.setParameter("DATE", particularDate);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selection is failed");
        }
    }

    public List<Flight> selectByDatePeriod(Date dateFrom, Date dateTo) throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(SELECT_BY_DATE_PERIOD, Flight.class);
            query.setParameter("DATE_FROM", dateFrom);
            query.setParameter("DATE_TO", dateTo);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selection is failed");
        }
    }

    public List<Flight> selectByCityTo(String cityTo) throws RuntimeException {
        return selectByCity(cityTo, SELECT_BY_CITY_TO);
    }

    public List<Flight> selectByCityFrom(String cityFrom) throws RuntimeException {
        return selectByCity(cityFrom, SELECT_BY_CITY_FROM);
    }

    public List<Flight> selectByPlaneModel(String planeModel) throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(SELECT_BY_PLANE_MODEL, Flight.class);
            query.setParameter("MODEL", planeModel);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selection is failed");
        }
    }

    public Map<String, List<Flight>> selectMostPopularFrom() throws RuntimeException {
        return selectMostPopularFlights(MOST_POPULAR_CITY_FROM_QUERY, FLIGHTS_BY_MOST_POPULAR_CITY_FROM_QUERY);
    }

    public Map<String, List<Flight>> selectMostPopularTo() throws RuntimeException {
        return selectMostPopularFlights(MOST_POPULAR_CITY_TO_QUERY, FLIGHTS_BY_MOST_POPULAR_CITY_TO_QUERY);
    }

    @Override
    public Flight save(Flight obj) throws RuntimeException {
        return super.save(obj);
    }

    @Override
    public void delete(long id) throws RuntimeException {
        super.delete(id);
    }

    @Override
    public Flight update(Flight obj) throws RuntimeException {
        return super.update(obj);
    }

    @Override
    public Flight findById(long id) throws IllegalArgumentException {
        return super.findById(id);
    }

    private Map<String, List<Flight>> selectMostPopularFlights
            (String mostPopularCityQuery, String flightsByCityQuery) throws RuntimeException {
        try {
            Query selectCitesQuery = entityManager.createNativeQuery(mostPopularCityQuery);

            Map<String, List<Flight>> mostPopular = new HashMap<>();
            List<String> mostPopularCity = selectCitesQuery.getResultList();

            for (String city : mostPopularCity) {
                Query selectFlightsQuery = entityManager.createNativeQuery(flightsByCityQuery, Flight.class);
                selectFlightsQuery.setParameter("CITY", city);
                mostPopular.put(city, selectFlightsQuery.getResultList());
            }

            return mostPopular;
        } catch (RuntimeException e) {
            throw new RuntimeException("Selection is failed");
        }
    }

    private List<Flight> selectByCity(String city, String request) throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(request, Flight.class);
            query.setParameter("CITY", city);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selection is failed");
        }
    }
}
