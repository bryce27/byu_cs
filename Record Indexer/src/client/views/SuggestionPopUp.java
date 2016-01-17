package client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import client.checker.Checker;
import client.views.dialogs.SuggestionDialog;
import client.views.states.BatchState;

public class SuggestionPopUp extends JPopupMenu{
	private JMenuItem seeMenu;
	private Cell errorCell;
	private Set<String> suggestions;
	
	private BatchState batchState;
	
	public SuggestionPopUp(){
		seeMenu = new JMenuItem("See Suggestions");
		seeMenu.addActionListener(seeListener);
		
		this.add(seeMenu);

	}
	
	/**
	 * generates suggestions if the word is misspelled
	 * @param cell -- which word to generate suggestions on
	 */
	
	public void createSuggestions(Cell cell){
		Checker spellChecker = new Checker();
		
		File dictionaryFile = new File(batchState.getFields().get(cell.getField()-1).getKnowndata());
		try {
			spellChecker.useDictionary(dictionaryFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		suggestions = spellChecker.getSuggestions(batchState.getValue(cell));
		
		errorCell = cell;
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
	}
	
	private ActionListener seeListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//show suggestions dialog
			SuggestionDialog suggestionDialog = new SuggestionDialog(suggestions, errorCell);
			suggestionDialog.setBatchState(batchState);
			suggestionDialog.setVisible(true);
			
		}
		
	};
}