-- public.country definition

-- Drop table

-- DROP TABLE country;

CREATE TABLE country (
                         id bigserial NOT NULL,
                         "name" varchar(255) NULL,
                         CONSTRAINT country_pkey PRIMARY KEY (id)
);


-- public.director definition

-- Drop table

-- DROP TABLE director;

CREATE TABLE director (
                          id bigserial NOT NULL,
                          full_name varchar(255) NULL,
                          CONSTRAINT director_pkey PRIMARY KEY (id)
);


-- public.genre definition

-- Drop table

-- DROP TABLE genre;

CREATE TABLE genre (
                       id bigserial NOT NULL,
                       "label" varchar(255) NULL,
                       CONSTRAINT genre_pkey PRIMARY KEY (id)
);


-- public.movie definition

-- Drop table

-- DROP TABLE movie;

CREATE TABLE movie (
                       id bigserial NOT NULL,
                       duration int4 NULL,
                       rating float4 NULL,
                       title varchar(255) NULL,
                       "year" int4 NULL,
                       CONSTRAINT movie_pkey PRIMARY KEY (id)
);


-- public.country_movies definition

-- Drop table

-- DROP TABLE country_movies;

CREATE TABLE country_movies (
                                country_id int8 NOT NULL,
                                movie_id int8 NOT NULL,
                                CONSTRAINT country_movies_pkey PRIMARY KEY (country_id, movie_id),
                                CONSTRAINT fkejeamori79pfevpaobkmv5qhp FOREIGN KEY (country_id) REFERENCES country(id),
                                CONSTRAINT fkj9mof2ff4upah22fm5ikkgkk0 FOREIGN KEY (movie_id) REFERENCES movie(id)
);


-- public.director_movies definition

-- Drop table

-- DROP TABLE director_movies;

CREATE TABLE director_movies (
                                 director_id int8 NOT NULL,
                                 movie_id int8 NOT NULL,
                                 CONSTRAINT director_movies_pkey PRIMARY KEY (director_id, movie_id),
                                 CONSTRAINT uk_r4hx2t9ljletxp7nvcajri29e UNIQUE (movie_id),
                                 CONSTRAINT fka1cjleod4t86rd7oquys5heoe FOREIGN KEY (movie_id) REFERENCES movie(id),
                                 CONSTRAINT fkdl3n3od7w3v7g2tjrte89e7aw FOREIGN KEY (director_id) REFERENCES director(id)
);


-- public.genre_movies definition

-- Drop table

-- DROP TABLE genre_movies;

CREATE TABLE genre_movies (
                              genre_id int8 NOT NULL,
                              movie_id int8 NOT NULL,
                              CONSTRAINT genre_movies_pkey PRIMARY KEY (genre_id, movie_id),
                              CONSTRAINT fkna0q0v9ce4wywop8o440cv70p FOREIGN KEY (genre_id) REFERENCES genre(id),
                              CONSTRAINT fko0p8dudi1ar9iy1oiq7gesymb FOREIGN KEY (movie_id) REFERENCES movie(id)
);

ALTER TABLE country ADD CONSTRAINT unique_country_name UNIQUE (name);
ALTER TABLE genre ADD CONSTRAINT unique_genre_label UNIQUE (label);
ALTER TABLE director ADD CONSTRAINT unique_director_full_name UNIQUE (full_name);
