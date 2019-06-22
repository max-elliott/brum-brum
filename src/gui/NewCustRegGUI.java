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

import clientside.CustomerClient;

/**
 * This page allows new customers to register their details and create a new account.
 * 
 * After successfully creating an account, the customer's details are added to the customer
 * database table and the customer returns to the customer login page of the GUI to log in
 * using their now registered email and password.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-04
 * @version 2017-03-11
 */

public class NewCustRegGUI extends JPanel {
	BrumBrumGUI frame;

	private static final long serialVersionUID = 1L;

	public NewCustRegGUI(BrumBrumGUI frame) {
		this.frame = frame;

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);


		//add the logo
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
		name.setBounds(112, 70, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 15);
		name.setFont(brum);
		add(name);


		/**
		 * Customer details
		 */
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//firstname
		JLabel enterFirstname = new JLabel("Firstname");
		enterFirstname.setBounds(120, 100, 200, 30);
		enterFirstname.setFont(font);
		add(enterFirstname);

		JTextField usrFirstname = new JTextField();
		usrFirstname.setBounds(50, 130, 200, 30);
		add(usrFirstname);

		//lastname
		JLabel enterLastname = new JLabel("Lastname");
		enterLastname.setBounds(120, 160, 200, 30);
		enterLastname.setFont(font);
		add(enterLastname);

		JTextField usrLastname = new JTextField();
		usrLastname.setBounds(50, 190, 200, 30);
		add(usrLastname);

		//email
		JLabel enterEmail = new JLabel("Email");
		enterEmail.setBounds(130, 220, 200, 30);
		enterEmail.setFont(font);
		add(enterEmail);

		JTextField usrEmail = new JTextField();
		usrEmail.setBounds(50, 250, 200, 30);
		add(usrEmail);

		//telephone number
		JLabel enterPhone = new JLabel("Telephone Number");
		enterPhone.setBounds(95, 280, 200, 30);
		enterPhone.setFont(font);
		add(enterPhone);

		JTextField usrPhone = new JTextField();
		usrPhone.setBounds(50, 310, 200, 30);
		add(usrPhone);

		//password
		JLabel enterPassword = new JLabel("Password");
		enterPassword.setBounds(120, 340, 200, 30);
		enterPassword.setFont(font);
		add(enterPassword);

		JTextField usrPassword = new JPasswordField();
		usrPassword.setBounds(50, 370, 200, 30);
		add(usrPassword);


		//create button colour
		Color buttonCol = new Color(200, 200, 200);

		//signup button
		JButton signUp = new JButton("Signup");
		signUp.setBounds(40, 410, 100, 50);
		signUp.setFont(font);
		signUp.setBackground(buttonCol);
		signUp.setBorder(null);
		add(signUp);

		//back button
		JButton back = new JButton("Back");
		back.setBounds(160, 410, 100, 50);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setBorder(null);
		add(back);


		/**
		 * Action listeners
		 */
		//sign up button action listener
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
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
						JOptionPane.showMessageDialog(message, "Password must be 8 to 12 characters in length");
						
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
						frame.setClient(new CustomerClient(frame.getIP(), frame.getPort()));
						
						if (frame.getClient().isValid()) {
							String message = frame.getClient().createAccount(usrFirstname.getText(), usrLastname.getText(), 
									usrEmail.getText(), usrPhone.getText(), usrPassword.getText());
							
							if(message.equals("SUCCESS")) {
								frame.setClient(null);
								JOptionPane.showMessageDialog(null, "Registration successful");
								CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
								cardLayout.show(frame.Cards, "custLogin");
								frame.Cards.revalidate();
								frame.Cards.repaint();
							} else {
								frame.setClient(null);
							}
						} else {
							JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nPlease try again later");
							CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							cardLayout.show(frame.Cards, "main");		
							frame.Cards.revalidate();
							frame.Cards.repaint();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please complete all fields correctly");
				}
//				
//				} else { DISPLAY JLABEL WITH THE String message ON.
//					if (registration not successful) {
//						JOptionPane.showMessageDialog(null, "Account already exists");
//				
//						JOptionPane.showMessageDialog(null, "Registration failed");
//					}
//				
				//}

			}
		});
		

		//back button action listener
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {

				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "custLogin");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});

	}


}
