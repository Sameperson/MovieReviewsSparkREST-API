package com.sameperson.movies;

import com.google.gson.Gson;
import com.sameperson.movies.dao.MovieDao;
import com.sameperson.movies.dao.Sql2oMovieDao;
import com.sameperson.movies.model.Movie;
import org.sql2o.Sql2o;

import static spark.Spark.after;
import static spark.Spark.post;
import static spark.Spark.get;

public class App {

    public static void main(String[] args) {

        Sql2o sql2o = new Sql2o("jdbc:h2:~/movieReviews.db:INIT=RUNSCRIPT from 'classpath:db/init.sql");
        MovieDao movieDao = new Sql2oMovieDao(sql2o);
        Gson gson = new Gson();

        post("/movies", "application/json", (req, res) -> {
            Movie movie = gson.fromJson(req.body(), Movie.class);
            movieDao.add(movie);
            res.status(201);
            return res;
        }, gson::toJson);

        get("/movies", "application/json", (req, res) -> movieDao.findAll(), gson::toJson);

        get("/movies/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Movie movie = movieDao.findById(id);
            return null;
        }, gson::toJson);

        after((req, res) -> res.type("application/json"));

    }

}
