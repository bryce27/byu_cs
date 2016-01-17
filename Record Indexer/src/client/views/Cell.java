package client.views;

public class Cell {
	
	private int record;
	private int field;
	
	public Cell(int row, int col){ //record is row num, field is col
		record = row;
		field = col;
	}
	
	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}
	
	public String toString(){
		String result = "row: " + this.record + " col: " +  this.field;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + field;
		result = prime * result + record;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (field != other.field)
			return false;
		if (record != other.record)
			return false;
		return true;
	}

	
	
}