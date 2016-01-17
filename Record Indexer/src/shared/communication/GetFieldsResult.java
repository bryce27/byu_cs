package shared.communication;

import java.util.ArrayList;
import shared.model.Field;

public class GetFieldsResult {
	
	private int projectID;
	private ArrayList<Field> fields;
	
	public GetFieldsResult(int projectID, ArrayList<Field> fields) {
		this.projectID = projectID;
		this.fields = fields;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	
	public String toString(){
		String result = "";
		for(Field field : fields){
			result += (field.getProjectID() + "\n" + field.getId() + "\n" + field.getTitle() + "\n");
		}
		return result;
	}
	
	
}
