CREATE TABLE country (
                         id bigint AUTO_INCREMENT NOT NULL,
                         country_name varchar(128) NULL,
                         CONSTRAINT country_pkey PRIMARY KEY (id)
);

CREATE TABLE director (
                          id bigint AUTO_INCREMENT NOT NULL,
                          full_name varchar(128) NULL,
                          CONSTRAINT director_pkey PRIMARY KEY (id)
);

CREATE TABLE genre (
                       id bigint AUTO_INCREMENT NOT NULL,
                       genre_label varchar(128) NULL,
                       CONSTRAINT genre_pkey PRIMARY KEY (id)
);

CREATE TABLE movie (
                       id bigint AUTO_INCREMENT NOT NULL,
                       duration int NULL,
                       rating float NULL,
                       movie_title varchar(255) NULL,
                       movie_year int NULL,
                       CONSTRAINT movie_pkey PRIMARY KEY (id)
);

CREATE TABLE country_movies (
                                country_id bigint NOT NULL,
                                movie_id bigint NOT NULL,
                                CONSTRAINT country_movies_pkey PRIMARY KEY (country_id, movie_id),
                                CONSTRAINT fk_country_movies_country FOREIGN KEY (country_id) REFERENCES country(id),
                                CONSTRAINT fk_country_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE director_movies (
                                 director_id bigint NOT NULL,
                                 movie_id bigint NOT NULL,
                                 CONSTRAINT director_movies_pkey PRIMARY KEY (director_id, movie_id),
                                 CONSTRAINT uk_director_movies_movie UNIQUE (movie_id),
                                 CONSTRAINT fk_director_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id),
                                 CONSTRAINT fk_director_movies_director FOREIGN KEY (director_id) REFERENCES director(id)
);

CREATE TABLE genre_movies (
                              genre_id bigint NOT NULL,
                              movie_id bigint NOT NULL,
                              CONSTRAINT genre_movies_pkey PRIMARY KEY (genre_id, movie_id),
                              CONSTRAINT fk_genre_movies_genre FOREIGN KEY (genre_id) REFERENCES genre(id),
                              CONSTRAINT fk_genre_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);

ALTER TABLE country ADD CONSTRAINT unique_country_name UNIQUE (country_name);
ALTER TABLE genre ADD CONSTRAINT unique_genre_label UNIQUE (genre_label);
ALTER TABLE director ADD CONSTRAINT unique_director_full_name UNIQUE (full_name);
