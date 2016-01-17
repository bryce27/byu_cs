package shared.model;

public class User {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int currentbatch_id;
	private int indexedrecords;
	private int id;
	
	public User(String username, String password, String firstname, String lastname, 
			String email, int currentbatch_id, int indexedrecords, int id){
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.currentbatch_id = currentbatch_id;
		this.indexedrecords = indexedrecords;
		this.id = id;
	}
	
	public User(){}
	
	public void incrementRecordsIndexed(int number){
		indexedrecords += number;
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIndexedrecords() {
		return indexedrecords;
	}
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}
	public int getCurrentbatch_id() {
		return currentbatch_id;
	}
	public void setCurrentbatch_id(int currentbatch_id) {
		this.currentbatch_id = currentbatch_id;
	}

	public int getUserID() {
		return id;
	}

	public void setUserID(int userID) {
		this.id = userID;
	}
	
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", currentbatch_id=" + currentbatch_id + ", indexedrecords="
				+ indexedrecords + ", id=" + id + "]";
	}

	public boolean equals(User user){
		if((this == null && user != null)|| (user == null && this != null)){
			return false;
		}
		if(this.toString().equals(user.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
