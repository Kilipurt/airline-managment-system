CREATE TABLE FLIGHT (
    ID NUMBER PRIMARY KEY,
	PLANE NUMBER,
    CONSTRAINT PLANE_FK FOREIGN KEY (PLANE)
	  REFERENCES PLANE (ID) ON DELETE SET NULL,
	DATE_FLIGHT DATE NOT NULL,
	CITY_FROM NVARCHAR2(50) NOT NULL,
	CITY_TO NVARCHAR2(50) NOT NULL
);