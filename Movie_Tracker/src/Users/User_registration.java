package Users;

import java.util.Scanner;
import java.util.regex.Pattern;

public class User_registration {
    private int userId;
    private String email;
    private String password;
    
    public User_registration(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
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


	@Override
	public String toString() {
		return "{" +
			" userId='" + getUserId() + "'" +
			", email='" + getEmail() + "'" +
			", password='" + getPassword() + "'" +
			"}";
	}

}

