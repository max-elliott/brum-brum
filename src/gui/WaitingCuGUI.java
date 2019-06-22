package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This page is displayed to the customer after they have requested a ride. It is a waiting page that
 * will change to the RunningJobCuGUI page once the job has been accepted. 
 * 
 * @author Elizabeth Warner
 * @created 2017-03-16
 * @version 2017-03-16
 */

public class WaitingCuGUI extends JPanel {
	BrumBrumGUI frame;

	private static final long serialVersionUID = 1L;

	public WaitingCuGUI(BrumBrumGUI frame) {
		this.frame = frame;

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);


		//add the company logo
		try {
			URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image scaled = bufImg.getScaledInstance(100, 110, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel(new ImageIcon(scaled));

			logo.setBounds(95, 20, 110, 130);
			add(logo);

		} catch (IOException e) {
			e.printStackTrace();
		}


		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrum");
		name.setBounds(105, 150, 210, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 16);
		name.setFont(brum);
		add(name);

		//set font for labels and buttons
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//waiting JLabel
		JLabel waiting = new JLabel("Waiting for a driver");
		waiting.setBounds(90, 260, 210, 30);
		waiting.setFont(font);
		add(waiting);

		//waiting JLabel
		JLabel toAccept = new JLabel("to accept your request");
		toAccept.setBounds(80, 280, 210, 30);
		toAccept.setFont(font);
		add(toAccept);
	}
}
