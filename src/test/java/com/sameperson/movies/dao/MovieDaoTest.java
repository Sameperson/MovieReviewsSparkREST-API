package com.sameperson.movies.dao;

import com.sameperson.movies.exc.DaoException;
import com.sameperson.movies.model.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class MovieDaoTest {
    private Sql2oMovieDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new Sql2oMovieDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingMovieSetsId() throws DaoException {
        Movie movie = getNewMovie();
        int originalMovieId = movie.getId();
        dao.add(movie);
        assertNotEquals(originalMovieId, movie.getId());
    }

    @Test
    public void moviesAreReturnedFromAddAll() throws Exception {
        Movie movie = getNewMovie();
        dao.add(movie);
        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void noMoviesReturnsEmptyList() throws Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void existingMoviesCanBeFoundById() throws Exception {
        Movie movie = getNewMovie();
        dao.add(movie);
        Movie foundMovie = dao.findById(movie.getId());
        assertEquals(movie, foundMovie);
    }

    private Movie getNewMovie() {
        return new Movie("Movie", "http://movie.com");
    }
}