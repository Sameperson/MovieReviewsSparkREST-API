package com.sameperson.movies.dao;

import com.sameperson.movies.exc.DaoException;
import com.sameperson.movies.model.Movie;

import java.util.List;

public interface MovieDao {
    void add(Movie movie) throws DaoException;
    List<Movie> findAll();
    Movie findById(int id);
}
