package Users;

import java.util.Scanner;
import java.util.regex.Pattern;

public class User_registration {

	private String email;
	private String password;
	
	
	public User_registration(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
public static User_registration createUser() {
		
		Scanner info = new Scanner(System.in);
        
        System.out.println("-----------------------------------");
        System.out.println("Creating User Profile");
        
        System.out.println("Enter your email: ");
        String email= info.nextLine();
       
        System.out.println("Enter your password: ");
        String password = info.nextLine();
        
        System.out.println("Confirm your password: ");
        String confirmpassword = info.nextLine();
        
        while(!confirmpassword.equals(password)) {
        	System.out.println("Passwords do not match. Please confirm password ");
        	System.out.println("Enter your password: ");
        	confirmpassword = info.nextLine();
        }
        
        // Regex to validate the email format
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format. Please try again.");
            return createUser();
        }
		
        System.out.println("User Profile Created");
        return new User_registration(email, confirmpassword);
	}

	
	
		
}
