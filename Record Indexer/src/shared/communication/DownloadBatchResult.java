package shared.communication;

import java.util.ArrayList;

import shared.model.Image;
import shared.model.Project;
import shared.model.Field;

public class DownloadBatchResult {

	private Project project;
	private Image image;
	private ArrayList<Field> projectFields;
	
	public DownloadBatchResult(Project project, Image image, ArrayList<Field> projectFields) {
		super();
		this.project = project;
		this.image = image;
		this.projectFields = projectFields;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public ArrayList<Field> getProjectFields() {
		return projectFields;
	}

	public void setProjectFields(ArrayList<Field> projectFields) {
		this.projectFields = projectFields;
	}
	
	public boolean equals(DownloadBatchResult output){
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

	public String toString(){
		String result = image.getId() + "\n" + project.getId() + "\n" + image.getFile() + "\n" + project.getFirstycoord() + 
				"\n" + project.getRecordheight() + "\n" + project.getRecordsperimage() + "\n" + projectFields.size() + "\n";
		for(Field field: projectFields){
			int count = 1;
			String part = field.getId() + "\n" + count + "\n" + field.getTitle() + "\n" + field.getHelphtml() + "\n" + 
					field.getXcoord() + "\n" + field.getWidth() + "\n";
			if(field.getKnowndata() != null && !field.getKnowndata().equals("")){
				part += field.getKnowndata() + "\n";
			}
			result += part;
			count++;
		}
		
		return result;
	}
	
	
	
}
