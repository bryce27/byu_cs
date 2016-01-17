package shared.communication;

public class GetSampleImageParams {

	private String username;
	private String password;
	private int projectID;
	
	public GetSampleImageParams(String username, String password, int projectID) {
		super();
		this.username = username;
		this.password = password;
		this.projectID = projectID;
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

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	@Override
	public String toString() {
		return "GetSampleImageParams [username=" + username + ", password=" + password + ", projectID=" + projectID
				+ "]";
	}
	
}
