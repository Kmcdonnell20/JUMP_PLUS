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

import Users.User_registration;

import connection.ConnectionManager;

public class UserDaoSql implements UserDao {

    private Connection conn;

    public UserDaoSql() throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
        conn = ConnectionManager.getConnection();
    }

    public User_registration createUser(String email, String password) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO user (email, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, email);
            stmt.setString(2, password);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return new User_registration(id, email, password);
            } else {
                return null;
            }
        } finally {
            ConnectionManager.closeConnection(conn, stmt);
        }
    }

    @Override
    public User_registration login(String email, String password) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT userId, email, password FROM user WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("userId");
                return new User_registration(id, email, password);
            } else {
                return null;
            }
        } finally {
            ConnectionManager.closeConnection(conn, stmt);
        }
    }

	

	@Override
public Optional<User_registration> validateUser(String email, String password) throws SQLException {
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            User_registration user = new User_registration(id, email, password);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    } catch (SQLException e) {
        // Handle the exception or rethrow it
        throw e;
    }
}

@Override
public boolean addMovieRating(User_registration user, int movieId, double rating) throws SQLException {
    PreparedStatement stmt = conn.prepareStatement("INSERT INTO ratings (userId, movieId, rating) VALUES (?, ?, ?)");
    stmt.setInt(1, user.getUserId());
    stmt.setInt(2, movieId);
    stmt.setDouble(3, rating);

    int affectedRows = stmt.executeUpdate();
    return affectedRows > 0;
}

@Override
public List<Movie> getRatedMovies(User_registration user) throws SQLException {
    PreparedStatement stmt = conn.prepareStatement("SELECT m.id, m.title, r.rating FROM movies m INNER JOIN ratings r ON m.id = r.movie_id WHERE r.user_id = ?");
    stmt.setInt(1, user.getUserId());

    ResultSet rs = stmt.executeQuery();
    List<Movie> movies = new ArrayList<>();
    while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        double rating = rs.getDouble("rating");
        Movie movie = new Movie(id, title, rating);
        movies.add(movie);
    }
    return movies;
}


}
