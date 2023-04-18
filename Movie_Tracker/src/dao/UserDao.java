package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import Users.User_registration;

public interface UserDao {

    public Optional<User_registration> validateUser(String email, String password) throws SQLException;

	public boolean addMovieRating(User_registration user, int movieId, double rating) throws SQLException;


    public User_registration createUser(String email, String password) throws Exception;

    public User_registration login(String email, String password) throws SQLException;

	public List<Movie> getRatedMovies(User_registration user) throws SQLException;



}

