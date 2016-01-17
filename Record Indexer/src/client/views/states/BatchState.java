package client.views.states;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.imageio.ImageIO;

import shared.model.Field;
import shared.model.Project;
import shared.communication.*;
import client.checker.Checker;
import client.communication.ClientCommunicator;
import client.views.Cell;
import client.views.InfoManager;

public class BatchState {
	
	private ClientCommunicator communicator = ClientCommunicator.getCommunicator();
	private InfoManager infoManager = InfoManager.getInfo();
	
	private String[][] values;
	private Cell selectedCell;
	private ArrayList<BatchStateListener> bsListeners;
	
	private ArrayList<Cell> errorCells;
	
	private DownloadBatchResult batchOutput;
	
	BufferedImage image;
	private ArrayList<Field> fieldsofBatch;
	private Project currProject;
	private int imageID;
	
	
	public BatchState(){
		bsListeners = new ArrayList<BatchStateListener>();
		errorCells = new ArrayList<Cell>();
		image = null;
		fieldsofBatch = new ArrayList<Field>();
		
		selectedCell = new Cell(0, 0);
	}
	
	/**
	 * adds a listener to the batch state
	 * @param batch state listener to be added
	 */
	
	public void addBatchStateListener(BatchStateListener bsl){
		bsListeners.add(bsl);
	}
	
	public ArrayList<BatchStateListener> getBatchStateListeners(){
		return bsListeners;
	}
	
	/**
	 * initializes everything -- notifies all listener that batch has been downloaded
	 * gets image component, table, field help, form entry to display themselves
	 */
	
	public void initDownloadBatch(){
		
		String imageURLtemp = communicator.getURLPrefix() + "//" + batchOutput.getImage().getFile();
		imageURLtemp = imageURLtemp.replace("\\", "/");
		try {
			URL imageURL = new URL(imageURLtemp);
			image = ImageIO.read(imageURL);
			
			values = new String[currProject.getRecordsperimage()][fieldsofBatch.size() + 1];
			for(int i = 0; i < currProject.getRecordsperimage(); i++){
				values[i][0] = String.valueOf(i + 1);
			}
			
			for(BatchStateListener bsl : bsListeners){
				bsl.batchDownloaded(true);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * clears out everything -- once batch is submitted, all listeners will be notified
	 * image, table, form, field help will clear out
	 */
	
	public void submitBatch(){
		
		batchOutput = null;
		
		ArrayList<String> inputValues = new ArrayList<String>();
		
		for(int i = 0; i < values.length; i++){
			for(int j = 1; j < values[0].length; j++){
				if(values[i][j] == null){
					values[i][j] = "";
				}
				inputValues.add(values[i][j]);
			}
		}
		SubmitBatchParams input = new SubmitBatchParams(infoManager.getUsername(), infoManager.getPassword(), imageID, inputValues);
		SubmitBatchResult output = communicator.submitBatch(input);
		
		if(output != null){
			
			for(BatchStateListener bsl : bsListeners){
				bsl.batchSubmitted(true);
			}
		}
		else{
			System.out.println("submit failed");
		}
	}
	
	public void setProject(Project project){
		currProject = project;
	}
	
	public Project getProject(){
		return currProject;
	}
	
	public int getImageID(){
		return imageID;
	}
	
	public void setImageID(int idImage){
		imageID = idImage;
	}
	
	public ArrayList<Field> getFields(){
		return fieldsofBatch;
	}
	
	public void setFields(ArrayList<Field> inFields){
		fieldsofBatch = inFields;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage currImage){
		image = currImage;
	}
	
	public DownloadBatchResult getBatchOutput() {
		return batchOutput;
	}

	public void setBatchOutput(DownloadBatchResult batchOutput) {
		this.batchOutput = batchOutput;
	}

	public ArrayList<Cell> getErrors(){
		return errorCells;
	}
	
	public void setErrors(ArrayList<Cell> currErrors){
		errorCells = currErrors;
	}
	
	public String[][] getValues(){
		return values;
	}
	
	public void setValues(String [][] savedValues){
		values = savedValues;
		for(int i = 0; i < values.length; i++){
			for(int j = 1; j < values[0].length; j++){
				setValue(new Cell(i, j), values[i][j]);
			}
		}
	}
	/**
	 * notifies every listener that a new value is to be set -- also checks the value for misspellings
	 * @param cell -- what cell to set the value at
	 * @param value -- value to be set
	 */
	public void setValue(Cell cell, String value){
		values[cell.getRecord()][cell.getField()] = value;
		
		Checker spellChecker = new Checker();
		if(fieldsofBatch.get(cell.getField()-1).getKnowndata() != null && !fieldsofBatch.get(cell.getField()-1).getKnowndata().isEmpty()){
			try {
				File dictionaryFile = new File(fieldsofBatch.get(cell.getField()-1).getKnowndata());
				spellChecker.useDictionary(dictionaryFile);
				
				Set<String> suggestions = spellChecker.getSuggestions(value);
				
				if(suggestions.size() == 1 && suggestions.iterator().next().equals(value.toUpperCase())){
					if(errorCells.contains(cell)){
						errorCells.remove(cell);
					}
				}
				else if(value.isEmpty()){
					errorCells.remove(cell);
				}
				else{
					errorCells.add(cell);
				}
				
			} 
			catch (IOException e) {
				System.out.println("known data file is bad");
				e.printStackTrace();
			}
		}
		
		
		for(BatchStateListener bsl : bsListeners){
			bsl.valueChanged(cell, value);
		}

	}
	
	public String getValue(Cell cell){
		if(values[cell.getRecord()][cell.getField()] == null){
			values[cell.getRecord()][cell.getField()] = "";
		}
		
		return values[cell.getRecord()][cell.getField()];
		
	}
	/**
	 * notifies all listener that a different cell has been selected
	 * @param cell to be selected
	 */
	
	public void setSelectedCell(Cell sCell){
		selectedCell = sCell;
		
		for(BatchStateListener bsl : bsListeners){
			bsl.selectedCellChanged(sCell);
		}
		
	}
	
	public Cell getSelectedCell(){
		return selectedCell;
	}
}