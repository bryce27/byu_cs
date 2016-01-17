package client.views.states;

import client.views.Cell;

public interface BatchStateListener {
	
	void batchDownloaded(boolean downloaded); //generate image, entries, etc
	void batchSubmitted(boolean submitted); //clear everything out
	void selectedCellChanged(Cell newSelectedCell);
 	void valueChanged(Cell cell, String newValue); 
	
}