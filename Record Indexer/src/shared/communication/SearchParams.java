package shared.communication;

import java.util.ArrayList;

public class SearchParams {

	private String username;
	private String password;
	private ArrayList<Integer> fieldIDs;
	private ArrayList<String> searchValues;
	
	public SearchParams(String username, String password, ArrayList<Integer> fieldIDs, ArrayList<String> searchValues) {
		this.username = username;
		this.password = password;
		this.fieldIDs = fieldIDs;
		this.searchValues = searchValues;
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

	public ArrayList<Integer> getFieldIDs() {
		return fieldIDs;
	}

	public void setFieldIDs(ArrayList<Integer> fieldIDs) {
		this.fieldIDs = fieldIDs;
	}

	public ArrayList<String> getSearchValues() {
		return searchValues;
	}

	public void setSearchValues(ArrayList<String> searchValues) {
		this.searchValues = searchValues;
	}
	
	public String toString(){
		String fields = "";
		for(int id : fieldIDs){
			fields += id + "\n";
		}
		String values = "";
		for(String input : searchValues){
			values += input + "\n";
		}
		String result = username + "\n" + password + "\n" + fields + "\n" + values + "\n";
		return result;
	}
	
	
}
