package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Project;

public class ProjectDAO {
	
	private Database db;
	
	ProjectDAO(Database db) {
		this.setDb(db);
	}

	/**
	 * Get a project from the database
	 * @param projectID
	 * @return project
	 */
	public Project getProject(int projectID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Project project = null;
		try {
			String sql = "SELECT * from projects WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  projectID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				project = new Project(rs.getString("title"), rs.getInt("recordsperimage"), rs.getInt("firstycoord"), 
								rs.getInt("recordheight"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println("Could not get project");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Could not close prepared statement resultset");
				e.printStackTrace();
			}
		}
		return project;
	}
	
	/**
	 * Get all projects from the database
	 * @return arraylist of projects
	 */
	public ArrayList<Project> getAllProjects(){
		ArrayList<Project> allProjects = new ArrayList<Project>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from projects";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Project project = new Project(rs.getString("title"), rs.getInt("recordsperimage"), rs.getInt("firstycoord"), 
						rs.getInt("recordheight"), rs.getInt("id"));
				allProjects.add(project);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not get all projects");
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not close prepared statement or return set");
				e.printStackTrace();
			}
			
		}
		return allProjects;
	}
	
	/**
	 * Add a project to the database
	 * @param project
	 * @return project's new id
	 */
	public int add(Project project){
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO projects" +
					"(title, recordsperimage, firstycoord, recordheight)" +
					"VALUES(?,?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  project.getTitle());
			pstmt.setInt(2,  project.getRecordsperimage());
			pstmt.setInt(3, project.getFirstycoord());
			pstmt.setInt(4, project.getRecordheight());
			
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				project.setId(id);
			}
			else{
				System.out.println("Could not add project");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to close prepared statement or return set");
				e.printStackTrace();
			}
		}
		return id;
	}
	
	/**
	 * Update a project's info
	 * @param project
	 */
	public void update(Project project){
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE projects " + "SET title = ?, recordsperimage = ?, firstycoord = ?, recordheight = ?" + "WHERE id = ?";
			
			pstmt = db.getConnection().prepareStatement(sql);
			
			pstmt.setString(1,  project.getTitle());
			pstmt.setInt(2,  project.getRecordsperimage());
			pstmt.setInt(3, project.getFirstycoord());
			pstmt.setInt(4,  project.getRecordheight());
			pstmt.setInt(5,  project.getId());
			
			if(pstmt.executeUpdate() == 1){
				System.out.println("Project updated successfuly");
			}
			else{
				//error
				System.out.println("Could not update Project");
			}
		}
		catch(SQLException e){
			System.out.println("Could not update Project");
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
			} 
			catch(SQLException e){
				System.out.println("Could not close prepared statement");
				e.printStackTrace();
			}
		}
	}
	
	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}
	
}
