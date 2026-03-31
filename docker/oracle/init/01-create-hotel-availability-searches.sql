CREATE TABLE HOTEL_AVAILABILITY_SEARCHES (
                                             ID VARCHAR2(100) PRIMARY KEY,
                                             HOTEL_ID  varchar2(50) NOT NULL,
                                             CHECK_IN_DATE DATE NOT NULL,
                                             CHECK_OUT_DATE DATE NOT NULL,
                                             AGES VARCHAR2(4000) NOT NULL,
                                             AGES_HASH VARCHAR2(1000) NOT NULL,
                                             REQUESTED_AT TIMESTAMP NOT NULL
);

