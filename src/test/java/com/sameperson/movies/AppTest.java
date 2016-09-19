package com.sameperson.movies;

import com.google.gson.Gson;
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

}