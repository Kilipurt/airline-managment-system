package com.dao;

import com.model.Plane;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class PlaneDAO extends GeneralDAO<Plane> {

    private static final String OLD_PLANES_QUERY = "SELECT * FROM PLANE " +
            "WHERE TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) - TO_NUMBER(TO_CHAR(YEAR_PRODUCED, 'YYYY')) > 20";

    private static final String REGULAR_PLANES_QUERY =
            "SELECT P.ID, P.MODEL, P.CODE, P.YEAR_PRODUCED, P.AVG_FUEL_CONSUMPTION FROM PLANE P, FLIGHT F " +
                    "WHERE F.PLANE = P.ID " +
                    "AND TO_NUMBER (TO_CHAR(F.DATE_FLIGHT, 'YYYY')) = :year " +
                    "GROUP BY P.ID, P.MODEL, P.CODE, P.YEAR_PRODUCED, P.AVG_FUEL_CONSUMPTION " +
                    "HAVING COUNT(F.ID) > 300";

    @PersistenceContext
    private EntityManager entityManager;

    public PlaneDAO() {
        setEntityManager(this.entityManager);
        setTypeParameterOfClass(Plane.class);
    }

    public List<Plane> selectOldPlanes() throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(OLD_PLANES_QUERY, Plane.class);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selected is failed");
        }
    }

    public List<Plane> selectRegularPlanes(int year) throws RuntimeException {
        try {
            Query query = entityManager.createNativeQuery(REGULAR_PLANES_QUERY, Plane.class);
            query.setParameter("year", year);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Selection is failed");
        }
    }

    @Override
    public Plane save(Plane obj) throws RuntimeException {
        return super.save(obj);
    }

    @Override
    public void delete(long id) throws RuntimeException {
        super.delete(id);
    }

    @Override
    public Plane update(Plane obj) throws RuntimeException {
        return super.update(obj);
    }

    @Override
    public Plane findById(long id) throws IllegalArgumentException {
        return super.findById(id);
    }
}
