package gui;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.DriverClient;
import communication.*;

import java.awt.event.*;

/**
 * This page will display details of a job request to the driver. 
 * 
 * The driver will have the option to accept a job, in which case the job status will change to 
 * in progress and the drivers state will change to inactive, and the customer will receive details
 * of the driver that has accepted the job.
 * 
 * The driver will also have to option to decline a job, in which case they will receive another job request
 * and again have the option to accept or decline it.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-08
 * @version 2017-03-13
 */

public class DriverJobGUI extends JPanel {
	BrumBrumGUI frame;
	JLabel locationFrom;
	JLabel locationTo;
	JLabel what;
	JTextArea extraDetails;
	JLabel customerName;
	JLabel customerRating;
	Job currentJobGUI;

	private static final long serialVersionUID = 1L;

	public DriverJobGUI(BrumBrumGUI frame) {
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
		 * New job details
		 */
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//locationFrom
		locationFrom = new JLabel();
		locationFrom.setBounds(30, 110, 210, 20);
		locationFrom.setFont(font);
		add(locationFrom);

		//locationTo
		locationTo = new JLabel();
		locationTo.setBounds(30, 140, 210, 20);
		locationTo.setFont(font);
		add(locationTo);

		//what
		what = new JLabel();
		what.setBounds(30, 170, 210, 20);
		what.setFont(font);
		add(what);

		JLabel detailsLabel = new JLabel("Extra Details:");
		detailsLabel.setBounds(30, 200, 210, 20);
		detailsLabel.setFont(font);
		add(detailsLabel);

		//extraDetails
		extraDetails = new JTextArea();
		extraDetails.setEditable(false);
		extraDetails.setFont(font);

		JScrollPane scrollPane = new JScrollPane(extraDetails);
		scrollPane.getVerticalScrollBar();
		//		scrollPane.getHorizontalScrollBar();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(30, 230, 240, 70);
		add(scrollPane, BorderLayout.CENTER);

		//customerName
		customerName = new JLabel();
		customerName.setBounds(30, 310, 210, 20);
		customerName.setFont(font);
		add(customerName);

		//customerRating
		customerRating = new JLabel();
		customerRating.setBounds(30, 330, 210, 20);
		customerRating.setFont(font);
		add(customerRating);


		/**
		 * Accept and decline job buttons
		 */
		Color buttonCol = new Color(200, 200, 200);

		//accept button
		JButton accept = new JButton("Accept");
		accept.setBounds(45, 360, 100, 50);
		accept.setFont(font);
		accept.setBackground(buttonCol);
		accept.setBorder(null);
		add(accept);

		//decline button
		JButton decline = new JButton("Decline");
		decline.setBounds(155, 360, 100, 50);
		decline.setFont(font);
		decline.setBackground(buttonCol);
		decline.setBorder(null);
		add(decline);

		//back button
		JButton back = new JButton("Back");
		back.setBounds(120, 420, 60, 30);
		back.setFont(font);
		back.setBackground(buttonCol);
		back.setBorder(null);
		add(back);


		/**
		 * Action listeners
		 */
		//accept button action listener
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				DriverClient client = (DriverClient) frame.getClient();
				Job currentJob = client.getDriver().getCurrentJob();

				//				Possible Message.id = "ACCEPTED", "JOB UNAVAILABLE", "FAILED", "ERROR"
				Message message = client.acceptJob(currentJob);
				if (!frame.getClient().isValid()) { 
					JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nAutomatically logged out\nPlease log in and try again");
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.setClient(null);
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} else {
//					System.out.println("Server reply: " + message.getId());

					if (message.getId().equals("JOB ACCEPTED")) {
//						System.out.println("Job accepted!");

						BrumBrumGUI.getAcceptedJobDr().setCurrentJobGUI(BrumBrumGUI.getDrJob().getCurrentJobGUI());
						BrumBrumGUI.getAcceptedJobDr().setLabelText();
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "acceptedJobDr");
						frame.Cards.revalidate();
						frame.Cards.repaint();
					} 

					else if (message.getId().equals("JOB UNAVAILABLE")) {
//						System.out.println("Job unavailable");
						JOptionPane.showMessageDialog(frame, "Job has been taken by another driver, sorry!");
						BrumBrumGUI.getDrJob().refreshJob();
					} 

					else if (message.getId().equals("FAILED")) {
//						System.out.println("Failed");
					} 

					else {
						System.out.println("DriverJobGUI -> accept() : Error");
					}
				}
			}
		});


		//decline button action listener - brings up the next available job
		decline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				BrumBrumGUI.getDrJob().refreshJob();
			}
		});


		//back button action listener - takes driver back to main page
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) {

				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "drMain");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});
	}

	/**
	 * Setter for the currentJob
	 * @param Job
	 */
	public void setCurrentJobGUI(Job currentJob) {
		this.currentJobGUI = currentJob;
	}

	public Job getCurrentJobGUI() {
		return currentJobGUI;
	}

	public void setLabelText() {
		locationFrom.setText("FROM: " + currentJobGUI.getFrom());
		locationTo.setText("TO: " + currentJobGUI.getTo());
		what.setText("TYPE: " + currentJobGUI.getJobType());
		extraDetails.setText(currentJobGUI.getDetails());
		customerName.setText("Name: " + currentJobGUI.getCustomer().getFirstName() + " " + currentJobGUI.getCustomer().getLastName());
		customerRating.setText("Rating: " + Double.toString(currentJobGUI.getCustomer().getRating()));
		/**
		 * NEED TO ADD EXTRA DETAILS AND CONVERT RATING TO STRING?
		 */


	}

	public void refreshJob() {
		DriverClient client = (DriverClient) frame.getClient();
		Job job = client.requestJob(new JobSearch(client.getDriver().getId(), currentJobGUI.getJobID()));
		if(job == null) {
			job = client.requestJob(new JobSearch(client.getDriver().getId(), -1));
			if (job == null) {
				System.out.println("NO JOB AVAILABLE!!!!");
				JOptionPane.showMessageDialog(frame, "No jobs are available, sorry :(");
				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "drMain");
			}
		}
		if (job != null) {
			job.setDriver(client.getDriver());
			this.currentJobGUI = job;

			client.getDriver().setCurrentJob(currentJobGUI);

			setLabelText();
		}
	}
}
