CREATE DATABASE petstore;
\c petstore;

CREATE TABLE pets (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);
