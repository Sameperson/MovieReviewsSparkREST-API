package com.sameperson.movies;

import com.google.gson.Gson;
import com.sameperson.movies.dao.MovieDao;
import com.sameperson.movies.dao.Sql2oMovieDao;
import com.sameperson.movies.model.Movie;
import com.sameperson.testing.ApiClient;
import com.sameperson.testing.ApiResponse;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AppTest {

    public static final String PORT = "9091";
    public static final String DATASOURCE = "jdbc:h2:mem:tests";

    private Connection connection;
    private ApiClient client;
    private Gson gson;
    private Sql2oMovieDao movieDao;

    @BeforeClass
    public static void b4() {
        String[] args = {PORT, DATASOURCE};
        App.main(args);
    }

    @AfterClass
    public static void onFinish() {
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        movieDao = new Sql2oMovieDao(sql2o);
        connection = sql2o.open();
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void addingMovies_ReturnsCreatedStatus() throws Exception {
        Map<String, String> movieData = new HashMap<>();
        movieData.put("name", "Titanic");
        movieData.put("url", "http://www.imdb.com/title/tt0120338/");
        ApiResponse apiResponse = client.request("POST", "/movies", gson.toJson(movieData));
        assertEquals(201, apiResponse.getStatus());

    }

    @Test
    public void movies_CanBeAccessedById() throws Exception {
        Movie movie = getNewMovie();
        movieDao.add(movie);
        ApiResponse apiResponse = client.request("GET", "/movies/" + movie.getId());
        Movie retrieved = gson.fromJson(apiResponse.getBody(), Movie.class);
        assertEquals(movie, retrieved);
    }

    private Movie getNewMovie() {
        return new Movie("Movie", "http://movie.com");
    }
}