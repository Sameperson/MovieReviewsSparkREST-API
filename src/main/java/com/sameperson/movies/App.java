package com.sameperson.movies;

import com.google.gson.Gson;
import com.sameperson.movies.dao.MovieDao;
import com.sameperson.movies.dao.Sql2oMovieDao;
import com.sameperson.movies.model.Movie;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        String datasource = "jdbc:h2:~/movieReviews.db";
        if(args.length > 0) {
            if(args.length != 2) {
                System.err.println("API needs to have 2 arguments (port, datasource)");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }
        Sql2o sql2o = new Sql2o(String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource), "", "");

        MovieDao movieDao = new Sql2oMovieDao(sql2o);
        Gson gson = new Gson();

        port(9090);

        post("/movies", "application/json", (req, res) -> {
            Movie movie = gson.fromJson(req.body(), Movie.class);
            movieDao.add(movie);
            res.status(201);
            return movie;
        }, gson::toJson);

        get("/movies", "application/json",(req, res) -> movieDao.findAll(), gson::toJson);

        get("/movies/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Movie movie = movieDao.findById(id);
            return movie;
        }, gson::toJson);

        after((req, res) -> res.type("application/json"));

    }

}
