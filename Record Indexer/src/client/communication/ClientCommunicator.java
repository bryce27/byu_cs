package client.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import shared.communication.DownloadBatchParams;
import shared.communication.DownloadBatchResult;
import shared.communication.GetFieldsParams;
import shared.communication.GetFieldsResult;
import shared.communication.GetProjectsParams;
import shared.communication.GetProjectsResult;
import shared.communication.GetSampleImageParams;
import shared.communication.GetSampleImageResult;
import shared.communication.SearchParams;
import shared.communication.SearchResult;
import shared.communication.SubmitBatchParams;
import shared.communication.SubmitBatchResult;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ClientCommunicator {
	
	private String urlPrefix = "";
	private static ClientCommunicator communicator = new ClientCommunicator();
	public ClientCommunicator(){
		
	}
	public ClientCommunicator(String host, String port){
		urlPrefix = "http://" + host + ":" + port;
	}
	public void getHostPort(String host, String port){
		urlPrefix = "http://" + host + ":" + port;
	}
	
	public static ClientCommunicator getCommunicator(){
		return communicator;
	}
	
	public String getURLPrefix(){
		return urlPrefix;
	}

	/**
	 * Validates the user's credentials
	 * @param username, password
	 * @return success status of user's input,
	 * 		if successful, returns name 
	 * 		and number of records indexed.
	 */
	public ValidateUserResult validateUser(ValidateUserParams params){
		return (ValidateUserResult) doPost("/validateuser", params);
	}
	
	/**
	 * Requests info about all available projects
	 * @param username, password	
	 * @return available projects
	 */
	public GetProjectsResult getProject(GetProjectsParams params){
		return (GetProjectsResult) doPost("/getprojects", params);
	}
	
	/**
	 * Requests a sample image from a given project
	 * @param username, password, projectID
	 * @return success status, image
	 */
	public GetSampleImageResult getSampleImage(GetSampleImageParams params){
		return (GetSampleImageResult) doPost("/getsampleimage", params);
	}
	
	/**
	 * Downloads a batch for the user to index
	 * @param username, password, project id
	 * @return batch and its info
	 */
	public DownloadBatchResult downloadBatch(DownloadBatchParams params){
		return (DownloadBatchResult) doPost("/downloadbatch", params);
	}
	
	/**
	 * Submits the indexed record from a batch to the server
	 * @param batch
	 * @return success status
	 */
	public SubmitBatchResult submitBatch(SubmitBatchParams params){
		return (SubmitBatchResult) doPost("/submitbatch", params);
	}
	
	/**
	 * Returns info about all fields of a given project
	 * @param username, password, project id
	 * @return project id, field info 
	 */
	public GetFieldsResult getFields(GetFieldsParams params){
		return (GetFieldsResult) doPost("/getfields", params);
	}
	
	/**
	 * Searches the indexed records for given strings 
	 * If results are found, returns batch id, image url, record number, and field id.
	 * @param username, pasword, fields to search, and search values
	 * @return search results
	 */
	public SearchResult search(SearchParams params){
		return (SearchResult) doPost("/search", params);
	}
	
	/**
	 * Make POST request to the given URL with given post data
	 * @param url
	 * @param postData object
	 * @return response object
	 */
	private Object doPost(String urlPath, Object postData) {
		Object response = null;
		try { 
			urlPath = urlPrefix + urlPath;
			URL url = new URL(urlPath); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			 
			connection.setRequestMethod("POST"); 
			connection.setDoOutput(true); 
			 
			connection.connect(); 
			 
			OutputStream requestBody = connection.getOutputStream(); 
			XStream xmlStream = new XStream(new DomDriver());
			// Write request body to OutputStream ...
			xmlStream.toXML(postData, requestBody);
			  
			requestBody.close(); 
			 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { 
				 
				InputStream responseBody = connection.getInputStream(); 
				// Read response body from InputStream ...
				response = xmlStream.fromXML(responseBody);
				 
			} 
			
			else if(connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
				//wrong user or password
				return new ValidateUserResult(false, null);
			}
			
			else { 
				System.out.println("FAILED");
				return null;
			} 
		} 
		
		catch(IOException e){
			e.printStackTrace();
			System.out.println("Could not doPost");
		}

		return response;
	}
}
