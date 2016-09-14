CREATE TABLE IF NOT EXISTS movies (
   id int PRIMARY KEY auto_increment,
   name VARCHAR,
   url VARCHAR
);

CREATE TABLE IF NOT EXISTS reviews (
   id INTEGER PRIMARY KEY auto_increment,
   movie_id INTEGER,
   rating INTEGER,
   comment VARCHAR,
   FOREIGN KEY(movie_id) REFERENCES public.movies(id)
);