CREATE TABLE FLIGHT_PASSENGER (
    FLIGHT_ID NUMBER,
    CONSTRAINT FLIGHT_FK FOREIGN KEY(FLIGHT_ID)
        REFERENCES FLIGHT(ID),
    PASSENGER_ID NUMBER,
    CONSTRAINT PASSENGER_FK FOREIGN KEY(PASSENGER_ID)
        REFERENCES PASSENGER(ID)
);