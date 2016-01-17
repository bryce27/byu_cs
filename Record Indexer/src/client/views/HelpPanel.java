package client.views;

import javax.swing.JTabbedPane;

import client.views.states.BatchState;

public class HelpPanel extends JTabbedPane{
	private FieldHelpPanel fieldHelpPanel;
	private NavigationPanel navigationPanel;
	
	public HelpPanel(){
		fieldHelpPanel = new FieldHelpPanel();
		navigationPanel = new NavigationPanel();
		
		this.addTab("Field Help", fieldHelpPanel);
		this.addTab("Image Navigation", navigationPanel);
	}
	
	public void setBatchState(BatchState state){
		fieldHelpPanel.setBatchState(state);
	}
}