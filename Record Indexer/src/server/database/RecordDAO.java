package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Record;

public class RecordDAO {
	
	private Database db;
	
	RecordDAO(Database db) {
		this.setDb(db);
	}

	/**
	 * Get a record from the database
	 * @param recordID
	 * @return record
	 */
	public Record getRecord(int recordID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Record record = null;
		try {
			String sql = "Select * from records WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  recordID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				record = new Record(rs.getInt("rownumber"), rs.getInt("image_id"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get record, SQL exception");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Couldn't close prepared statement or resultset.");
				e.printStackTrace();
			}
		}
		return record;
	}
	
	public int getImage_idByRecord_id(int recordID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int imageID = 0;
		try {
			String sql = "Select image_id from records WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  recordID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				imageID = rs.getInt("image_id");
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get record, SQL exception");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Couldn't close prepared statement or resultset.");
				e.printStackTrace();
			}
		}
		return imageID;
	}
	
	/**
	 * Get a list of records that belong to a specific image
	 * @param imageID
	 * @return arraylist of records in the image given	
	 */
	public ArrayList<Record> getRecordsByImageID(int imageID){
		ArrayList<Record> recordsofImage = new ArrayList<Record>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Record record = null;
		try {
			String sql = "Select * from records WHERE image_id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  imageID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				record = new Record(rs.getInt("rownumber"), rs.getInt("image_id"), rs.getInt("id"));
				recordsofImage.add(record);
			}
		} catch (SQLException e) {
			System.out.println("Could not get records of image, SQL exception");
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
		return recordsofImage;
	}
	
	/**
	 * Get all records from the database
	 * @return arraylist of records
	 */
	public ArrayList<Record> getAllRecords(){
		ArrayList<Record> allRecords = new ArrayList<Record>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from records";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Record record = new Record(rs.getInt("rownumber"), rs.getInt("image_id"), rs.getInt("id"));
				allRecords.add(record);
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
		return allRecords;
	}
	
	/**
	 * Add a record to the database
	 * @param record
	 * @return record's new id
	 */
	public int add(Record record){
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO records " +
					"(rownumber, image_id)" +
					"VALUES(?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,  record.getRownumber());
			pstmt.setInt(2,  record.getImage_id());
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				record.setId(id);
			}
			else{
				System.out.println("Could not add record");
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
	 * Update a record's info
	 * @param record
	 */
	public void update(Record record){
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE records " + 
							"SET rownumber = ?, image_id = ? " +
							"WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  record.getRownumber());
			pstmt.setInt(2,  record.getImage_id());
			pstmt.setInt(3, record.getId());
			if(pstmt.executeUpdate() == 1){
				System.out.println("Updated record successfuly");
			}
			else{
				//error
				System.out.println("Could not update record");
			}
		}
		catch(SQLException e){
			System.out.println("Could not update record");
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
