package Users;

import java.util.Scanner;

public class User_login {

	public static boolean login(User_registration user) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("---------------------");
		System.out.println("Login: ");
		
		System.out.println("Enter your email: ");
		String email = scanner.nextLine();
		
		System.out.println("Enter your password: ");
		String password = scanner.nextLine();
		
		scanner.close();
		
		return email.equals(user.getEmail()) && password.equals(user.getPassword());
		
		
	}
	
}