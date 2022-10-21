create table if not exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   city_id INT
);
