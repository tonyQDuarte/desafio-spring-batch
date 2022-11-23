DROP VIEW WINNING_PRODUCERS IF EXISTS;
DROP VIEW WINNING_MOVIES IF EXISTS;
DROP TABLE movie_studio IF EXISTS;
DROP TABLE movie_producer IF EXISTS;
DROP TABLE studio_movie IF EXISTS;
DROP TABLE producer_movie IF EXISTS;
DROP TABLE studio IF EXISTS;
DROP TABLE producer IF EXISTS;
DROP TABLE movie IF EXISTS;

CREATE TABLE studio (
	studio_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE producer (
	producer_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE movie  (
    movie_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "year" INTEGER,  
    title VARCHAR(100),
    winner BOOLEAN NOT NULL
);

CREATE TABLE movie_studio (
    movie_id BIGINT,
    studio_id BIGINT
);

ALTER TABLE movie_studio
    ADD FOREIGN KEY (movie_id) 
    REFERENCES movie(movie_id);

ALTER TABLE movie_studio
    ADD FOREIGN KEY (studio_id) 
    REFERENCES studio(studio_id);

CREATE TABLE movie_producer (
    movie_id  BIGINT,
    producer_id  BIGINT
);

ALTER TABLE movie_producer
    ADD FOREIGN KEY (movie_id) 
    REFERENCES movie(movie_id);

ALTER TABLE movie_producer
    ADD FOREIGN KEY (producer_id) 
    REFERENCES producer(producer_id);

CREATE TABLE studio_movie (
    studio_id  BIGINT,
    movie_id  BIGINT
);

ALTER TABLE studio_movie
    ADD FOREIGN KEY (movie_id) 
    REFERENCES movie(movie_id);

ALTER TABLE studio_movie
    ADD FOREIGN KEY (studio_id) 
    REFERENCES studio(studio_id);

CREATE TABLE producer_movie (
    producer_id  BIGINT,
    movie_id  BIGINT
);

ALTER TABLE producer_movie
    ADD FOREIGN KEY (producer_id) 
    REFERENCES producer(producer_id);

ALTER TABLE producer_movie
    ADD FOREIGN KEY (movie_id) 
    REFERENCES movie(movie_id);

CREATE OR REPLACE VIEW WINNING_MOVIES AS
    SELECT
        M1.MOVIE_ID AS MOVIE_ID,
        P1.NAME AS PRODUCER,
        M1."year" AS ANO 
    FROM
        MOVIE M1
        JOIN MOVIE_PRODUCER MP1 ON (MP1.MOVIE_ID = M1.MOVIE_ID)
        JOIN PRODUCER P1 ON (P1.PRODUCER_ID = MP1.PRODUCER_ID)
    WHERE
        M1.WINNER = TRUE;

CREATE OR REPLACE VIEW WINNING_PRODUCERS AS
    SELECT  
        WM.PRODUCER AS PRODUCER,
        WM.ANO AS PREVIOUSWIN,
        WM2.ANO AS FOLLOWINGWIN,
        WM2.ANO - WM.ANO AS INTERVALO
    FROM 
        WINNING_MOVIES WM,
        WINNING_MOVIES WM2 
    WHERE 
        WM.MOVIE_ID <> WM2.MOVIE_ID AND 
        WM.PRODUCER = WM2.PRODUCER AND 
        WM2.ANO = (SELECT MIN(WM3.ANO) FROM WINNING_MOVIES WM3 WHERE WM.MOVIE_ID <> WM3.MOVIE_ID AND WM.PRODUCER = WM3.PRODUCER AND WM3.ANO > WM.ANO)
    GROUP BY
        INTERVALO, PRODUCER, PREVIOUSWIN, FOLLOWINGWIN;
