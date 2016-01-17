package shared.model;

public class Record {
	private int rownumber;
	private int image_id;
	private int id;
	
	public Record(int rownumber, int image_id, int id) {
		super();
		this.rownumber = rownumber;
		this.image_id = image_id;
		this.id = id;
	}
	
	public Record(){}

	public int getRownumber() {
		return rownumber;
	}

	public void setRownumber(int rownumber) {
		this.rownumber = rownumber;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Record [rownumber=" + rownumber + ", image_id=" + image_id + ", id=" + id + "]";
	}
	
	public boolean equals(Record record){
		if(this == null || record == null){
			return false;
		}
		if(this.toString().equals(record.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
