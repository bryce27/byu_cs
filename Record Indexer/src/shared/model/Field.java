package shared.model;

public class Field {
	private String title;
	private int columnnumber;
	private int xcoord;
	private int width;
	private String helphtml;
	private String knowndata;
	private int project_id;
	private int id;
	
	public Field(String title, int columnnumber, int xcoord, int width, String helphtml, String knowndata, int project_id, int id) {
		this.title = title;
		this.columnnumber = columnnumber;
		this.xcoord = xcoord;
		this.width = width;
		this.helphtml = helphtml;
		this.knowndata = knowndata;
		this.project_id = project_id;
		this.id = id;
	}
	
	public Field(){}

	public String getTitle() {
		return title;
	}
	
	public void addUrl(String host, String port){
		String url = "http://" + host + ":" + port + "//";
		helphtml = url + helphtml;
		if(knowndata != null && !knowndata.equals("")){
			knowndata = url + knowndata;
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getColumnnumber() {
		return columnnumber;
	}

	public void setColumnnumber(int columnnumber) {
		this.columnnumber = columnnumber;
	}

	public int getXcoord() {
		return xcoord;
	}

	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getHelphtml() {
		return helphtml;
	}

	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}

	public String getKnowndata() {
		return knowndata;
	}

	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}

	public int getProjectID() {
		return project_id;
	}

	public void setProjectID(int project_id) {
		this.project_id = project_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Field [title=" + title + ", columnnumber=" + columnnumber + ", xcoord=" + xcoord + ", width=" + width
				+ ", helphtml=" + helphtml + ", knowndata=" + knowndata + ", id=" + id + "]";
	}
	
	public boolean equals(Field field){
		if(this == null || field == null){
			return false;
		}
		if(this.toString().equals(field.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
