package com.sameperson.movies;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import spark.Spark;

import static org.junit.Assert.*;

public class AppTest {

    public static final String PORT = "9191";
    public static final String DATASOURCE = "jdbc:h2:mem:tests";

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

    }

    @After
    public void tearDown() throws Exception {

    }

}