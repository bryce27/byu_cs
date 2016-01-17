package client.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import shared.model.Field;
import shared.model.Project;
import client.views.states.BatchState;
import client.views.states.BatchStateListener;

public class DisplayComponent extends JComponent{
	
	private BufferedImage image;
	private Rectangle2D highlightRect;
	
	private int w_originX;
	private int w_originY;
	private double scale;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	
	private int currSelectedRow;
	private int currSelectedCol;
	private boolean toggleOn;
	private boolean inverted;
	
	
	private DrawingImage imagetoDraw;
	private DrawingHighlight highlighttoDraw;
	
	private BatchState batchState;
	
	public DisplayComponent(){
		
		w_originX = this.getWidth() / 2; 
		w_originY = this.getHeight() / 2;
		scale = 1.0;
		
		toggleOn = true;
		inverted = false;
		
		initDrag();
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.repaint();
		
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
		batchState.addBatchStateListener(batchStateListener);
	}
	
	private void initDrag(){
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
	
	
	
	public int getW_originX() {
		return w_originX;
	}

	public void setW_originX(int w_originX) {
		this.w_originX = w_originX;
	}

	public int getW_originY() {
		return w_originY;
	}

	public void setW_originY(int w_originY) {
		this.w_originY = w_originY;
	}

	public boolean isToggleOn() {
		return toggleOn;
	}

	public void setToggleOn(boolean toggleOn) {
		this.toggleOn = toggleOn;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double newScale){
		
		if(newScale < 0.3){
			scale = 0.3;
		}
		else if(newScale > 3.0){
			scale = 3.0;
		}
		else{
			scale = newScale;
		}
		
		this.repaint();
	}
	
	public void zoomin(){
		scale += 0.05;
		setScale(scale);
	}
	
	public void zoomout(){
		scale -= 0.05; 
		setScale(scale);
	}
	
	/**
	 * inverts the image user is currently working on
	 * @param initial -- if this invert is restoring the user state 
	 * 				or if it's user pressing invert button
	 */
	
	public void invert(boolean initial){
		if(!initial){
			if(inverted == true){
				inverted = false;
			}
			else{
				inverted = true;
			}
		}
		
		RescaleOp rescaleop = new RescaleOp(-1.0f, 255f, null);
		BufferedImage invertedImage = rescaleop.filter(image, null);
		
		image = invertedImage;
		imagetoDraw = new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight()));
		this.repaint();
	}
	
	/**
	 * allows the user to toggle highlights
	 */
	
	public void toggleHighlight(){
		if(toggleOn == true){
			toggleOn = false;
		}
		else{
			toggleOn = true;
		}
		this.repaint();
	}
	
	/**
	 * updates where the highlight is currently at
	 */
	
	private void updateHighlight(){
		double fieldX = (double) batchState.getFields().get(currSelectedCol - 1).getXcoord();
		double fieldY = (double) batchState.getProject().getFirstycoord() + (batchState.getProject().getRecordheight() * (currSelectedRow));
		double fieldW = (double) batchState.getFields().get(currSelectedCol - 1).getWidth();
		double fieldH = (double) batchState.getProject().getRecordheight();
		
		highlighttoDraw = new DrawingHighlight(new Rectangle2D.Double(fieldX, fieldY, fieldW, fieldH), new Color(100, 149, 237, 128));
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		
		g2.translate(getWidth() / 2, getHeight() / 2);
		g2.scale(scale, scale);
		g2.translate(-(getWidth() / 2), -(getHeight() / 2));
		g2.translate(-w_originX, -w_originY);
		
		if(imagetoDraw != null){
			imagetoDraw.draw(g2);
		}

		if(highlighttoDraw != null && toggleOn == true){
			highlighttoDraw.draw(g2);
		}
	}
	
	private void drawBackground(Graphics2D g2){
		g2.setColor(getBackground());
		g2.fillRect(0,  0,  getWidth(), getHeight());
		
		this.setBackground(new Color(105, 105, 105));
	}
	
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
	
		//mouse click to change highlighted cell
		@Override
		public void mouseClicked(MouseEvent e){
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(DisplayComponent.this.getWidth() / 2, DisplayComponent.this.getHeight() / 2);
			transform.scale(scale, scale);
			transform.translate(-(DisplayComponent.this.getWidth() / 2), -(DisplayComponent.this.getHeight() / 2));
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			ArrayList<Field> tempFields = batchState.getFields();
			Project tempProject = batchState.getProject();
			
			int minfieldX = tempFields.get(0).getXcoord();
			int minrecY = tempProject.getFirstycoord(); //+ tempProject.getRecordheight();
			int maxfieldX = tempFields.get(tempFields.size() - 1).getXcoord() + tempFields.get(tempFields.size() - 1).getWidth();
			int maxrecY = minrecY + (tempProject.getRecordheight() * tempProject.getRecordsperimage());
					
			
			if(image != null && w_X > minfieldX && w_X < maxfieldX && w_Y > minrecY && w_Y < maxrecY){
				currSelectedCol = 0;
				
				currSelectedRow = (w_Y - tempProject.getFirstycoord())/tempProject.getRecordheight();
				
				for(Field field : tempFields){
					if(w_X > field.getXcoord()){
						currSelectedCol++;
					}
					else{
						break;
					}
				}
				
				batchState.setSelectedCell(new Cell(currSelectedRow, currSelectedCol));
				
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(DisplayComponent.this.getWidth() / 2, DisplayComponent.this.getHeight() / 2);
			transform.scale(scale, scale);
			transform.translate(-(DisplayComponent.this.getWidth() / 2), -(DisplayComponent.this.getHeight() / 2));
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			
			//if (w_Pt != null && imagetoDraw.contains(g2, w_X, w_Y)) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			//}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.translate(DisplayComponent.this.getWidth() / 2, DisplayComponent.this.getHeight() / 2);
				transform.scale(scale, scale);
				transform.translate(-(DisplayComponent.this.getWidth() / 2), -(DisplayComponent.this.getHeight() / 2));
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				
				DisplayComponent.this.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			if(e.getWheelRotation() > 0){
				scale -= 0.05;
			}
			else if(e.getWheelRotation() < 0){
				scale += 0.05;
			}
			
			setScale(scale);
			
			return;
			
		}
	};
	
	/**
	 * class to draw the image
	 */
	
	private class DrawingImage{
		private Image image;
		private Rectangle2D rectangle;
		public DrawingImage(Image image, Rectangle2D rectangle){
			this.image = image;
			this.rectangle = rectangle;
		}

		public void draw(Graphics2D g2d){
			
			g2d.drawImage(image, (int)rectangle.getMinX(), (int)rectangle.getMinY(), (int)rectangle.getMaxX(),
					(int)rectangle.getMaxY(), 0, 0, image.getWidth(null), 
					image.getHeight(null), null);
		}
		
//		public boolean contains(Graphics2D g2, double x, double y) {
//			return rectangle.contains(x, y);
//		}
		
	};
	
	/**
	 * class to draw the highlight
	 */
	
	private class DrawingHighlight{
		private Rectangle2D rectangle;
		private Color color;
		public DrawingHighlight(Rectangle2D rectangle, Color color){
			this.rectangle = rectangle;
			this.color = color;
		}
		
		public void draw(Graphics2D g2){
			
			g2.setColor(color);
			g2.fill(rectangle);
		}
		
	};
	
	private BatchStateListener batchStateListener = new BatchStateListener() {

		@Override
		public void batchDownloaded(boolean downloaded) {
			if(downloaded == true){
				image = batchState.getImage();
				double fieldX = (double) batchState.getFields().get(0).getXcoord();
				double firstY = (double) batchState.getProject().getFirstycoord();
				double fieldW = (double) batchState.getFields().get(0).getWidth();
				double recordH = (double) batchState.getProject().getRecordheight();
				
				if(inverted){
					DisplayComponent.this.invert(true);
				}
				if(!toggleOn){
					DisplayComponent.this.toggleHighlight();
				}
				
				highlightRect = new Rectangle2D.Double(fieldX, firstY, fieldW, recordH);
				
				imagetoDraw = new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null)));
				highlighttoDraw = new DrawingHighlight(highlightRect, new Color(100, 149, 237, 128));
				setScale(0.5);
				DisplayComponent.this.repaint();
			}
		}

		@Override
		public void batchSubmitted(boolean submitted) {
			if(submitted == true){
				scale = 1.0;
				
				initDrag();
				
				imagetoDraw = null;
				highlighttoDraw = null;
				
				toggleOn = true;
				inverted = false;
				DisplayComponent.this.repaint();
			}
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			if(newSelectedCell.getField() != 0){
				currSelectedRow = newSelectedCell.getRecord();
				currSelectedCol = newSelectedCell.getField();
				DisplayComponent.this.updateHighlight();
			}
			
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			
		}

	};
}