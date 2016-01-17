package shared.communication;

import java.util.ArrayList;

import shared.model.Cell;
import shared.model.Image;
import shared.model.Record;


public class SearchResult {

	private ArrayList<Image> images;
	private ArrayList<Cell> cells;
	private ArrayList<Record> records;
	
	public SearchResult(ArrayList<Image> images, ArrayList<Cell> cells, ArrayList<Record> records) {
		this.images = images;
		this.cells = cells;
		this.records = records;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
	
	public String toString(){
		String result = "";
		for(int i = 0; i < images.size(); i++){
			Image image = images.get(i);
			Cell cell = cells.get(i);
			Record record = records.get(i);
			int rowNumber = record.getRownumber();
			
			String part = image.getId() + "\n" + image.getFile() + "\n" + rowNumber + "\n" + cell.getField_id() + "\n";
			result += part;
		}
		System.out.println(result);
		return result;
	}
	
	public boolean equals(SearchResult output){
		if(this == null || output == null){
			return (this == null && output == null);
		}
		if(this.toString().equals(output.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
