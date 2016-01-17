package client.views.states;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import shared.communication.DownloadBatchResult;
import shared.model.Field;
import shared.model.Project;
import client.views.Cell;

public class CurrentUserState implements Serializable {
	/**
	 * class to store all of user's current information 
	 * saves user's progress on a record as well as
	 * user's preferred settings
	 */
	
	private String [][] values; //values user has entered
	private Cell currSelectedCell; //current selected cell
	private ArrayList<Cell> errorCells;
	private Project currProject; 
	private ArrayList<Field> currFields;
	
	private DownloadBatchResult batchOutput;
	
	private Point currWindowPos; //where window is
	private Dimension currWindowSize; //current window size
	
	private double currScale; //zoom level
	private int originX; //image origin x
	private int originY; //image origin y
	private boolean inverted; //invert setting
	private boolean highlight; //highlight setting
	
	private int horizontalDiv; //pos of horizontal split pane divider
	private int vertivalDiv; //position of vertical split pane divider
	public String[][] getValues() {
		return values;
	}
	public void setValues(String[][] values) {
		this.values = values;
	}
	public Cell getCurrSelectedCell() {
		return currSelectedCell;
	}
	public void setCurrSelectedCell(Cell currSelectedCell) {
		this.currSelectedCell = currSelectedCell;
	}
	public ArrayList<Cell> getErrorCells() {
		return errorCells;
	}
	public void setErrorCells(ArrayList<Cell> errorCells) {
		this.errorCells = errorCells;
	}
	public Project getCurrProject() {
		return currProject;
	}
	public void setCurrProject(Project currProject) {
		this.currProject = currProject;
	}
	public ArrayList<Field> getCurrFields() {
		return currFields;
	}
	public void setCurrFields(ArrayList<Field> currFields) {
		this.currFields = currFields;
	}
	
	public DownloadBatchResult getBatchOutput() {
		return batchOutput;
	}
	public void setBatchOutput(DownloadBatchResult batchOutput) {
		this.batchOutput = batchOutput;
	}
	public Point getCurrWindowPos() {
		return currWindowPos;
	}
	public void setCurrWindowPos(Point currWindowPos) {
		this.currWindowPos = currWindowPos;
	}
	public Dimension getCurrWindowSize() {
		return currWindowSize;
	}
	public void setCurrWindowSize(Dimension currWindowSize) {
		this.currWindowSize = currWindowSize;
	}
	public double getCurrScale() {
		return currScale;
	}
	public void setCurrScale(double currScale) {
		this.currScale = currScale;
	}
	public int getOriginX() {
		return originX;
	}
	public void setOriginX(int originX) {
		this.originX = originX;
	}
	public int getOriginY() {
		return originY;
	}
	public void setOriginY(int originY) {
		this.originY = originY;
	}
	public boolean isInverted() {
		return inverted;
	}
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public int getHorizontalDiv() {
		return horizontalDiv;
	}
	public void setHorizontalDiv(int horizontalDiv) {
		this.horizontalDiv = horizontalDiv;
	}
	public int getVertivalDiv() {
		return vertivalDiv;
	}
	public void setVertivalDiv(int vertivalDiv) {
		this.vertivalDiv = vertivalDiv;
	}
	
	
	
}