-- suppression de la table 
DROP DATABASE IF EXISTS movie_db;

DROP USER IF EXISTS movie_user;

--------------------------------------------
-- Create user
--------------------------------------------
CREATE USER movie_user WITH PASSWORD 'movie_pwd';

--------------------------------------------
-- Create database database_name
--------------------------------------------


CREATE DATABASE movie_db
    WITH 
    OWNER = movie_user
    ENCODING = 'UTF8'
--    LC_COLLATE = 'en_US.utf8'
--    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE postgres
    IS 'My movie database for dev environnement';
-- improve selects on partitionned tables
ALTER DATABASE movie_db SET constraint_exclusion=on;

-- select database before
CREATE SCHEMA movie_api AUTHORIZATION movie_user;




