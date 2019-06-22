package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.Client;
import clientside.CustomerClient;
import clientside.DriverClient;
import communication.Job;
import communication.Message;

/**
 * This is the first and main page that the customer will see after they have successfully
 * logged into the system.
 * 
 * This is the page where the customer can specify the locations for the pickup and
 * drop-off, whether it is a person or a package to be picked up and any other specific
 * details before selecting the 'Request' button.
 * 
 * This page will also include a settings icon which will take the user to the settings page.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-16
 */

public class CustMainGUI extends JPanel {
	BrumBrumGUI frame;
	Job customerJob;
	private boolean accepted = false;
	private boolean ongoing = false;

	private static final long serialVersionUID = 1L;

	public CustMainGUI(BrumBrumGUI frame) {
		this.frame = frame;

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);


		/**
		 * set the JButton that will take users to the settings page and add a settings logo.
		 * The logo is a standard gear from an icon repository online.
		 */ 
		JButton settings = new JButton();
		settings.setBounds(10, 10, 20, 20);
		settings.setBorder(null);
		settings.setBackground(null);

		try {
			URL url = new URL("https://cdn3.iconfinder.com/data/icons/simplius-pack/512/gear-512.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image gear = bufImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			ImageIcon img = new ImageIcon(gear);

			settings.setIcon(img);

		} catch (IOException e) {
			e.printStackTrace();
		}

		add(settings);


		//add the company logo
		try {
			URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image scaled = bufImg.getScaledInstance(40, 45, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel(new ImageIcon(scaled));

			logo.setBounds(125, 10, 40, 45);
			logo.setBorder(null);
			add(logo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrum");
		name.setBounds(115, 55, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 10);
		name.setFont(brum);
		add(name);


		/**
		 * Details of ride request from customer - location from, location to, what will be
		 * picked up by the driver (i.e, person or package), and any additional details that 
		 * the driver should be made aware of
		 */
		//set the font
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//JLabel asking customer to enter location of pickup
		JLabel whereFrom = new JLabel("Please enter location of pickup");
		whereFrom.setBounds(55, 80, 210, 20);
		whereFrom.setFont(font);
		add(whereFrom);

		//JTextField for user to enter location of pickup
		JTextField from = new JTextField();
		from.setBounds(40, 100, 210, 30);
		add(from);

		//JLabel asking customer to enter location of drop-off
		JLabel whereTo = new JLabel("Please enter location of drop-off");
		whereTo.setBounds(55, 140, 210, 20);
		whereTo.setFont(font);
		add(whereTo);

		//JTextField for user to enter location of drop-off
		JTextField to = new JTextField();
		to.setBounds(40, 160, 210, 30);
		add(to);

		//JLabel asking user to choose an option from the JComboBox of what will be picked up
		JLabel select = new JLabel("What will be picked up?");
		select.setBounds(80, 210, 210, 20);
		select.setFont(font);
		add(select);

		//the JComboBox where user can select whether the driver will pick up a person or a package
		JComboBox<String> selectWhat = new JComboBox<String>();
		selectWhat.setBounds(95, 230, 100, 30);
		selectWhat.setFont(font);
		selectWhat.addItem("Person");
		selectWhat.addItem("Package");
		add(selectWhat);

		//JLabel asking customer for any additional details of the pickup/drop-off - could be more
		//details about the locations or the nature of the ride
		JLabel addDetails = new JLabel("Please enter additional details");
		addDetails.setBounds(45, 280, 210, 20);
		addDetails.setFont(font);
		add(addDetails);

		//JTextField for user to enter additional details with JScrollPane
		JTextArea rideDetails =  new JTextArea();
		rideDetails.setLineWrap(true);
		rideDetails.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(rideDetails);
		scrollPane.setBounds(30, 300, 230, 60);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);


		//Request ride JButton
		JButton request = new JButton("Request");
		request.setBounds(95, 380, 100, 50);
		request.setBackground(new Color(200, 200, 200));
		request.setForeground(Color.BLACK);
		request.setFont(font);
		request.setBorder(null);
		add(request);

		/** 
		 * Request button action listener - sends request and takes customer to new waiting page.
		 * When a driver accepts a new page will show the driver's details and the details of the ride to the customer
		 */
		request.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				if(from.getText().equals("") || to.getText().equals("")) {

					JFrame emptyFields = new JFrame();
					JOptionPane.showMessageDialog(emptyFields, "Please complete pick-up and drop-off locations");
				} 

				else {					
					CustomerClient client = (CustomerClient) frame.getClient();
					Message message = client.requestPickup(from.getText(), to.getText(), (String) selectWhat.getSelectedItem(), rideDetails.getText());
					if (!frame.getClient().isValid()) { 
						JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nAutomatically logged out");
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "main");
						frame.setClient(null);
						frame.Cards.revalidate();
						frame.Cards.repaint();
					} else {

						if(Boolean.parseBoolean(message.getId())) {

							CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							cardLayout.show(frame.Cards, "waitingCu");
							frame.Cards.revalidate();
							frame.Cards.repaint();

							BrumBrumGUI.getCustMain().setCustomerJob((Job) message.getData());


							//						THREAD POOL COMMENTED OUT. PINGS SERVER EVERY 10 SECONDS
							//						**********************************************************************
							ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
							exec.scheduleAtFixedRate(new Runnable() {
								public void run() {
									try {
										CustomerClient client = (CustomerClient) BrumBrumGUI.getCustMain().getFrame().getClient();
										Job requestingJob = BrumBrumGUI.getCustMain().getCustomerJob();
										Message acceptMessage = client.isAccepted(requestingJob);
										BrumBrumGUI frame = BrumBrumGUI.getCustMain().getFrame(); 

//										System.out.println(acceptMessage.getId());

										if(acceptMessage.getId().equals("ACCEPTED") && !BrumBrumGUI.getCustMain().getAccepted()) {
											BrumBrumGUI.getCustMain().setCustomerJob( (Job) acceptMessage.getData() ); 
											requestingJob = (Job) acceptMessage.getData();
											BrumBrumGUI.getCustMain().setAccepted(true);
											JOptionPane.showMessageDialog(frame, "Someone has accepted your request");

//											System.out.println("Adding Labels.");
											BrumBrumGUI.getRunningJobCu().setCurrentJobGUI(requestingJob);
											BrumBrumGUI.getRunningJobCu().setLabelText();
//											System.out.println("Finished adding Labels.");

//						        		   System.out.println("Is Driver null? " + (requestingJob.getDriver() == null));

//											System.out.println("Changing page.");
											CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
											cardLayout.show(frame.Cards, "runningJobCu");
											frame.Cards.revalidate();
											frame.Cards.repaint();
//											System.out.println("Changed page.");
										} else if(acceptMessage.getId().equals("ONGOING") && !BrumBrumGUI.getCustMain().getOngoing()) {
											BrumBrumGUI.getCustMain().setCustomerJob( (Job) acceptMessage.getData() );
											BrumBrumGUI.getCustMain().setOngoing(true);
											JOptionPane.showMessageDialog(frame, "Your ride has begun!");
											BrumBrumGUI.getOngoingJobCu().setLabelText();

											CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
											cardLayout.show(frame.Cards, "ongoingJobCu");
											frame.Cards.revalidate();
											frame.Cards.repaint();
										} else if(acceptMessage.getId().equals("FINISHED")) {
											BrumBrumGUI.getCustMain().setAccepted(false);
											BrumBrumGUI.getCustMain().setOngoing(false);
											JOptionPane.showMessageDialog(frame, "Your journey is complete!");
											BrumBrumGUI.getCustMain().setCustomerJob( (Job) acceptMessage.getData() ); 
											BrumBrumGUI.finishedJobCu.setFinishedJobGUI( (Job) acceptMessage.getData() );
											BrumBrumGUI.finishedJobCu.setLabelText();

											CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
											cardLayout.show(frame.Cards, "finishedJobCuGUI");
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
							//						
							from.setText("");
							to.setText("");
							rideDetails.setText("");

							//						// Take them to the next page if accepted.
							//						
							//						JOptionPane.showMessageDialog(null, "Someone has accepted your request");
							//						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							//						cardLayout.show(frame.Cards, "runningJobCu");
							//						*************************************************************************

							//						DriverClient drClient = (DriverClient) frame.getClient();
							//						Job currentJob = drClient.getDriver().getCurrentJob();
							//						Message message = drClient.acceptJob(currentJob);
							//						
							//						if(message.getId().equals("JOB ACCEPTED")) {
							//				
							//							JOptionPane.showMessageDialog(null, "Someone has accepted your request");
							//							CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							//							cardLayout.show(frame.Cards, "runningJobCu");
							//						}
							//						else {
							//						JOptionPane.showMessageDialog(null, "Request unsuccessful.");
							//						}
						}
					}
				}
			}
		});

		//settings button action listener
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {

				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "settings");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});
	}

	/**
	 * The getter for customerJob
	 * @return Job
	 */
	public Job getCustomerJob() {
		return customerJob;
	}

	/**
	 * Setter for the customerJob
	 * @param Job
	 */
	public void setCustomerJob(Job customerJob) {
		this.customerJob = customerJob;
	}

	/**
	 * The getter for frame
	 * @return BrumBrumGUI
	 */
	public BrumBrumGUI getFrame() {
		return frame;
	}

	/**
	 * The getter for accepted
	 * @return Boolean
	 */
	public Boolean getAccepted() {
		return accepted;
	}

	/**
	 * Setter for the accepted
	 * @param Boolean
	 */
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * The getter for ongoing
	 * @return boolean
	 */
	public boolean getOngoing() {
		return ongoing;
	}

	/**
	 * Setter for the ongoing
	 * @param boolean
	 */
	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}



}





