package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Image;

public class ImageDAO {

	private Database db;
	
	ImageDAO(Database db) {
		this.setDb(db);
	}
	
	/**
	 * Get a image from the database
	 * @param imageID
	 * @return image
	 */
	public Image getImage(int imageID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Image image = null;
		try {
			String sql = "SELECT * from images WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  imageID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				image = new Image(rs.getString("file"), rs.getBoolean("isAvailable"), rs.getInt("project_id"), rs.getInt("id"));
			}

		} catch (SQLException e) {
			System.out.println("Could not get image");
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
		return image;
	}
	
	/**
	 * Get a list of images that belong to a specific project
	 * @param projectID
	 * @return arraylist of images in the project given
	 */
	public ArrayList<Image> getImagesbyProjectID(int projectID){
		ArrayList<Image> imagesbyProject = new ArrayList<Image>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from images WHERE project_id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1, projectID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Image image = new Image(rs.getString("file"), rs.getBoolean("isAvailable"), rs.getInt("project_id"), rs.getInt("id"));
				imagesbyProject.add(image);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not get images by projectID");
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				 System.out.println("Could not close prepared statement resultset");
				e.printStackTrace();
			}
			
		}
		return imagesbyProject;
	}
	
	/**
	 * Get all images from the database
	 * @return arraylist of images
	 */
	public ArrayList<Image> getAllImages(){
		ArrayList<Image> allImages = new ArrayList<Image>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from images";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Image image = new Image(rs.getString("file"), rs.getBoolean("isAvailable"), rs.getInt("project_id"), rs.getInt("id"));
				allImages.add(image);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not get all images");
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Could not close prepared statement resultset");
				e.printStackTrace();
			}
			
		}
		return allImages;
	}
	
	/**
	 * Add a image to the database
	 * @param image
	 * @return image's new id
	 */
	public int add(Image image){
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO images" +
					"(file, project_id, isAvailable)" +
					"VALUES(?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  image.getFile());
			pstmt.setInt(2,  image.getProject_id());
			pstmt.setBoolean(3, image.isAvailable());
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
			}
			else{
				System.out.println("Could not add image to database");
			}
			
		} catch (SQLException e) {
			System.out.println("Could not add image to database");
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
			}
			 catch (SQLException e) {
					System.out.println("Could not close prepared statement resultset");
				e.printStackTrace();
			}
		}
		return id;
	}
	
	/**
	 * Update a image's info
	 * @param image
	 */
	public void update(Image image){
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE images " + 
							"SET file = ?, isAvailable = ?, project_id = ?" +
							"WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1,  image.getFile());
			pstmt.setBoolean(2, image.isAvailable());
			pstmt.setInt(3,  image.getProject_id());
			pstmt.setInt(4,  image.getId());
			if(pstmt.executeUpdate() == 1){
				//ok
				System.out.println("Image updated successfuly");
			}
			else{
				//error
				System.out.println("Could not update image");
			}
		}
		catch(SQLException e){
			System.out.println("image update failed");
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
