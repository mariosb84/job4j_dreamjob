CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  email TEXT,
  password TEXT
);