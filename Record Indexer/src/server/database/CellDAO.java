package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Cell;

public class CellDAO {
	
	private Database db;
	
	CellDAO(Database db) {
		this.setDb(db);
	}

		/**
		 * Get a cell from the database
		 * @param recordID
		 * @param fieldID
		 * @return
		 */
		public Cell getCell(int recordID, int fieldID){
			// hold off until I know this is needed
			return new Cell();
		}
		
		/**
		 * Get a list of cells that belong to a given field and match a given string
		 * @param fieldID
		 * @param inputString
		 * @return arraylist of all cells that match the fieldID and inputString
		 */
		public ArrayList<Cell> getCellsByFieldID(int fieldID, String inputString){
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Cell> cells = new ArrayList<Cell>();
			try {
				String sql = "SELECT * from cells WHERE field_id = ? AND content = ?";
				pstmt = db.getConnection().prepareStatement(sql);
				pstmt.setInt(1,  fieldID);
				pstmt.setString(2, inputString);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Cell cell = new Cell(rs.getString("content"), rs.getInt("record_id"), rs.getInt("field_id"));
					cells.add(cell);
				}
			} catch (SQLException e) {
				System.out.println("Could not get cell cells");
				e.printStackTrace();
			}  finally{
				try{
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				}
				 catch (SQLException e) {
					System.out.println("Could not close prepared statement or resultset");
					e.printStackTrace();
				}
			}
			return cells;
		}
		
		/**
		 * Get a list of cells that belong to a given record
		 * @param recordID
		 * @return arraylist of cells in the record given
		 */
		public ArrayList<Cell> getCellsByRecordID(int recordID){
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Cell> cells = new ArrayList<Cell>();
			try {
				String sql = "SELECT * from cells WHERE record_id = ?";
				pstmt = db.getConnection().prepareStatement(sql);
				pstmt.setInt(1,  recordID);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Cell cell = new Cell(rs.getString("content"), rs.getInt("record_id"), rs.getInt("field_id"));
					cells.add(cell);
				}
			} catch (SQLException e) {
				System.out.println("Could not get cell cells");
				e.printStackTrace();
			}  finally{
				try{
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				}
				 catch (SQLException e) {
					System.out.println("Could not close prepared statement or resultset!?");
					e.printStackTrace();
				}
			}
			return cells;
		}
		
		/**
		 * Get all cells from the database
		 * @return arraylist of cells
		 */
		public ArrayList<Cell> getAllCells(){
			
			ArrayList<Cell> allValues = new ArrayList<Cell>();
			PreparedStatement pstmt = null; 
			ResultSet rs = null;
			try{
				String sql = "SELECT * from cells";
				pstmt = db.getConnection().prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Cell cell = new Cell(rs.getString("content"), rs.getInt("record_id"), rs.getInt("field_id"));
					allValues.add(cell);
				}
			}
			catch(SQLException e){
				e.printStackTrace();
				System.out.println("Could not get all cells");
			}
			finally{
				try{
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				}
				 catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			return allValues;
		}
		
		/**
		 * Add a cell to the database
		 * @param cell
		 * @return cell's new id
		 */
		public int add(Cell cell){
			
			int id = 0;
			PreparedStatement pstmt = null; 
			ResultSet rs = null;
			Connection connection = null;
			Statement stmt = null;
			try{
				String sql =  "INSERT INTO cells " +
						"(content, record_id, field_id)" +
						"VALUES(?,?,?)";
				connection = db.getConnection();
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1,  cell.getContent());
				pstmt.setInt(2,  cell.getRecord_id());
				pstmt.setInt(3, cell.getField_id());
				
				// unnecessary, as cell has no auto increment id
				if(pstmt.executeUpdate() == 1){
					stmt = connection.createStatement();
					//rs = stmt.executeQuery("SELECT last_insert_rowid()");
					//rs.next();
					//id = rs.getInt(1);
				}
				else{
					System.out.println("Adding failed");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				try{
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
					if(stmt != null) {stmt.close();}
				}
				 catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return id;
		}
		
		/**
		 * Update a cell's info
		 * @param cell
		 */
		public void update(Cell cell){
			
			PreparedStatement pstmt = null; 
			// TODO: make sure this sql works
			
			try{
				String sql = "UPDATE cells " + 
								"SET content = ?, record_id = ?, field_id = ?" +
								"WHERE record_id = ? AND field_id = ?";
				pstmt = db.getConnection().prepareStatement(sql);
				pstmt.setString(1,  cell.getContent());
				pstmt.setInt(2,  cell.getRecord_id());
				pstmt.setInt(3, cell.getField_id());
				pstmt.setInt(4,  cell.getRecord_id());
				pstmt.setInt(5, cell.getField_id());
				if(pstmt.executeUpdate() == 1){
					//ok
					System.out.println("Cell updated sucessfully");
				}
				else{
					//error
					System.out.println("Could not update cell");
				}
			}
			catch(SQLException e){
				System.out.println("Could not update cell");
				e.printStackTrace();
			}
			finally{
				try{
					if(pstmt != null) {pstmt.close();}
				} 
				catch(SQLException e){
					System.out.println("failed to close prepared statement");
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
