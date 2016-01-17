package client.views.dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ImageDialog extends JDialog{
	public ImageDialog(Image image, String projectTitle){
		super(null, "Sample image from " + projectTitle, Dialog.ModalityType.APPLICATION_MODAL);
		JLabel imageDisplayer = new JLabel(new ImageIcon(image));
		JButton okButton = new JButton("OK");
		this.setResizable(true);
	    this.add(imageDisplayer, BorderLayout.CENTER);
	    this.add(okButton, BorderLayout.SOUTH);
	    this.pack();
	    setLocationRelativeTo(null);
	}
}