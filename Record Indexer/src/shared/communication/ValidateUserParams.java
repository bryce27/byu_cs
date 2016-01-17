package shared.communication;

public class ValidateUserParams {
	private String username;
	private String password;
	
	public ValidateUserParams(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "ValidateUserParams [username=" + username + ", password=" + password + "]";
	}
	
}
