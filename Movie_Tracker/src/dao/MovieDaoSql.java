package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import connection.ConnectionManager;

public class MovieDaoSql implements MovieDao{
	
	// Connection used for all methods
	private Connection conn;

	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = connection.ConnectionManager.getConnection();
	}

	@Override
	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<>();
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery("Select * from movie");){
			while(rs.next()) {
				int id = rs.getInt("movieId");
				String title = rs.getString("title");
				double rating = rs.getDouble("rating");

				
				Movie movie = new Movie(id, title, rating);
				
				// adding the movie into the movie list
				movies.add(movie);
			}
			
			
		} catch(SQLException e) {
			// uncomment of you're running into issues and want to know what's
			// going on
//			e.printStackTrace();
		}
		return movies;
	}

	@Override
public Movie getMovieById(int id) {
    try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movie WHERE movieId = ?")) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        
        // Check if the ResultSet has any rows
        if (rs.next()) {
            int movieId = rs.getInt("movieId");
            String title = rs.getString("title");
            double rating = rs.getDouble("rating");
            
            // Create a new Movie object with the retrieved data
            Movie movie = new Movie(movieId, title, rating);
            
            // Return the Movie object
            return movie;
        } else {
            // If there are no rows in the ResultSet, return null
            return null;
        }
    } catch (SQLException e) {
        // If an exception occurs, print the stack trace and return null
        e.printStackTrace();
        return null;
    }
}

	@Override
	public void rateMovie(int movieId, double rating) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Movie WHERE movieId = ?")) {
			pstmt.setInt(1, movieId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Movie movie = new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getDouble("rating"));
					movie.addRating(rating);
					updateMovie(movie);
				}
			}
		}
	}
	

	@Override
	public void updateMovieRating(int id, double rating) {
    try {
        // Get the movie object from the database
        Movie movie = getMovieById(id);
        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        // Add the new rating to the movie object and calculate the new average rating
        movie.addRating(rating);
        double averageRating = movie.getAverageRating();
		System.out.println("Average rating: " + averageRating);

        // Update the movie rating in the database
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE movie SET rating = ? WHERE movieId = ?")) {
            pstmt.setDouble(1, averageRating);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Movie rating updated successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

	@Override
	public boolean updateMovie(Movie movie) {
		try {
			// Get the movie object from the database
			Movie existingMovie = getMovieById(movie.getId());
			if (existingMovie == null) {
				System.out.println("Movie not found.");
				return false;
			}

			// Add the new rating to the existing movie object and calculate the new average rating
			existingMovie.addRating(movie.getRating());
			double averageRating = existingMovie.getAverageRating();

			// Update the movie title and rating in the database
			try (PreparedStatement pstmt = conn.prepareStatement("UPDATE Movie SET title=?, rating=? WHERE movieId=?")) {
				pstmt.setString(1, movie.getTitle());
				pstmt.setDouble(2, averageRating);
				pstmt.setInt(3, movie.getId());
				int rowsUpdated = pstmt.executeUpdate();
				return rowsUpdated > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



}
