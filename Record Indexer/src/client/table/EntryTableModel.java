package client.table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import client.views.Cell;
import client.views.states.BatchState;

@SuppressWarnings("serial")
public class EntryTableModel extends AbstractTableModel{
	
	/**
	 * abstract table model with overrided methods for the table in TableEntry
	 */

	private BatchState batchState;
	
	private ArrayList<EntryScheme> entrySchemes;
	
	
	public EntryTableModel(ArrayList<EntryScheme> entrySchemes){
		this.entrySchemes = entrySchemes;
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
	}
	
	@Override
	public int getColumnCount() {
		return entrySchemes.get(0).getColumns();
	}
	
	@Override
	public String getColumnName(int col){
		String result = null;
		
		if(col == 0){
			result = "Record Number";
		}
		else if(col > 0 && col < getColumnCount()){
			result = entrySchemes.get(0).getFields().get(col - 1).getTitle();
		}
		else{
			throw new IndexOutOfBoundsException();
		}
		
		return result;
	}

	@Override
	public int getRowCount() {
		return entrySchemes.size();
	}

	@Override
	public boolean isCellEditable(int row, int col){
		if(col == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		Object result = null;
		
		if (row >= 0 && row < getRowCount() && col >= 0	&& col < getColumnCount()) {

			result = batchState.getValue(new Cell(row, col));
			
		}
		else{
			throw new IndexOutOfBoundsException();
		}
		
		return result;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount()) {
			batchState.setValue(new Cell(row, col), (String) value);
			this.fireTableCellUpdated(row, col);
		}
		else{
			throw new IndexOutOfBoundsException();
		}
	}

}