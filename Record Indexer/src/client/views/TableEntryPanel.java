package client.views;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import shared.model.Field;
import client.table.CellRenderer;
import client.table.EntryScheme;
import client.table.EntryTableModel;
import client.views.states.BatchState;
import client.views.states.BatchStateListener;

public class TableEntryPanel extends JScrollPane {
	
	private BatchState batchState;
	
	private ArrayList<EntryScheme> entrySchemes;
	private EntryTableModel tableModel;
	private JTable table;
	
	private ArrayList<Field> fields;
	
	private Cell currCell;
	
	public TableEntryPanel(){
		table = new JTable();
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
		batchState.addBatchStateListener(bsl);
	}
	/**
	 * generates schemes
	 * @param how many rows there are in the table
	 * @param fields to generate table with
	 * @return array list of schemes
	 */
	
	private ArrayList<EntryScheme> generateSchemes(int rows, ArrayList<Field> inFields) {
		
		ArrayList<EntryScheme> result = new ArrayList<EntryScheme>();
		
		for(int i = 0; i < rows; i++){
			EntryScheme entry = new EntryScheme(i+1, inFields);
			result.add(entry);
		}
		
		return result;
	}
	
	/**
	 * sets up the table and initializes everything
	 */
	
	private void setupTable(){
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.changeSelection(0, 1, false, false);
		table.requestFocus();
		table.addMouseListener(mouseAdapter);
		table.getSelectionModel().addListSelectionListener(cellChangeListener);
		table.getColumnModel().getSelectionModel().addListSelectionListener(cellChangeListener);
		
		TableColumnModel columnModel = table.getColumnModel();		
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setWidth(80);
			CellRenderer rend = new CellRenderer();
			rend.setBatchState(batchState);
			column.setCellRenderer(rend);
		}		
	
	}
	
	private BatchStateListener bsl = new BatchStateListener(){

		@Override
		public void batchDownloaded(boolean downloaded) {
			if(downloaded == true){
				int numRecords = batchState.getProject().getRecordsperimage();
				fields = batchState.getFields();
				
				entrySchemes = generateSchemes(numRecords, fields);
				tableModel = new EntryTableModel(entrySchemes);
				tableModel.setBatchState(batchState);
				setupTable();
				
				TableEntryPanel.this.getViewport().add(table);
				
			}
			
		}

		@Override
		public void batchSubmitted(boolean submitted) {
			TableEntryPanel.this.getViewport().remove(table);
			table = new JTable();
			fields = new ArrayList<Field>();
			entrySchemes = new ArrayList<EntryScheme>();
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			table.changeSelection(newSelectedCell.getRecord(), newSelectedCell.getField(), false, false);
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			table.repaint();
		}
		
	};
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		
		@Override
		public void mousePressed(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON3){
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				Cell currCell = new Cell(row, col);
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
			if(e.getButton() == MouseEvent.BUTTON3){
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				Cell currCell = new Cell(row, col);
				if(batchState.getErrors().contains(currCell)){
					SuggestionPopUp popup = new SuggestionPopUp();
					popup.setBatchState(batchState);
					popup.createSuggestions(currCell);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
				
			}
		}
		
	};
	
	private ListSelectionListener cellChangeListener = new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = table.getSelectedRow();
			int col = table.getSelectedColumn();
			currCell = new Cell(row, col);
			batchState.setSelectedCell(currCell);
			
		}
		
	};
}