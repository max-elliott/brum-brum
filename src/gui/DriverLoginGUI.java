package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.CustomerClient;
import clientside.DriverClient;
import communication.Job;
import communication.Message;

/**
 * This is the log in page for drivers. An email address and password is entered and checked against
 * the driver database table.
 * 
 * Users cannot create a new driver account, as only genuine company drivers are allowed to use the system.
 * Driver accounts are therefore already created in the system.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-11
 */

public class DriverLoginGUI extends JPanel {
	BrumBrumGUI frame;

	private static final long serialVersionUID = 1L;

	public DriverLoginGUI(BrumBrumGUI frame) {
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
		name.setBounds(105, 150, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 16);
		name.setFont(brum);
		add(name);


		//set font for the labels and buttons
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//enter email JLabel
		JLabel enterEmail = new JLabel("Enter Email");
		enterEmail.setBounds(112, 190, 200, 30);
		enterEmail.setFont(font);
		enterEmail.grabFocus();
		add(enterEmail);
		

		//JTextField for user to enter email
		JTextField drEmail = new JTextField();
		drEmail.setBounds(50, 220, 200, 30);
		add(drEmail);

		//enter password JLabel
		JLabel enterPassword = new JLabel("Enter Password");
		enterPassword.setBounds(100, 260, 200, 30);
		enterPassword.setFont(font);
		add(enterPassword);

		//JPasswordField for user to enter password
		JTextField drPassword = new JPasswordField();
		drPassword.setBounds(50, 290, 200, 30);
		add(drPassword);


		//buttons
		Color buttonCol = new Color(200, 200, 200);

		//create login button
		JButton login = new JButton("Login");
		login.setBounds(100, 340, 100, 50);
		login.setFont(font);
		login.setBackground(buttonCol);
		login.setBorder(null);
		add(login);

		//create back button
		JButton back = new JButton("Back");
		back.setBounds(120, 400, 60, 30);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setBorder(null);
		add(back);


		//login button action listener
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				String username = drEmail.getText();
				String password = drPassword.getText();
				
				if(username.equals("") || password.equals("")) {
					
					JOptionPane.showMessageDialog(frame.Cards, "Both username and password can not be empty!");
					return;
				}
				else if (password.equals("") || password.length() < 8 || password.length() > 12) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "Password must be 8 to 12 characters in length");
					
				} 

				frame.setClient(new DriverClient(frame.getIP(), frame.getPort()));
				
				if(frame.getClient().isValid() && frame.getClient().login(username, password, "Driver")) {
					
					DriverClient dClient = (DriverClient) frame.getClient();
					
					Message message = dClient.checkStatus();
					String id = message.getId();
//					System.out.println(id);
					Job job = (Job) message.getData();
					System.out.println("DriverLoginGUI -> FROM: " + job.getFrom());
					System.out.println("DriverLoginGUI -> TO: " + job.getTo());
					if(id.equals("CURRENTLY ACCEPTED")){
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						BrumBrumGUI.acceptedJobDr.setCurrentJobGUI(job);
						BrumBrumGUI.acceptedJobDr.setLabelText();
						cardLayout.show(frame.Cards, "acceptedJobDr");
						frame.Cards.revalidate();
						frame.Cards.repaint();
//						System.out.println("CURRENTLY ACCEPTED");
					}
					else if(id.equals("CURRENTLY ONGOING")){
//						System.out.println("DriverLoginGUI - > currently ongoing check");
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						BrumBrumGUI.runningJobDr.setCurrentJobGUI(job);
						BrumBrumGUI.runningJobDr.setLabelText();
						cardLayout.show(frame.Cards, "runningJobDr");
						frame.Cards.revalidate();
						frame.Cards.repaint();
						System.out.println("CURRENTLY ONGOING");
					}
					else if(id.equals("CURRENTLY RATING")){
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						BrumBrumGUI.finishedJobDr.setFinishedJobGUI(job);
						BrumBrumGUI.acceptedJobDr.setCurrentJobGUI(job);
						BrumBrumGUI.acceptedJobDr.setLabelText();
						cardLayout.show(frame.Cards, "finishedJobDr");
						BrumBrumGUI.finishedJobDr.setLabelText();
						
						frame.Cards.revalidate();
						frame.Cards.repaint();
						System.out.println("CURRENTLY RATING");
					}
					else if(id.equals("NO CURRENT RIDES")){
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "drMain");		
						frame.Cards.revalidate();
						frame.Cards.repaint();
						System.out.println("NO CURRENT RIDES");
					}
					else{
						System.out.println("DriverLoginGUI: Something went wrong.");
					}
					
					
				} else {
					if (!frame.getClient().isValid()) {
						JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nPlease try again later");
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "main");		
						frame.Cards.revalidate();
						frame.Cards.repaint();
					} else {
						JOptionPane.showMessageDialog(frame.Cards, "Login failed"); 
					}
					frame.setClient(null);
					return;
				}
			}
		});

		
		//back button action listener - takes user back to main page to select login
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				
				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "main");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});


	}
}