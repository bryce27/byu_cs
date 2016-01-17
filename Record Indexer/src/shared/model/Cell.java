package shared.model;

public class Cell {
	
	private String content;
	private int record_id;
	private int field_id;
	
	public Cell(String content, int record_id, int field_id) {
		this.content = content;
		this.record_id = record_id;
		this.field_id = field_id;
	}
	
	public Cell(){}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRecord_id() {
		return record_id;
	}

	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	public int getField_id() {
		return field_id;
	}

	public void setField_id(int field_id) {
		this.field_id = field_id;
	}

	public String toString() {
		return "Cell [content=" + content + ", record_id="
				+ record_id + ", field_id=" + field_id + "]";
	}
	
	public boolean equals(Cell cell){
		if((this == null && cell != null)|| (cell == null && this != null)){
			return false;
		}
		if(this.toString().equals(cell.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
