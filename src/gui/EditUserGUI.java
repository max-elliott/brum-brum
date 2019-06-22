package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.Client;
import clientside.Customer;
import clientside.Driver;
import clientside.User;

/**
 * This page allows users to edit their personal details - name, email, telephone number or password.
 * 
 * Any changes will update the user information in the relevant user database table. This page will be 
 * the same for drivers and customers.
 * 
 * Any JTextFields left blank will not be updated in the database.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-11
 */

public class EditUserGUI extends JPanel {
	BrumBrumGUI frame;

	private static final long serialVersionUID = 1L;

	public EditUserGUI(BrumBrumGUI frame) {
		this.frame = frame;

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);


		//add the company logo
		try {
			URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image scaled = bufImg.getScaledInstance(50, 60, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel(new ImageIcon(scaled));

			logo.setBounds(125, 10, 50, 60);
			add(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}


		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrum");
		name.setBounds(117, 70, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 13);
		name.setFont(brum);
		add(name);


		//JTextFields to for user to edit personal details
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//JLabel Firstname
		JLabel enterFirstname = new JLabel("Edit first name");
		enterFirstname.setBounds(110, 100, 200, 30);
		enterFirstname.setFont(font);
		add(enterFirstname);
		

		//JTextField for user to enter Fistname
		JTextField usrFirstname = new JTextField();
		usrFirstname.setBounds(50, 130, 200, 30);
		

		//JLabel Lastname
		JLabel enterLastname = new JLabel("Edit surname");
		enterLastname.setBounds(110, 160, 200, 30);
		enterLastname.setFont(font);
		add(enterLastname);

		//JTextField for user to enter Lastname
		JTextField usrLastname = new JTextField();
		usrLastname.setBounds(50, 190, 200, 30);
		

		//JLabel Email
		JLabel enterEmail = new JLabel("Edit email");
		enterEmail.setBounds(120, 220, 200, 30);
		enterEmail.setFont(font);
		add(enterEmail);

		//JTextField for user to enter Email address
		JTextField usrEmail = new JTextField();
		usrEmail.setBounds(50, 250, 200, 30);
		

		//JLabel Telephone number
		JLabel enterPhone = new JLabel("Edit telephone number");
		enterPhone.setBounds(80, 280, 200, 30);
		enterPhone.setFont(font);
		add(enterPhone);

		//JTextField for user to enter Telephone number
		JTextField usrPhone = new JTextField();
		usrPhone.setBounds(50, 310, 200, 30);
		
		//JLabel Password
		JLabel enterPassword = new JLabel("Please enter your password");
		enterPassword.setBounds(60, 340, 200, 30);
		enterPassword.setFont(font);
		add(enterPassword);
		
		//JPasswordField for user to enter their password
		JTextField usrPassword = new JPasswordField();
		usrPassword.setBounds(50, 370, 200, 30);
		
//		If the client is not null then pre-fill all of the text fields!
		if (frame.getClient() != null) {
			User user = frame.getClient().getUser();
			usrFirstname.setText(user.getFirstName());
			usrLastname.setText(user.getLastName());
			usrPhone.setText(user.getTelNumber());
			usrEmail.setText(user.getEmail());
			usrPassword.setText("");
		}
		
		add(usrEmail);
		add(usrPhone);
		add(usrLastname);
		add(usrFirstname);
		add(usrPassword);


		//buttons
		Color buttonCol = new Color(200, 200, 200);

		//JButton to save any changes to database - if no changes have been entered the database
		//will not be changed
		JButton save = new JButton("Save");
		save.setBounds(40, 410, 100, 50);
		save.setFont(font);
		save.setBackground(buttonCol);
		save.setBorder(null);
		add(save);

		//back button to take user back to main customer or driver page
		JButton back = new JButton("Back");
		back.setBounds(160, 410, 100, 50);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setForeground(Color.BLACK);
		back.setBorder(null);
		add(back);


		//save button action listener
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher emailMatcher = validEmailRegex.matcher(usrEmail.getText());
				
				if (!usrFirstname.getText().equals("")    &&
						!usrLastname.getText().equals("") &&
						usrFirstname.getText().matches("^[A-Za-z ,.'-]+") != false &&
						usrLastname.getText().matches("^[A-Za-z ,.'-]+") != false &&
						!usrEmail.getText().equals("")    &&
						!usrPhone.getText().equals("")    &&
						usrPhone.getText().matches("^[0-9]+$") != false) {
					
					if (usrPassword.getText().equals("") || usrPassword.getText().length() < 8 || usrPassword.getText().length() > 12) {
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "Please verify your password before continuing");
						
					} else if (usrFirstname.getText().contains(" ") || usrLastname.getText().contains(" ")) {
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "Your name can not contain whitespace");
						
					} else if (!emailMatcher.find()) {
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "Please enter a valid email address");
						
					} else if (usrPhone.getText().length() < 10 || usrPhone.getText().length() > 11) {
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "Phone number must be 10 to 11 numbers in length\nNo hyphens or spaces, please");
						
					} else {
						String firstName = usrFirstname.getText();
						String lastName = usrLastname.getText();
						String email = usrEmail.getText();
						String tel_no = usrPhone.getText();
						String response = frame.getClient().editUser(firstName, lastName, email, tel_no, usrPassword.getText());
//						System.out.println("EditUserGUI.java -> " + response);
						if (!frame.getClient().isValid()) {
							JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nYour details have not been changed");
							CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							cardLayout.show(frame.Cards, "main");		
							frame.Cards.revalidate();
							frame.Cards.repaint();
						}
						else if (response.equals("SUCCESS")) {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message, "Changes saved");
//							Make sure that their locally stored information is correctly updated
							frame.getClient().getUser().setFirstName(firstName);
							frame.getClient().getUser().setLastName(lastName);
							frame.getClient().getUser().setEmail(email);
							frame.getClient().getUser().setTelNumber(tel_no);
							
//							Send the user to the correct main screen!
							if (frame.getClient().getUser().getClass().equals(Customer.class)) {
								CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
								cardLayout.show(frame.Cards, "custMain");
								frame.Cards.revalidate();
								frame.Cards.repaint();
							} 
							else if (frame.getClient().getUser().getClass().equals(Driver.class)) {
								CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
								cardLayout.show(frame.Cards, "drMain");
								frame.Cards.revalidate();
								frame.Cards.repaint();
							}
						} 
						else if (response.equals("INCORRECT PASSWORD")) {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message, "Incorrect password!");
						} 
						else if (response.equals("FAILED")) {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message, "Edit failed");
						} else if (response.equals("EMAIL IN USE")) {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message, "Email address is already in use by another user!\nPlease use a different email address");
						} 
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please complete all fields correctly");
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
