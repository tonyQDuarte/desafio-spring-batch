DROP TABLE movie_studio IF EXISTS;
DROP TABLE movie_producer IF EXISTS;
DROP TABLE movie IF EXISTS;
DROP TABLE studio IF EXISTS;
DROP TABLE producer IF EXISTS;

CREATE TABLE movie  (
    movie_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    year INTEGER,  
    title VARCHAR(100),
    winner BOOLEAN
);

CREATE TABLE studio (
	studio_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE producer (
	producer_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100)
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
        P1.NAME AS PRODUCER,
        M1.YEAR AS PREVIOUSWIN,
        M2.YEAR AS FOLLOWINGWIN,
        M2.YEAR - M1.YEAR AS INTERVALO
    FROM
        MOVIE M1
        JOIN MOVIE_PRODUCER MP1 ON (MP1.MOVIE_ID = M1.MOVIE_ID)
        JOIN PRODUCER P1 ON (P1.PRODUCER_ID = MP1.PRODUCER_ID)
        JOIN MOVIE M2 ON (M2.TITLE <> M1.TITLE AND M2.WINNER = TRUE AND M2.YEAR > M1.YEAR)
        JOIN MOVIE_PRODUCER MP2 ON (MP2.MOVIE_ID = M2.MOVIE_ID)
        JOIN PRODUCER P2 ON (P2.PRODUCER_ID = MP2.PRODUCER_ID)
    WHERE
        M1.WINNER = TRUE 
        AND P1.NAME = P2.NAME
    GROUP BY
        INTERVALO, PRODUCER, PREVIOUSWIN, FOLLOWINGWIN;