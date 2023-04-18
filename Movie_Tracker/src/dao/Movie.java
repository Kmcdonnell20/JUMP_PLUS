package dao;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private Integer id;
    private String title;
    private double rating;
    private List<Double> ratings;

    public Movie(Integer id, String title, double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.ratings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", rating='" + rating + "'" +
                "}";
    }

    public void addRating(double rating) {
        if (this.ratings == null) {
            this.ratings = new ArrayList<>();
        }
        this.ratings.add(rating);
        double sum = this.ratings.stream().mapToDouble(Double::doubleValue).sum();
        double newRating = sum / this.ratings.size();
        this.setRating(newRating);
        // System.out.println("list: " + newRating);
    }
    

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        } else {
            double sum = 0.0;
            for (Double rating : ratings) {
                sum += rating;
            }
            return sum / ratings.size();
        }
    }

    public boolean isPresent() {
        return (id != null);
    }

    public Movie get() {
        return this;
    }
}
