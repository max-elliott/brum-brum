package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.Client;
import clientside.Customer;
import clientside.Driver;

/**
 * The settings page will be the same for customers and drivers and will have several options for the user - 
 * change their details, view their ride history, log out or request help by contacting the company.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-04
 * @version 2017-03-19
 */

public class SettingsGUI extends JPanel {
	BrumBrumGUI frame;
	
	private static final long serialVersionUID = 1L;
	
	public SettingsGUI(BrumBrumGUI frame) {
		this.frame = frame;

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);


		//add the company logo
		try {
			URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image scaled = bufImg.getScaledInstance(110, 115, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel(new ImageIcon(scaled));

			logo.setBounds(95, 20, 110, 115);
			logo.setBorder(null);
			add(logo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrum");
		name.setBounds(105, 140, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 15);
		name.setFont(brum);
		add(name);


		//option buttons
		Font font = new Font("SansSerif", Font.PLAIN, 13);
		Color buttonCol = new Color(200, 200, 200);

		//edit button
		JButton edit = new JButton("Personal Details");
		edit.setBounds(85, 170, 130, 40);
		edit.setFont(font);
		edit.setBackground(buttonCol);
		edit.setBorder(null);
		add(edit);

		//contact button
		JButton contact = new JButton("Contact Us");
		contact.setBounds(85, 220, 130, 40);
		contact.setFont(font);
		contact.setBackground(buttonCol);
		contact.setBorder(null);
		add(contact);

		//request ride history button
		JButton hist = new JButton("Ride History");
		hist.setBounds(85, 270, 130, 40);
		hist.setFont(font);
		hist.setBackground(buttonCol);
		hist.setBorder(null);
		add(hist);	
		
		//get user rating button
		JButton getMyRating = new JButton("Get My Rating");
		getMyRating.setBounds(85, 320, 130, 40);
		getMyRating.setFont(font);
		getMyRating.setBackground(buttonCol);
		getMyRating.setBorder(null);
		add(getMyRating);

		//logout button
		JButton logout = new JButton("Logout");
		logout.setBounds(85, 370, 130, 40);
		logout.setFont(font);
		logout.setBackground(buttonCol);
		logout.setBorder(null);
		add(logout);

		//back button to take user back to main customer page
		JButton back = new JButton("Back");
		back.setBounds(120, 420, 60, 30);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setForeground(Color.BLACK);
		back.setBorder(null);
		add(back);


		//edit button action listener
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				frame.refreshEditUsr();
				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "editUsr");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});


		//contact button action listener
		contact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "contact");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});


		//ride history button action listener
		hist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) {
				frame.refreshHistory();
				if (!frame.getClient().isValid()) {
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.setClient(null);
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} else {
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "history");
					frame.Cards.revalidate();
					frame.Cards.repaint();
				}
			}
		});
		
		
		//getMyRating button action listener - creates a pop-up dialogue with user rating
		getMyRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e4) {
				String rating = frame.getClient().getRating();
				if (frame.getClient().isValid()) {
					if(rating == "No Rating") {
						JOptionPane.showMessageDialog(frame, "No rating");
					}	
					else {
						JOptionPane.showMessageDialog(frame, "Your rating is " + rating);
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
		});
		
		
		//logout button action listener
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e5) {
				boolean result = frame.getClient().logout();
				if (!frame.getClient().isValid()) { 
					JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nAutomatically logged out");
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.setClient(null);
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} 
				else if (result) {
					frame.setClient(null);
//					logout.equals(true);
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.Cards.revalidate();
					frame.Cards.repaint();
				}
			}
		});


		//back button action listener
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {

				if(frame.getClient().getUser().getClass().equals(Driver.class)) {
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "drMain");
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} 

				if(frame.getClient().getUser().getClass().equals(Customer.class)) {
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "custMain");
					frame.Cards.revalidate();
					frame.Cards.repaint();
				}
			}
		});
	}
}
