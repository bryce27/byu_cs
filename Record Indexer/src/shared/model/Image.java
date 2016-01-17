package shared.model;

public class Image {
	private String file;
	private boolean isAvailable;
	private int project_id;
	int id;
	
	public Image(String file, boolean isAvailable, int project_id, int id) {
		this.file = file;
		this.isAvailable = isAvailable;
		this.project_id = project_id;
		this.id = id;
	}
	
	public Image(){}
	
	public void addUrl(String host, String port){
		String url = "http://" + host + ":" + port + "//"; //toy with changing these last //'s 
		file = url + file;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Image [file=" + file + ", isAvailable=" + isAvailable + ", project_id=" + project_id + ", id=" + id
				+ "]";
	}
	
	public boolean equals(Image image){
		if((this == null && image != null)|| (image == null && this != null)){
			return false;
		}
		if(this.toString().equals(image.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
