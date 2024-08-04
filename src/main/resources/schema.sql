CREATE TABLE IF NOT EXISTS employee (
                          employee_id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          email VARCHAR(255),
                          nationality VARCHAR(255),
                          old INTEGER
);

CREATE TABLE IF NOT EXISTS users (
                                      id BIGSERIAL PRIMARY KEY,
                                      username VARCHAR(255),
    password VARCHAR(255)
    );
