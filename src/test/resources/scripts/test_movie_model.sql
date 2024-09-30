-- country table definition
CREATE TABLE country (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

-- director table definition
CREATE TABLE director (
    id BIGINT AUTO_INCREMENT NOT NULL,
    full_name VARCHAR(255),
    PRIMARY KEY (id)
);

-- genre table definition
CREATE TABLE genre (
    id BIGINT AUTO_INCREMENT NOT NULL,
    label VARCHAR(255),
    PRIMARY KEY (id)
);

-- movie table definition
CREATE TABLE movie (
    id BIGINT AUTO_INCREMENT NOT NULL,
    duration INT,
    rating FLOAT,
    title VARCHAR(255),
    year INT,
    director_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_movie_director FOREIGN KEY (director_id) REFERENCES director(id)
);

-- country_movies table definition
CREATE TABLE country_movies (
    country_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    PRIMARY KEY (country_id, movie_id),
    CONSTRAINT fk_country_movies_country FOREIGN KEY (country_id) REFERENCES country(id),
    CONSTRAINT fk_country_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);

-- director_movies table definition
CREATE TABLE director_movies (
    director_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    PRIMARY KEY (director_id, movie_id),
    UNIQUE (movie_id),
    CONSTRAINT fk_director_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT fk_director_movies_director FOREIGN KEY (director_id) REFERENCES director(id)
);

-- genre_movies table definition
CREATE TABLE genre_movies (
    genre_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    PRIMARY KEY (genre_id, movie_id),
    CONSTRAINT fk_genre_movies_genre FOREIGN KEY (genre_id) REFERENCES genre(id),
    CONSTRAINT fk_genre_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);
