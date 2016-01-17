package client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.views.states.BatchState;
import client.views.states.BatchStateListener;
import shared.model.Field;

public class FormEntryPanel extends JSplitPane{
	
	private BatchState batchState;
	
	private JList<Integer> recordNums;
	private ArrayList<Field> currFields;
	private int numRecords;
	private JScrollPane numScrollPane;
	private JScrollPane fieldScrollPane;
	private ArrayList<JTextField> textFields;
	private JPanel fieldsPanel;
	private JTextField currTextField;
	
	public FormEntryPanel(){
		recordNums = new JList<Integer>();
		textFields = new ArrayList<JTextField>();
		
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
		batchState.addBatchStateListener(bsl);
	}
	/**
	 * generates the number panel (for the row/records of form entry)
	 */
	
	private void generateNumPanel(){
		Integer[] numArray = new Integer[numRecords];
		for(int i = 0; i < numRecords; i++){
			numArray[i] = i+1;
		}
		recordNums.setListData(numArray);
		recordNums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recordNums.setSelectedIndex(0);
		
		numScrollPane = new JScrollPane(recordNums);
	}
	
	/**
	 * generates the field panel for the column/field of form entry
	 */
	
	private void generateFieldPanel(){
		fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridLayout(currFields.size(), 1));
		for (int i = 0; i < currFields.size(); i++){
			JPanel formEntry = new JPanel();
			
			JLabel fieldLabel = new JLabel(currFields.get(i).getTitle() + " ");
			JTextField fieldText = new JTextField(10);
			JPanel fieldEntry = new JPanel(new FlowLayout(FlowLayout.LEFT));
			fieldEntry.add(fieldLabel);
			fieldEntry.add(fieldText);
			fieldText.addFocusListener(textFocusListener);
			fieldText.setName(String.valueOf(i));
			fieldText.addMouseListener(mouseAdapter);
			textFields.add(fieldText);
			
			formEntry.add(fieldEntry);
			
			fieldsPanel.add(formEntry);
		}
		currTextField = textFields.get(0);
		fieldScrollPane = new JScrollPane(fieldsPanel);
	}
	
	public void reqFocus(){
        currTextField.requestFocusInWindow();
    }
	
	private BatchStateListener bsl = new BatchStateListener() {

		@Override
		public void batchDownloaded(boolean downloaded) {
			recordNums = new JList<Integer>();
			textFields = new ArrayList<JTextField>();
			
			currFields = batchState.getFields();
			numRecords = batchState.getProject().getRecordsperimage();
			
			generateNumPanel();
			generateFieldPanel();
			
			int currRow = batchState.getSelectedCell().getRecord();
			int currCol = batchState.getSelectedCell().getField();
		
			recordNums.addListSelectionListener(listListener);
			
			recordNums.setSelectedIndex(currRow);
			textFields.get(currCol).requestFocus();
			
			FormEntryPanel.this.setLeftComponent(numScrollPane);
			FormEntryPanel.this.setRightComponent(fieldScrollPane);
			FormEntryPanel.this.setResizeWeight(0.4);
			
		}

		@Override
		public void batchSubmitted(boolean submitted) {
			numScrollPane.getViewport().remove(recordNums);
			fieldScrollPane.getViewport().remove(fieldsPanel);
			numScrollPane = new JScrollPane();
			fieldScrollPane = new JScrollPane();
			currFields = new ArrayList<Field>();
			numRecords = 0;
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			recordNums.setSelectedIndex(newSelectedCell.getRecord());
			int currCol = newSelectedCell.getField();
			if(currCol != 0){
				currCol = currCol - 1;
			}
			currTextField = textFields.get(currCol);
			textFields.get(currCol).requestFocusInWindow();

			
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			int row = cell.getRecord();
			int col = cell.getField() - 1;
			
			textFields.get(col).setText(newValue);
			if(recordNums.getSelectedIndex() == row){
				if(batchState.getErrors().contains(cell)){
					
					textFields.get(col).setBackground(Color.RED);
					
				}
				else{
					textFields.get(col).setBackground(Color.WHITE);
				}
			}
			
		}
		
	};
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		@Override
		public void mousePressed(MouseEvent e){
			if(e.isPopupTrigger()){
				int row = recordNums.getSelectedIndex();
				
				JTextField focusedField = (JTextField) e.getSource();
				int col = Integer.parseInt(focusedField.getName());
				
				Cell currCell = new Cell(row, col+1);
				
				if(batchState.getErrors().contains(currCell)){
					SuggestionPopUp popup = new SuggestionPopUp();
					popup.setBatchState(batchState);
					popup.createSuggestions(currCell);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			if(e.isPopupTrigger()){
				int row = recordNums.getSelectedIndex();
				
				JTextField focusedField = (JTextField) e.getSource();
				int col = Integer.parseInt(focusedField.getName());
				
				Cell currCell = new Cell(row, col+1);
				
				if(batchState.getErrors().contains(currCell)){
					SuggestionPopUp popup = new SuggestionPopUp();
					popup.setBatchState(batchState);
					popup.createSuggestions(currCell);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
		
	};
	/**
	 * list selection listener for number list
	 */
	
	private ListSelectionListener listListener = new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = recordNums.getSelectedIndex();
			Cell currCell = batchState.getSelectedCell();
			currCell.setRecord(row);
			int currCol = currCell.getField();
			if(currCol != 0){
				currCol = currCol - 1;
			}
			textFields.get(currCol).requestFocus();
			batchState.setSelectedCell(currCell);
			
			for(int i = 0; i < textFields.size(); i++){
				String text = batchState.getValue(new Cell(row, i+1));
				textFields.get(i).setText(text);
				if(batchState.getErrors().contains(new Cell(row, i+1))){
					textFields.get(i).setBackground(Color.RED);
				}
				else{
					textFields.get(i).setBackground(Color.WHITE);
				}
			}
		}
		
	};
	/**
	 * focus listeners for the text fields
	 */
	
	private FocusListener textFocusListener = new FocusListener(){

		@Override
		public void focusGained(FocusEvent e) {
			
			int row = recordNums.getSelectedIndex();
			JTextField focusedField = (JTextField) e.getSource();
			int col = Integer.parseInt(focusedField.getName());
			
			batchState.setSelectedCell(new Cell(row, col + 1));
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			
			int row = recordNums.getSelectedIndex();
			JTextField focusedField = (JTextField) e.getSource();
			int col = Integer.parseInt(focusedField.getName());
			String value = focusedField.getText();
			batchState.setValue(new Cell(row, col+1), value);
			
		}
		
	};

}