package com.sameperson.movies.model;

public class Review {

    private int id;
    private int movieId;
    private int rating;
    private String comment;

    public Review(int courseId, int rating, String comment) {
        this.movieId = courseId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        if (movieId != review.movieId) return false;
        if (rating != review.rating) return false;
        return comment != null ? comment.equals(review.comment) : review.comment == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + movieId;
        result = 31 * result + rating;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
