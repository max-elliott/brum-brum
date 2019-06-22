package gui;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.CustomerClient;
import communication.Job;
import communication.Message;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This is the customer log in page. An email address and password is entered and checked against
 * the customer database table. 
 * 
 * Customers are able to create a new account with the company by selecting a 'New Customer' button
 * and completing the required details.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-11
 */


public class CustLoginGUI extends JPanel {
	BrumBrumGUI frame;
	private boolean accepted = false;
	private boolean requesting = false;
	private boolean ongoing = false;
	private boolean rating = false;

	private static final long serialVersionUID = 1L;

	public CustLoginGUI(BrumBrumGUI frame) {
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


		//set font for labels and buttons
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//enter email JLabel
		JLabel enterEmail = new JLabel("Enter Email");
		enterEmail.setBounds(117, 190, 200, 30);
		enterEmail.setFont(font);
		enterEmail.grabFocus();
		add(enterEmail);

		//JTextField for user to enter email
		JTextField cuEmail = new JTextField();
		cuEmail.setBounds(50, 220, 200, 30);
		add(cuEmail);

		//enter password JLabel
		JLabel enterPassword = new JLabel("Enter Password");
		enterPassword.setBounds(105, 260, 200, 30);
		enterPassword.setFont(font);
		add(enterPassword);

		//JPasswordField for user to enter password
		JTextField cuPassword = new JPasswordField();
		cuPassword.setBounds(50, 290, 200, 30);
		add(cuPassword);


		//set colour for buttons
		Color buttonCol = new Color(200, 200, 200);

		//create login button
		JButton login = new JButton("Login");
		login.setBounds(45, 340, 100, 50);
		login.setFont(font);
		login.setBackground(buttonCol);
		login.setBorder(null);
		add(login);

		//new customer button
		JButton newCust = new JButton("New Customer");
		newCust.setBounds(155, 340, 100, 50);
		newCust.setFont(font);
		newCust.setBackground(buttonCol);
		newCust.setBorder(null);
		add(newCust);

		//back button to take user back to main page to choose login type
		JButton back = new JButton("Back");
		back.setBounds(120, 400, 60, 30);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setBorder(null);
		add(back);


		//login button action listener
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				String username = cuEmail.getText();
				String password = cuPassword.getText();

				if(username.equals("") || password.equals("")) {

					JOptionPane.showMessageDialog(frame.Cards, "Both username and password can not be empty!");
					return;
				}
				else if (password.equals("") || password.length() < 8 || password.length() > 12) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "Password must be 8 to 12 characters in length");

				} 


				frame.setClient(new CustomerClient(frame.getIP(), frame.getPort()));
				//				System.out.println("CustLoginGUI -> frame set!");

				if(frame.getClient().isValid() && frame.getClient().login(username, password, "Customer")) {

					CustomerClient cClient = (CustomerClient) frame.getClient();
					Message message = cClient.checkStatus();
					String id = message.getId();
					//					System.out.println("CustLoginGUI -> id: " + id);

					if(id.equals("FAILED")) {
						frame.setClient(null);
						JOptionPane.showMessageDialog(frame, "Login failed");
						frame.setClient(null);
					} else {
						//						System.out.println("CustLoginGUI -> Frame is valid");
						BrumBrumGUI.getCustMain().setCustomerJob( (Job) message.getData() );

						//						*****BEGIN THREADPOOL******
						ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

						exec.scheduleAtFixedRate(new Runnable() {
							public void run() {
								try {
									Message message = cClient.checkStatus();
									String id = message.getId();
	
									if(id.equals("REQUESTING") && !requesting) {
										JOptionPane.showMessageDialog(frame, "You have an active job.");
	
										BrumBrumGUI.getCustMain().setCustomerJob( (Job) message.getData() );
										requesting = true;
										CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
										cardLayout.show(frame.Cards, "waitingCu");		
										frame.Cards.revalidate();
										frame.Cards.repaint();
	
									} else if(id.equals("ACCEPTED") && !accepted) {
										JOptionPane.showMessageDialog(frame, "You have an active job.");
	
										BrumBrumGUI.getCustMain().setCustomerJob( (Job) message.getData() );
										BrumBrumGUI.getRunningJobCu().setCurrentJobGUI((Job) message.getData());
										accepted = true;
										BrumBrumGUI.getRunningJobCu().setLabelText();
	
										JOptionPane.showMessageDialog(frame, "Someone has accepted your request");
										CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
										cardLayout.show(frame.Cards, "runningJobCu");
										frame.Cards.revalidate();
										frame.Cards.repaint();
	
									} else if(id.equals("ONGOING") && !ongoing) {
										JOptionPane.showMessageDialog(frame, "You have an active job.");
	
										BrumBrumGUI.getCustMain().setCustomerJob( (Job) message.getData() );
										ongoing = true;
										BrumBrumGUI.getOngoingJobCu().setLabelText();
	
										JOptionPane.showMessageDialog(frame, "Your ride has begun!");
										CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
										cardLayout.show(frame.Cards, "ongoingJobCu");
										frame.Cards.revalidate();
										frame.Cards.repaint();
	
									} else if(id.equals("CURRENTLY RATING") && !rating) {
										JOptionPane.showMessageDialog(frame, "You have a job that needs rating.");
	
										rating = true;
	
										CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
										cardLayout.show(frame.Cards, "finishedJobCuGUI");
										frame.Cards.revalidate();
										frame.Cards.repaint();
										BrumBrumGUI.finishedJobCu.setFinishedJobGUI( (Job) message.getData() );
										frame.finishedJobCu.setLabelText();
									} else if(id.equals("NO CURRENT RIDES")) { // No outstanding actions.
										requesting = false;
										accepted = false;
										ongoing = false;
										rating = false;
	
										CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
										cardLayout.show(frame.Cards, "custMain");		
										frame.Cards.revalidate();
										frame.Cards.repaint();
	
										exec.shutdown();
									}
								} catch (Exception e) {
										JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nAutomatically logged out");
										CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
										cardLayout.show(frame.Cards, "main");
										frame.setClient(null);
										frame.Cards.revalidate();
										frame.Cards.repaint();
										exec.shutdown();
								}
							}

						}, 0, 3, TimeUnit.SECONDS);
					}
//					frame.getClient().getRating();
					

					//				**********************************************************************
					//				LINE OF CORRECTNESS

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
		}
		);


		//new customer button action listener
		newCust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {

				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "custReg");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});


		//back button action listener
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) {

				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "main");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});

	}
}
