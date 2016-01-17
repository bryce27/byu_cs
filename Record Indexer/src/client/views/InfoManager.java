package client.views;

public class InfoManager {
	private String username;
	private String password;
	private int imageID;
	
	private static InfoManager infoManager = new InfoManager();
	
	/**
	 * a class to store user's information
	 */

	public InfoManager() {
	
	}
	
	public static InfoManager getInfo(){
		return infoManager;
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

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	
}