package com.sameperson.movies.dao;

import com.sameperson.movies.exc.DaoException;
import com.sameperson.movies.model.Review;

import java.util.List;

public interface ReviewDao {
    void add(Review review) throws DaoException;
    List<Review> findAll();
    Review findByMovieId(int movieId);
}
