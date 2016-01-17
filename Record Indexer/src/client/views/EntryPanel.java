package client.views;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.views.states.BatchState;
import client.views.states.BatchStateListener;

public class EntryPanel extends JTabbedPane{
	private TableEntryPanel tablePanel;
	private FormEntryPanel formPanel;
	
	
	public EntryPanel(){
		
		tablePanel = new TableEntryPanel();
		formPanel = new FormEntryPanel();
		
		this.addTab("Table Entry", tablePanel);
		this.addTab("Form Entry", formPanel);
		this.addChangeListener(tabChangedListener);
	}
	
	public void setBatchState(BatchState state){
		tablePanel.setBatchState(state);
		formPanel.setBatchState(state);
	}
	/**
	 * change listener to notify form panel that user has changed tabs and that 
	 * the selected field must be given focus
	 */
	
	private ChangeListener tabChangedListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			if(EntryPanel.this.getSelectedIndex() == 1){
                formPanel.reqFocus();
            }
		}
        
    };

	
}