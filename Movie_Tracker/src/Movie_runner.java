import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Users.User_registration;
import dao.Movie;
import dao.MovieDao;
import dao.MovieDaoSql;
import dao.UserDao;
import dao.UserDaoSql;

public class Movie_runner {

    private static MovieDao movieDao;
    private static UserDao userDao;
    private static List<Movie> movies;
    private static User_registration userFound;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Movie Tracker!");

        userDao = new UserDaoSql();
        User_registration user = new User_registration(0, null, null);

        boolean isLoggedIn = false; // Add a variable to indicate whether the user is logged in or not

        while (!isLoggedIn) { // Loop until the user logs in or creates a new user
            System.out.println("Do you want to create a new user? (Y/N)");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Enter a email: ");
                String email = sc.nextLine();

                System.out.println("Enter a password: ");
                String password = sc.nextLine();

                user = userDao.createUser(email, password);
                if (user != null) {
                    System.out.println("User created successfully.");
                } else {
                    System.out.println("Failed to create user.");
                    return;
                }
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Enter your email: ");
                String username = sc.nextLine();

                System.out.println("Enter your password: ");
                String password = sc.nextLine();

                user = userDao.login(username, password);
                if (user != null) {
                    System.out.println("Login successful. Welcome " + user.getEmail() + "!");
                    isLoggedIn = true; // Set the variable to true when the user logs in
                } else {
                    System.out.println("Invalid email or password.");
                    return;
                }
            } else {
                System.out.println("Invalid choice.");
                return;
            }
        }

        menu(user);
    }

    public static void menu(User_registration user) {
        movieDao = new MovieDaoSql();

        try {
            movieDao.setConnection();

            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("\nWelcome to your Movie Tracker!\n");

                System.out.println();
                System.out.println("===========================================================================================");
                System.out.println("Please select an option :");
                System.out.println("1) View all movies");
                System.out.println("2) Rate a movie");
                System.out.println("3) Exit/Log out");
                System.out.println("===========================================================================================");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        List<Movie> movies = movieDao.getAllMovies();
                        for (Movie movie : movies) {
                            System.out.println(movie);
                        }
                        break;
                    case 2:
                        System.out.println("Enter the movie ID: ");
                        int movieId = sc.nextInt();
                        System.out.println("Enter your rating (out of 10): ");
                        double rating = sc.nextDouble();
                        rateMovie(movieId, rating);
                        break;
                    case 3:
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid input. Please enter a valid option");
                        break;
                }
            }
        } catch (ClassNotFoundException | IOException | SQLException e) {
            System.out.println("Could not find any movies.");
            e.printStackTrace();
        }
    }


    public static void rateMovie(int movieID, double rating) {
		try {
			Movie movie = movieDao.getMovieById(movieID);
			if (movie != null) {
				movie.addRating(rating);
				movieDao.updateMovie(movie);
				System.out.println("Rating updated successfully.");
			} else {
				System.out.println("Could not find movie with ID " + movieID);
			}
		} catch (Exception e) {
			System.out.println("Error updating rating.");
			e.printStackTrace();
		}
	}
}
	