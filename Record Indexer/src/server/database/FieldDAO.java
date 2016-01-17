package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Field;

public class FieldDAO {
	
	private Database db;
	
	FieldDAO(Database db) {
		this.setDb(db);
	}

	/**
	 * Get a field from the database
	 * @param fieldID
	 * @return field
	 */
	public Field getField(int fieldID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Field field = null;
		try {
			String sql = "Select * from fields WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  fieldID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				field = new Field(rs.getString("title"), rs.getInt("columnnumber"), rs.getInt("xcoord"), rs.getInt("width"), rs.getString("helphtml"), 
						rs.getString("knowndata"), rs.getInt("project_id"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get field, SQL exception");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Couldn't close prepared statement/resultset!?");
				e.printStackTrace();
			}
		}
		return field;
	}
	
	/**
	 * Get a list of fields that belong to a specific project
	 * @param projectID
	 * @return arraylist of fields in the project given
	 */
	public ArrayList<Field> getFieldsByProjectID(int projectID){
		ArrayList<Field> fieldsofProject = new ArrayList<Field>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Field field = null;
		try {
			String sql = "Select * from fields WHERE project_id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  projectID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				field = new Field(rs.getString("title"), rs.getInt("columnnumber"), rs.getInt("xcoord"), rs.getInt("width"), rs.getString("helphtml"), 
						rs.getString("knowndata"), rs.getInt("project_id"), rs.getInt("id"));
				fieldsofProject.add(field);
			}
		} catch (SQLException e) {
			System.out.println("Could not get fields of project, SQL exception");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Could not close prepared statement / resultset");
				e.printStackTrace();
			}
		}
		return fieldsofProject;
	}
	
	/**
	 * Get all fields from the database
	 * @return arraylist of fields
	 */
	public ArrayList<Field> getAllFields(){
		ArrayList<Field> allFields = new ArrayList<Field>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from fields";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Field field = new Field(rs.getString("title"), rs.getInt("columnnumber"), rs.getInt("xcoord"), rs.getInt("width"), rs.getString("helphtml"), 
						rs.getString("knowndata"), rs.getInt("project_id"), rs.getInt("id"));
				allFields.add(field);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not get all, sql connection fail?");
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return allFields;
	}
	
	/**
	 * Add a field to the database
	 * @param field
	 * @return field's new id
	 */
	public int add(Field field){
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO fields" +
					"(title, columnnumber, xcoord, width, helphtml, knowndata, project_id)" +
					"VALUES(?,?,?,?,?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  field.getTitle());
			pstmt.setInt(2,  field.getColumnnumber());
			pstmt.setInt(3, field.getXcoord());
			pstmt.setInt(4, field.getWidth());
			pstmt.setString(5, field.getHelphtml());
			pstmt.setString(6, field.getKnowndata());
			pstmt.setInt(7, field.getProjectID());
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				field.setId(id);
			}
			else{
				System.out.println("Could not add field");
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
				e.printStackTrace();
			}
		}
		return id;
	}
	
	/**
	 * Update a field's info
	 * @param field
	 */
	public void update(Field field){

		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE fields " + 
							"SET title = ?, columnnumber = ?, xcoord = ?, width = ?, " +
							"helphtml = ?, knowndata = ?, project_id = ?" +
							"WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1,  field.getTitle());
			pstmt.setInt(2,  field.getColumnnumber());
			pstmt.setInt(3, field.getXcoord());
			pstmt.setInt(4,  field.getWidth());
			pstmt.setString(5,  field.getHelphtml());
			pstmt.setString(6,  field.getKnowndata());
			pstmt.setInt(7,  field.getProjectID());
			pstmt.setInt(8,  field.getId());
			if(pstmt.executeUpdate() == 1){
				System.out.println("Updated field successfuly");
			}
			else{
				//error
				System.out.println("Could not update field");
			}
		}
		catch(SQLException e){
			System.out.println("Could not update field");
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
