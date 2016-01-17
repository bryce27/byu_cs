package servertester.controllers;

import java.io.IOException;
import java.util.*;

import client.communication.ClientCommunicator;
import servertester.views.*;
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

import shared.model.Field;
import shared.model.Image;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("8080");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		ClientCommunicator cc = new ClientCommunicator();
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		ValidateUserParams input = new ValidateUserParams(params[0], params[1]);
		getView().setRequest(input.toString());
		ValidateUserResult result = cc.validateUser(input);

		
		if (result != null){
			if (result.isApproved()){
				String resultStr = result.toString();
				getView().setResponse(resultStr);
			}
			else {
				getView().setResponse("FALSE\n");
			}
		}
		
		else {
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getProjects() {
		ClientCommunicator cc = new ClientCommunicator();
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		GetProjectsParams input = new GetProjectsParams(params[0], params[1]);
		getView().setRequest(input.toString());
		GetProjectsResult output = cc.getProject(input);
		
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
		
	}
	
	private void getSampleImage() {
		ClientCommunicator cc = new ClientCommunicator();
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		GetSampleImageParams input = new GetSampleImageParams(params[0], params[1], Integer.parseInt(params[2]));
		getView().setRequest(input.toString());
		GetSampleImageResult output = cc.getSampleImage(input);
		if(output != null){
			output.getImage().addUrl(getView().getHost(), getView().getPort());
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() {
		ClientCommunicator cc = new ClientCommunicator();
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		DownloadBatchParams input = new DownloadBatchParams(params[0], params[1], Integer.parseInt(params[2]));
		getView().setRequest(input.toString());
		DownloadBatchResult output = cc.downloadBatch(input);
		System.out.println("heere");

		if(output != null){
			for(Field field : output.getProjectFields()){
				field.addUrl(getView().getHost(), getView().getPort());
			}
			output.getImage().addUrl(getView().getHost(), getView().getPort());
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields() {

		ClientCommunicator cc = new ClientCommunicator();
		
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		GetFieldsParams input = null;
		try{
			input = new GetFieldsParams(params[0], params[1], Integer.parseInt(params[2]));
		}
		catch(Exception e){
			input = new GetFieldsParams(params[0], params[1], -1);
		}
		getView().setRequest(input.toString());
		GetFieldsResult output = cc.getFields(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void submitBatch() {
		ClientCommunicator cc = new ClientCommunicator();
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		String userInput = params[3].toLowerCase();
		ArrayList<String> allValues = new ArrayList<String>();
		String [] records = userInput.split(";");
		for(String record : records){
			String [] values = record.split(",");
			List<String> tempList = Arrays.asList(values);
			allValues.addAll(tempList);
		}
		SubmitBatchParams input = new SubmitBatchParams(params[0], params[1], Integer.parseInt(params[2]), allValues);
		getView().setRequest(input.toString());
		SubmitBatchResult output = cc.submitBatch(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private ArrayList<Integer> parseValuesToInts(String data){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (String value : data.split(",")){
			result.add(Integer.parseInt(value));
		}
		System.out.println("Here is the int array that was created");
		System.out.println(result.toString());
		return result;
	}
	
	private ArrayList<String> parseValuesToStrings(String data){
		ArrayList<String> result = new ArrayList<String>();
		for (String value : data.split(",")){
			result.add(value);
		}
		System.out.println("Here is the int array that was created");
		System.out.println(result.toString());
		return result;
	}
	
	private void search() {
		ClientCommunicator cc = new ClientCommunicator();
		cc.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		String userInputSearch = params[3].toLowerCase();
		String [] parsedFields = params[2].split(",");
		String [] parsedSearch = userInputSearch.split(",");
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		for(int i = 0; i < parsedFields.length; i++){
			fieldIDs.add(Integer.parseInt(parsedFields[i]));
		}
		ArrayList<String> searchValues = new ArrayList<String>();
		for(String value: parsedSearch){
			searchValues.add(value);
		}
		SearchParams input = new SearchParams(params[0], params[1], fieldIDs, searchValues);
		getView().setRequest(input.toString());
		SearchResult output = cc.search(input);
		if(output != null){
			for(Image image : output.getImages()){
				image.addUrl(getView().getHost(), getView().getPort());
			}
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}

}

