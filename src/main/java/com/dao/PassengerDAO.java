package com.dao;

import com.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class PassengerDAO extends GeneralDAO<Passenger> {

    private static final String REGULAR_PASSENGERS_QUERY = "SELECT P.ID, P.LAST_NAME, P.NATIONALITY, P.DATE_OF_BIRTH, P.PASSPORT_CODE " +
            "FROM PASSENGER P, FLIGHT_PASSENGER FL_P, FLIGHT F " +
            "WHERE P.ID = FL_P.PASSENGER_ID AND " +
            "    FL_P.FLIGHT_ID = F.ID AND " +
            "    TO_NUMBER(TO_CHAR(F.DATE_FLIGHT, 'YYYY')) = :YEAR " +
            "GROUP BY P.ID, P.LAST_NAME, P.NATIONALITY, P.DATE_OF_BIRTH, P.PASSPORT_CODE " +
            "HAVING COUNT(FL_P.FLIGHT_ID) > 25";

    @PersistenceContext
    private EntityManager entityManager;

    public PassengerDAO() {
        setEntityManager(entityManager);
        setTypeParameterOfClass(Passenger.class);
    }

    public List<Passenger> selectRegularPassengers(int year) throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(REGULAR_PASSENGERS_QUERY, Passenger.class);
            query.setParameter("YEAR", year);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selected is failed");
        }
    }

    @Override
    public Passenger save(Passenger obj) throws RuntimeException {
        return super.save(obj);
    }

    @Override
    public void delete(long id) throws RuntimeException {
        super.delete(id);
    }

    @Override
    public Passenger update(Passenger obj) throws RuntimeException {
        return super.update(obj);
    }

    @Override
    public Passenger findById(long id) throws IllegalArgumentException {
        return super.findById(id);
    }
}
