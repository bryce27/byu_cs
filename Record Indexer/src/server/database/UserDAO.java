package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.model.User;

public class UserDAO {
	
	private Database db;
	
	UserDAO(Database db) {
		this.setDb(db);
	}
	
	/**
	 * Get a user from the database
	 * @param username
	 * @return user
	 */
	public User getUser(String username, String password){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			String sql = "Select * from users WHERE username = ?";
			// TODO: make sure to check for password after you verify that this works
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1,  username);
			rs = pstmt.executeQuery();
			while(rs.next()){
				user = new User(rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), 
						rs.getString("email"), rs.getInt("currentbatch_id"), rs.getInt("indexedrecords"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get user, SQL exception");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Ugh. Could not close prepared statement or resultset!");
				e.printStackTrace();
			}
		}
		return user;
	}
	
	/**
	 * Add a user to the database
	 * @param user
	 * @return user's new id
	 */
	public int add(User user){
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = db.getConnection();
		Statement stmt = null;

		try{
			String sql =  "INSERT INTO users" +
					"(username, password, firstname, lastname, email, currentbatch_id, indexedrecords)" +
					"VALUES(?,?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  user.getUsername());
			pstmt.setString(2,  user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setInt(6, user.getCurrentbatch_id());
			pstmt.setInt(7,  user.getIndexedrecords());
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				user.setUserID(id);
			}
			else{
				System.out.println("Adding user failed");
			}
			
		} catch (SQLException e) {
			// TODO Write something good here
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
			}
			 catch (SQLException e) {
				// TODO Write something good here
				e.printStackTrace();
			}
		}
		return id;
	}
	
	/**
	 * Update a user's info
	 * @param user
	 */
	public void update(User user){
				// only updates current batch id and records indexed
				PreparedStatement pstmt = null; 
				
				try{
					String sql = "UPDATE users SET indexedrecords = ?, currentbatch_id = ? WHERE id = ?";
					pstmt = db.getConnection().prepareStatement(sql);
					pstmt.setInt(1,  user.getIndexedrecords());
					pstmt.setInt(2,  user.getCurrentbatch_id());
					pstmt.setInt(3, user.getUserID());
					if(pstmt.executeUpdate() == 1){
						System.out.println("User updated successfuly!");
					}
					else{
						System.out.println("Could not update user");
					}
				}
				catch(SQLException e){
					System.out.println("Could not update user");
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
