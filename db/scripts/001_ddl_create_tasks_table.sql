CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    description TEXT,
    created     TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    done        BOOLEAN
);