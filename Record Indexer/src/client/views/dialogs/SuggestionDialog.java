package client.views.dialogs;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.views.Cell;
import client.views.states.BatchState;

@SuppressWarnings("serial")
public class SuggestionDialog extends JDialog{
	private JList<String> suggestions;
	private JButton useButton;
	private JButton cancelButton;
	
	private JPanel buttonsPanel;
	private JScrollPane suggestionsScroll;
	
	private Cell errorCell;
	
	private BatchState batchState;
	
	public SuggestionDialog(Set <String> currSuggestions, Cell error){
		super(null, "Suggestions", Dialog.ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		
		errorCell = error;
		
		createComponents(currSuggestions);
		
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		this.add(suggestionsScroll);
		this.add(buttonsPanel);
		
		this.setLocationRelativeTo(null);
		this.pack();
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
	}
	
	private void createComponents(Set<String> currSuggestions){
		buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		useButton = new JButton("Use Suggestion");
		useButton.setEnabled(false);
		useButton.addActionListener(useListener);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(cancelListener);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(useButton);
		
		suggestions = new JList<String>();
		suggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] suggestionsArr = currSuggestions.toArray(new String[0]);
		suggestions.setListData(suggestionsArr);
		suggestions.addListSelectionListener(listListener);
		
		suggestionsScroll = new JScrollPane(suggestions);
		
	}
	
	private ListSelectionListener listListener = new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			useButton.setEnabled(true);			
		}
		
	};
	
	private ActionListener useListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			batchState.setValue(errorCell, suggestions.getSelectedValue());
			batchState.getErrors().remove(errorCell);
			SuggestionDialog.this.dispose();
		}
		
	};
	
	private ActionListener cancelListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SuggestionDialog.this.dispose();
			
		}
		
	};
	
	
	
	
}