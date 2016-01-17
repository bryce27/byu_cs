package client.views.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import shared.communication.GetProjectsParams;
import shared.communication.GetProjectsResult;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;
import shared.model.User;
import client.communication.ClientCommunicator;
import client.views.InfoManager;
import client.views.MainFrame;

@SuppressWarnings("serial")
public class LogInDialog extends JDialog{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private ClientCommunicator communicator = ClientCommunicator.getCommunicator();
	
	private JPanel userpwPanel;
	private JPanel userPanel;
	private JPanel passwordPanel;
	private JPanel buttonsPanel;
	
	public LogInDialog(){
		super(null, "Login to Indexer", Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		createComponents();
		
		this.add(Box.createRigidArea(new Dimension(0, 5)));
		this.add(userPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(passwordPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(buttonsPanel);
		
		this.pack();
		setLocationRelativeTo(null);
	}
	
	private void createComponents(){
		userPanel = new JPanel(new BorderLayout());
		JLabel usernameLabel = new JLabel("Username: ");
		usernameField = new JTextField(20);
		userPanel.add(usernameLabel, BorderLayout.WEST);
		userPanel.add(usernameField, BorderLayout.CENTER);
		
		passwordPanel = new JPanel(new BorderLayout());
		JLabel passwordLabel = new JLabel("Passowrd: ");
		passwordField = new JPasswordField(20);
		passwordPanel.add(passwordLabel, BorderLayout.WEST);
		passwordPanel.add(passwordField, BorderLayout.CENTER);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(loginListener);
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(exitListener);
		buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.add(loginButton);
		buttonsPanel.add(exitButton);
	}
	
	private ActionListener loginListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			ValidateUserParams input = new ValidateUserParams(usernameField.getText(), new String(passwordField.getPassword()));
			ValidateUserResult output = communicator.validateUser(input);
			
			if(output != null && output.isApproved()){
				InfoManager infoManager = InfoManager.getInfo();
				User user = output.getUser();
				infoManager.setUsername(user.getUsername());
				infoManager.setPassword(user.getPassword());
				infoManager.setImageID(user.getCurrentbatch_id());
				String message = "Welcome, " + user.getFirstname() + " " + user.getLastname() + 
						" .\n You have indexed " + user.getIndexedrecords() + " records.";
				JOptionPane.showMessageDialog(LogInDialog.this, message);
				MainFrame indexer = new MainFrame("Record Indexer", user.getUsername(), user.getPassword());
				indexer.setVisible(true);
				LogInDialog.this.dispose();
				
			}
			else{
				JOptionPane.showMessageDialog(LogInDialog.this, 
						"Invalid username and/or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
			}

		}
		
	};
	
	private ActionListener exitListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	};
	
	
}