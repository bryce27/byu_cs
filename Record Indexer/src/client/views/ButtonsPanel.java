package client.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.views.states.BatchState;
import client.views.states.BatchStateListener;

public class ButtonsPanel extends JPanel {
	
	private BatchState batchState;
	
	private MainFrame tempMainframe;
	private DisplayComponent display;
	
	private JButton zoominButton;
	private JButton zoomoutButton;
	private JButton invertButton;
	private JButton toggleButton;
	private JButton saveButton;
	private JButton submitButton;
	
	public ButtonsPanel(){
		
		createButtons();
		toggleButtons(false);
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		this.add(zoominButton);
		this.add(zoomoutButton);
		this.add(invertButton);
		this.add(toggleButton);
		this.add(saveButton);
		this.add(submitButton);
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
		batchState.addBatchStateListener(bsl);
	}
	
	private void createButtons(){
		zoominButton = new JButton("Zoom In");
		zoominButton.addActionListener(zoominListener);
		zoomoutButton = new JButton("Zoom Out");
		zoomoutButton.addActionListener(zoomoutListener);
		invertButton = new JButton("Invert Image");
		invertButton.addActionListener(invertListener);
		toggleButton = new JButton("Toggle Highlights");
		toggleButton.addActionListener(toggleListener);
		saveButton = new JButton("Save");
		saveButton.addActionListener(saveListener);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(submitListener);
	}
	/**
	 * toggles buttons -- if user is working on a batch, buttons are enabled; 
	 * if not, buttons are disabled
	 * @param enable or disable buttons
	 */
	
	public void toggleButtons(boolean on){
		zoominButton.setEnabled(on);
		zoomoutButton.setEnabled(on);
		invertButton.setEnabled(on);
		toggleButton.setEnabled(on);
		saveButton.setEnabled(on);
		submitButton.setEnabled(on);
	}
	
	public void setMainframe(MainFrame indexerframe){
		tempMainframe = indexerframe;
	}
	
	public void setDisplay(DisplayComponent displayComp){
		display = displayComp;
	}
	
	private ActionListener zoominListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			display.zoomin();
		}
		
	};
	
	private ActionListener zoomoutListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			display.zoomout();
		}
		
	};
	
	private ActionListener invertListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {	
			display.invert(false);
		}
		
	};
	
	private ActionListener toggleListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			display.toggleHighlight();
		}
		
	};
	
	private ActionListener saveListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			tempMainframe.saveCurrentUserState();
			
		}
		
	};
	
	private ActionListener submitListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			batchState.submitBatch();
		}
		
	};
	
	private BatchStateListener bsl = new BatchStateListener() {

		@Override
		public void batchDownloaded(boolean downloaded) {
			ButtonsPanel.this.toggleButtons(true);	
		}

		@Override
		public void batchSubmitted(boolean submitted) {
			ButtonsPanel.this.toggleButtons(false);
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			
		}
		
	};
	
}