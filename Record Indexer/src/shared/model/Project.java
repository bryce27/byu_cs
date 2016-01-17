package shared.model;

public class Project {
	private String title;
	private int recordsperimage;
	private int firstycoord;
	private int recordheight;
	private int id;
	
	public Project(String title, int recordsperimage, int firstycoord, int recordheight, int id) {
		this.title = title;
		this.recordsperimage = recordsperimage;
		this.firstycoord = firstycoord;
		this.recordheight = recordheight;
		this.id = id;
	}
	
	public Project(){}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRecordsperimage() {
		return recordsperimage;
	}

	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}

	public int getFirstycoord() {
		return firstycoord;
	}

	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}

	public int getRecordheight() {
		return recordheight;
	}

	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Project [title=" + title + ", recordsperimage=" + recordsperimage + ", firstycoord=" + firstycoord
				+ ", recordheight=" + recordheight + ", id=" + id + "]";
	}
	
	public boolean equals(Project project){
		if((this == null && project != null) || (this != null && project == null)){
			return false;
		}
		if(this.toString().equals(project.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	
}
