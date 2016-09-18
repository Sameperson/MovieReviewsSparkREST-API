package com.sameperson.movies.dao;

import com.sameperson.movies.exc.DaoException;
import com.sameperson.movies.model.Movie;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oMovieDao implements MovieDao {

    private final Sql2o sql2o;

    public Sql2oMovieDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void add(Movie movie) throws DaoException {
        String sql = "INSERT INTO movies(name, url) VALUES (:name, :url)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(movie)
                    .executeUpdate()
                    .getKey();
            movie.setId(id);
        } catch (Sql2oException e) {
            throw new DaoException(e, "Error occurred while adding the movie");
        }
    }

    public List<Movie> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM movies")
                    .executeAndFetch(Movie.class);
        }
    }

    @Override
    public Movie findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM movies WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Movie.class);
        }
    }
}
