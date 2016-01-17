package shared.communication;

import java.util.ArrayList;

import shared.model.Project;

public class GetProjectsResult {

	private boolean approved;
	private ArrayList<Project> projects;
	
	public GetProjectsResult(boolean approved, ArrayList<Project> projects) {
		this.approved = approved;
		this.projects = projects;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	public String toString(){
		String result = "";
		for(Project project: projects){
			result += (project.getId() + "\n" + project.getTitle() + "\n");
		}
		return result;
	}
	
	public boolean equals(GetProjectsResult output){
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
