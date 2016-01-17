package client;

import java.awt.EventQueue;
import client.communication.ClientCommunicator;
import client.views.dialogs.LogInDialog;


public class MainGUI {
	public static void main(String[] args) {
	    	ClientCommunicator communicator = ClientCommunicator.getCommunicator();
			
	    	//host, port input
	    	if(args.length == 0){
	    		communicator.getHostPort("localhost", "8080");
	    	}
	    	else{
	    		communicator.getHostPort(args[0], args[1]);
	    	}
	    	
	        EventQueue.invokeLater(
	        		new Runnable() {
			           public void run()
			           {
			        	   LogInDialog login = new LogInDialog();
			        	   login.setVisible(true);
			           }
	        		}
	        );
	}
}