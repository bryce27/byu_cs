package client.table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import client.views.Cell;
import client.views.states.BatchState;

@SuppressWarnings("serial")
public class CellRenderer extends JLabel implements TableCellRenderer {
	private BatchState batchState;
	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 0);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
	
	public CellRenderer(){
		setOpaque(true);
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		
		this.setBackground(Color.WHITE);
		ArrayList<Cell> errors = batchState.getErrors();
		
		if(errors.contains(new Cell(row, column)) && isSelected){
			this.setBorder(selectedBorder);
			this.setBackground(Color.RED);
		}
		else if(errors.contains(new Cell(row, column))){
			this.setBackground(Color.RED);
			this.setBorder(unselectedBorder);
		}
		else if(isSelected){
			this.setBorder(selectedBorder);
			this.setBackground(new Color(100, 149, 237, 128));
		}
		else{
			this.setBackground(Color.WHITE);
			this.setBorder(unselectedBorder);
		}
		
		this.setText((String) value);
		
		return this;
	}

}