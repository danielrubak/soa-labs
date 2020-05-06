BEGIN;
    DROP SCHEMA library CASCADE;
    CREATE SCHEMA library;

    CREATE TABLE library.Reader (
        id SERIAL PRIMARY KEY,
        name varchar(100) NOT NULL,
        surname varchar(100) NOT NULL
    );
COMMIT;