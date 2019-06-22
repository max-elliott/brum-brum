package gui;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.Customer;
import clientside.CustomerClient;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * This page allows the user to write a message to the company and send it.
 * 
 * Currently the message does not actually get sent anywhere - this may be something we can implement later
 * in the project.
 * 
 * Both customers and drivers can access this same page through the settings page.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-11
 */

public class ContactGUI extends JPanel {
	BrumBrumGUI frame;
	
	private static final long serialVersionUID = 1L;
	
	public ContactGUI(BrumBrumGUI frame) {
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

			logo.setBounds(95, 20, 110, 110);
			add(logo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrum");
		name.setBounds(105, 140, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 14);
		name.setFont(brum);
		add(name);


		
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//JLabel asking user to enter message
		JLabel enterMsg = new JLabel("Please enter message");
		enterMsg.setBounds(85, 200, 150, 20);
		enterMsg.setFont(font);
		add(enterMsg);

		
		/**
		 * JTextField within JScrollPane in which user can write message
		 */	
		JTextArea usrMsg = new JTextArea();
		
		usrMsg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		usrMsg.setLineWrap(true);
		usrMsg.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(usrMsg);
		scrollPane.getVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(50, 235, 200, 100);
		add(scrollPane, BorderLayout.CENTER);

		
		/**
		 * submit and back buttons
		 */
		Color buttonCol = new Color(200, 200, 200);

		//submit message button
		JButton submit = new JButton("Submit");
		submit.setBounds(100, 350, 100, 50);
		submit.setFont(font);
		submit.setBackground(buttonCol);
		submit.setBorder(null);
		add(submit);

		//back button to take user back to settings page
		JButton back = new JButton("Back");
		back.setBounds(120, 410, 60, 30);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setBorder(null);
		add(back);


		/**
		 * Action listeners
		 */
		//submit button action listener
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				if(usrMsg.getText() != "") {
					JFrame message = new JFrame();
					
					String type = (frame.getClient().getClass().equals(CustomerClient.class)) ? "Customer" : "Driver";
//					System.out.println("ContactGUI.java -> " + frame.getClient().getClass());
					String result = frame.getClient().contact(usrMsg.getText(), type);
					if (frame.getClient().isValid()) {
						if (result.equals("SUCCESS")) {
							JOptionPane.showMessageDialog(message, "Message Submitted");
							usrMsg.setText("");
							CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							cardLayout.show(frame.Cards, "settings");
							frame.Cards.revalidate();
							frame.Cards.repaint();
						} else {
							JOptionPane.showMessageDialog(message, "Message Failed\nPlease try again later");
							usrMsg.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nPlease try again later");
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "main");	
						frame.setClient(null);
						frame.Cards.revalidate();
						frame.Cards.repaint();
					}
				}
			}
		});

		//back button action listener
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {

				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "settings");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});
	}
}
