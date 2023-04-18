package dao;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MovieDao {
	// DAO Interface --> list out and outline the functionality that we want with our data
		
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

	public List<Movie> getAllMovies();
	
	public Movie getMovieById(int id);

	public void rateMovie(int movieId, double rating) throws SQLException;


	public void updateMovieRating(int id, double rating);

    public boolean updateMovie(Movie ratedMovie);

}
