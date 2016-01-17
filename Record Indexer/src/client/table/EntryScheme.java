package client.table;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Cell;

// this used to be Rvalue so you will have to change things to match your database

public class EntryScheme {
	private ArrayList<Field> fields;
	private ArrayList<String> values;
	private int columns;
	private int row;
	
	/**
	 * basically creates a class that represents a row in the batch 
	 * @param current number of rows in image
	 * @param current number of fields in image
	 */
	
	public EntryScheme(int currRow, ArrayList<Field> currFields){
		row = currRow;
		fields = currFields;
		values = new ArrayList<String>();
		values.add(String.valueOf(currRow));
		
		columns = currFields.size() + 1;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	
	
}