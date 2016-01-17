package shared.communication;

import java.util.ArrayList;

public class SubmitBatchParams {

	private String username;
	private String password;
	private int imageID;
	private ArrayList<String> recordValues;
	
	public SubmitBatchParams(String username, String password, int imageID, ArrayList<String> recordValues) {
		super();
		this.username = username;
		this.password = password;
		this.imageID = imageID;
		this.recordValues = recordValues;
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

	public ArrayList<String> getRecordValues() {
		return recordValues;
	}

	public void setRecordValues(ArrayList<String> recordValues) {
		this.recordValues = recordValues;
	}
	
	public String toString(){
		String values = "";
		for(String value : recordValues){
			values += value + "\n";
		}
		String result = username + "\n" + password + "\n" + imageID + "\n" + values + "\n";
		return result;
	}
	
}
