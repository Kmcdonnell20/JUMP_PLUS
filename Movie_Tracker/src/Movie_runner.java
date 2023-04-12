import Users.*;

public class Movie_runner {

	public static void main(String[] args) {
		
		User_registration user = User_registration.createUser();
		boolean isAuthorized = false;
		
		while(!isAuthorized)
		if(User_login.login(user)) {
			System.out.println("Login Successful!");
			isAuthorized = true;
		}else {
			System.out.println("Login FAILED, please try again");
		}
		
	}

}
