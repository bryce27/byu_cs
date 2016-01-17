package client.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.views.states.BatchStateListener;
import client.views.states.CurrentUserState;
import client.views.states.BatchState;
import client.views.dialogs.DownloadBatchDialog;
import client.views.dialogs.LogInDialog;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	private BatchState batchState;
	
	private String username;
	private String password;
	private JMenuItem downloadbatchMenu;
	private JMenuItem logoutMenu;
	private JMenuItem exitMenu;
	private ButtonsPanel buttonsPanel;
	private DisplayComponent display;
	private EntryPanel entryPanel;
	private HelpPanel helpPanel;
	private JSplitPane bottomPanel;
	private JSplitPane centerPanel;
	private InfoManager info = InfoManager.getInfo();
	
	public MainFrame(String title, String usernamein, String passwordin){
		super(title);
		batchState = new BatchState();
		batchState.addBatchStateListener(bsl);
		username = usernamein;
		password = passwordin;
		
		setPreferredSize(new Dimension(900, 700));
		setSize(new Dimension(900, 700));
		
		createComponents();
		createMenu();
		
		buttonsPanel.setMainframe(this);
		buttonsPanel.setDisplay(display);
		buttonsPanel.setBatchState(batchState);
		
		display.setBatchState(batchState);
		
		entryPanel.setBatchState(batchState);
		
		helpPanel.setBatchState(batchState);
		
		pack();
		setLocationRelativeTo(null);
		
		CurrentUserState savedUserState = new CurrentUserState();
		XStream xstream = new XStream(new DomDriver());
		
		try{
			FileInputStream savedStateFile = new FileInputStream(username + ".xml");
			savedUserState = (CurrentUserState) xstream.fromXML(savedStateFile);
			setCurrentUserState(savedUserState);
		} 
		catch(FileNotFoundException e){
			System.out.println("file doesn't exist");
		}
		
	}
	
	private void createComponents(){
		
		addWindowListener(windowAdapter);
		
		buttonsPanel = new ButtonsPanel();
		display = new DisplayComponent();
		entryPanel = new EntryPanel();
		helpPanel = new HelpPanel();
		
		bottomPanel = new JSplitPane();
		bottomPanel.setContinuousLayout(true);
		bottomPanel.setOneTouchExpandable(true);
		
		bottomPanel.setLeftComponent(entryPanel);
		bottomPanel.setRightComponent(helpPanel);
		
		bottomPanel.setResizeWeight(0.5);
		
		centerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, display, bottomPanel);
		
		centerPanel.setContinuousLayout(true);
		centerPanel.setOneTouchExpandable(true);
		
		centerPanel.setResizeWeight(0.8);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.add(buttonsPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		
		this.add(basePanel);
	}
	
	private void createMenu(){
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		downloadbatchMenu = new JMenuItem("Download Batch");
		downloadbatchMenu.addActionListener(downloadListener);
		if(info.getImageID() != -1){
			downloadbatchMenu.setEnabled(false); //user already has an image checked out
		}
		else{
			downloadbatchMenu.setEnabled(true);
		}
		menu.add(downloadbatchMenu);
		logoutMenu = new JMenuItem("Log Out");
		logoutMenu.addActionListener(logoutListener);
		menu.add(logoutMenu);
		exitMenu = new JMenuItem("Exit");
		exitMenu.addActionListener(exitListener);
		menu.add(exitMenu);
	}
	
	/**
	 * saves the current state of everything user has done
	 */
	
	public void saveCurrentUserState(){
		CurrentUserState currState = new CurrentUserState();
		currState.setBatchOutput(batchState.getBatchOutput());
		currState.setCurrFields(batchState.getFields());
		currState.setCurrProject(batchState.getProject());
		currState.setCurrScale(display.getScale());
		currState.setCurrSelectedCell(batchState.getSelectedCell());
		currState.setErrorCells(batchState.getErrors());
		
		currState.setHighlight(display.isToggleOn());
		currState.setInverted(display.isInverted());
		
		currState.setCurrWindowPos(getLocationOnScreen());
		currState.setCurrWindowSize(getSize());
		
		currState.setHorizontalDiv(bottomPanel.getDividerLocation());
		currState.setVertivalDiv(centerPanel.getDividerLocation());
		
		currState.setOriginX(display.getW_originX());
		currState.setOriginY(display.getW_originY());
		
		currState.setValues(batchState.getValues());
		
		//put to xstream
		XStream xstream = new XStream(new DomDriver());
		try {
		
			xstream.toXML(currState, new FileOutputStream(new File(info.getUsername() + ".xml")));
			System.out.println("current state is saved!");
		} catch (FileNotFoundException e) {
			System.out.println("file not found/ couldn't go to xml");
			e.printStackTrace();
		}
	}
	
	/**
	 * restoring the state that the user already has
	 * @param savedState
	 */
	
	private void setCurrentUserState(CurrentUserState savedState){
		
		if(savedState.getBatchOutput() != null){
			
			batchState.setImageID(info.getImageID());
			batchState.setErrors(savedState.getErrorCells());
			batchState.setFields(savedState.getCurrFields());
			batchState.setBatchOutput(savedState.getBatchOutput());
			batchState.setProject(savedState.getCurrProject());
			
			display.setInverted(savedState.isInverted());
			display.setToggleOn(savedState.isHighlight());
			batchState.initDownloadBatch();

			batchState.setSelectedCell(savedState.getCurrSelectedCell());
			batchState.setValues(savedState.getValues());
			
			display.setScale(savedState.getCurrScale());
			display.setW_originX(savedState.getOriginX());
			display.setW_originY(savedState.getOriginY());
			
			buttonsPanel.toggleButtons(true);
			downloadbatchMenu.setEnabled(false);
			
		}
		else{
			buttonsPanel.toggleButtons(false);
			downloadbatchMenu.setEnabled(true);
		}
		this.setLocation(savedState.getCurrWindowPos());
		this.setSize(savedState.getCurrWindowSize());
		bottomPanel.setDividerLocation(savedState.getHorizontalDiv());
		centerPanel.setDividerLocation(savedState.getVertivalDiv());
		
	}
	
	private ActionListener downloadListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			DownloadBatchDialog downloadDialog = new DownloadBatchDialog();
			downloadDialog.setBatchState(batchState);
			downloadDialog.setVisible(true);
			
		}
		
	};
	
	private ActionListener logoutListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveCurrentUserState();
			System.out.println("logged out");
			LogInDialog loginDialog = new LogInDialog();
			MainFrame.this.dispose();
			loginDialog.setVisible(true);
			
		}
		
	};
	
	private ActionListener exitListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			saveCurrentUserState();
			System.exit(0);
			
		}
		
	};
	
	private BatchStateListener bsl = new BatchStateListener() {

		@Override
		public void batchDownloaded(boolean downloaded) {
			downloadbatchMenu.setEnabled(false);
		}

		@Override
		public void batchSubmitted(boolean submitted) {
			downloadbatchMenu.setEnabled(true);
			MainFrame.this.revalidate();
			
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			
		}

		@Override
		public void valueChanged(Cell cell, String newValue) {
			
		}
		
	};
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
        	saveCurrentUserState();
            System.exit(0);
        }
	};
}