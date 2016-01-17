package client.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import client.communication.ClientCommunicator;
import client.views.states.BatchState;
import client.views.states.BatchStateListener;
import shared.model.Field;

public class FieldHelpPanel extends JScrollPane{
	
	private BatchState batchState;
	private ClientCommunicator communicator = ClientCommunicator.getCommunicator();
	
	private JEditorPane helpDisplay;
	
	private ArrayList<Field> fields;
	private Field currField;

	public FieldHelpPanel(){
		
		helpDisplay = new JEditorPane();
		helpDisplay.setContentType("text/html");
		
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		

	}

	public void setBatchState(BatchState state){
		batchState = state;
		batchState.addBatchStateListener(bsl);
	}
	
	/**
	 * displays the field help
	 * @param field
	 */
	
	public void displayHelp(Field field){
		try{
			String tempURL = communicator.getURLPrefix() + "//" + field.getHelphtml();
			tempURL = tempURL.replace("\\", "/");
			URL helpURL = new URL(tempURL);
			helpDisplay.setPage(helpURL);
			
		}
		catch(IOException e){
			System.out.println("bad field help url");
			e.printStackTrace();
		}
		
		
	}
	
	private BatchStateListener bsl = new BatchStateListener(){

		@Override
		public void batchDownloaded(boolean downloaded) {
			if(downloaded == true){
				fields = batchState.getFields();
				currField = fields.get(0);
				FieldHelpPanel.this.getViewport().add(helpDisplay);
				displayHelp(currField);
			}
			
		}

		@Override
		public void batchSubmitted(boolean submitted) {
			if(submitted == true){
				FieldHelpPanel.this.getViewport().remove(helpDisplay);
				helpDisplay = new JEditorPane();
				helpDisplay.setContentType("text/html");
				FieldHelpPanel.this.repaint();
			}
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			if(newSelectedCell.getField() == 0){
				return;
			}
			else{
				int fieldNum = newSelectedCell.getField();
				currField = fields.get(fieldNum-1);
				displayHelp(currField);
			}
			
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			
		}
		
	};
}