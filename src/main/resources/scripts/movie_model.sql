-- public.country definition

-- Drop table

-- DROP TABLE country;

CREATE TABLE countries (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT country_pkey PRIMARY KEY (id)
);


-- public.director definition

-- Drop table

-- DROP TABLE director;

CREATE TABLE directors (
	id bigserial NOT NULL,
	full_name varchar(255) NULL,
	CONSTRAINT director_pkey PRIMARY KEY (id)
);


-- public.genre definition

-- Drop table

-- DROP TABLE genre;

CREATE TABLE genres (
	id bigserial NOT NULL,
	"label" varchar(255) NULL,
	CONSTRAINT genre_pkey PRIMARY KEY (id)
);


-- public.movie definition

-- Drop table

-- DROP TABLE movie;

CREATE TABLE movies (
	id bigserial NOT NULL,
	duration int4 NULL,
	imdb_id varchar(25) NULL,
	movie_poster_uri varchar(255) NULL,
	rating float4 NULL,
	title varchar(255) NULL,
	"year" int4 NULL,
	director_id int8 NULL,
	CONSTRAINT movie_pkey PRIMARY KEY (id),
	CONSTRAINT fkbi47w3cnsfi30gc1nu2avgra2 FOREIGN KEY (director_id) REFERENCES directors(id)
);


-- public.country_movies definition

-- Drop table

-- DROP TABLE country_movies;

CREATE TABLE movie_countries (
	country_id int8 NOT NULL,
	movie_id int8 NOT NULL,
	CONSTRAINT country_movies_pkey PRIMARY KEY (country_id, movie_id),
	CONSTRAINT fkejeamori79pfevpaobkmv5qhp FOREIGN KEY (country_id) REFERENCES countries(id),
	CONSTRAINT fkj9mof2ff4upah22fm5ikkgkk0 FOREIGN KEY (movie_id) REFERENCES movies(id)
);


-- public.directors(id)_movies definition

-- Drop table

-- DROP TABLE director_movies;

CREATE TABLE director_movies (
	director_id int8 NOT NULL,
	movie_id int8 NOT NULL,
	CONSTRAINT director_movies_pkey PRIMARY KEY (director_id, movie_id),
	CONSTRAINT uk_r4hx2t9ljletxp7nvcajri29e UNIQUE (movie_id),
	CONSTRAINT fka1cjleod4t86rd7oquys5heoe FOREIGN KEY (movie_id) REFERENCES movies(id),
	CONSTRAINT fkdl3n3od7w3v7g2tjrte89e7aw FOREIGN KEY (director_id) REFERENCES directors(id)
);


-- public.genre_movies definition

-- Drop table

-- DROP TABLE genre_movies;

CREATE TABLE movie_genres (
	genre_id int8 NOT NULL,
	movie_id int8 NOT NULL,
	CONSTRAINT genre_movies_pkey PRIMARY KEY (genre_id, movie_id),
	CONSTRAINT fkna0q0v9ce4wywop8o440cv70p FOREIGN KEY (genre_id) REFERENCES genres(id),
	CONSTRAINT fko0p8dudi1ar9iy1oiq7gesymb FOREIGN KEY (movie_id) REFERENCES movies(id)
);