package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.CustomerClient;
import clientside.DriverClient;
import communication.Job;

/**
 * This page is displayed to the customer once their job has been finished.
 * 
 * It allows the customer to rate the driver and then return to the CustMainPageGUI where they can
 * request a new ride or logout.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-15
 * @version 2017-03-20
 */

public class FinishedJobCuGUI extends JPanel {
	BrumBrumGUI frame;
	JLabel driverName;
	private JLabel destination;
	private Job finishedJobGUI;

	private static final long serialVersionUID = 1L;

	public FinishedJobCuGUI(BrumBrumGUI frame) {
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
		name.setBounds(117, 70, frame.getWidth() - (117*2), 20);
		Font brum = new Font("SansSerif", Font.BOLD, 13);
		name.setFont(brum);
		add(name);


		/**
		 * Finished job details
		 */
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//job completed!
		JLabel complete = new JLabel("Job Completed!");
		complete.setBounds(100, 110, frame.getWidth() - (100*2), 20);
		complete.setFont(font);
		add(complete);

		//rate driver
		JLabel rate = new JLabel("Please rate your driver out of 5");
		rate.setBounds(50, 140, frame.getWidth() - (50*2), 20);
		rate.setFont(font);
		add(rate);

		//driverName
		driverName = new JLabel();
		driverName.setBounds(35, 230, frame.getWidth() - (30*2), 20);
		driverName.setFont(font);
		add(driverName);

		destination = new JLabel();
		destination.setBounds(35, 250, frame.getWidth() - (30*2), 20);
		destination.setFont(font);
		add(destination);

		StarRating stars = new StarRating();
		stars.setBounds(frame.getWidth()/2-50, 200, 100, 20);
		add(stars);

		/**
		 * Submit and return button to submit customer rating and return driver to the driver's main page
		 */
		Color buttonCol = new Color(200, 200, 200);

		//button to submit rating and return to DriverMainPageGUI
		JButton submitAndReturn = new JButton("Submit");
		submitAndReturn.setBounds(100, 360, 100, 50);
		submitAndReturn.setFont(font);
		submitAndReturn.setBackground(buttonCol);
		submitAndReturn.setBorder(null);
		add(submitAndReturn);


		//submitAndReturn button action listener
		submitAndReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				int rating = stars.getLevel();
				if(rating == -1) {
					JOptionPane.showMessageDialog(frame, "Please rate your Driver.");
				} else {
					rating++;
					CustomerClient client = (CustomerClient) frame.getClient();
					finishedJobGUI = BrumBrumGUI.getCustMain().getCustomerJob();
					//					finishedJobGUI.setCustomerRating(Integer.parseInt(rating));
					finishedJobGUI.setDriverRating(rating);

					Job job = new Job(finishedJobGUI.getFrom(), 
							finishedJobGUI.getTo(),
							finishedJobGUI.getJobType(), 
							finishedJobGUI.getCustomer(), 
							finishedJobGUI.getCustomerRating(), 
							finishedJobGUI.getJobID());
//					System.out.println("FinishedJobCuGUI -> Getting the driver...");
					job.setDriver(finishedJobGUI.getDriver());
					job.setDriverRating(rating);
//					System.out.println("FinishedJobCuGUI -> Got it...");

					boolean ratingResult = client.rateDriver(job);
					if (!frame.getClient().isValid()) { 
						JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nPlease try again later");
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "main");
						frame.setClient(null);
						frame.Cards.revalidate();
						frame.Cards.repaint();
					} else {
						if(ratingResult){

							CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
							cardLayout.show(frame.Cards, "custComplete");
							frame.Cards.revalidate();
							frame.Cards.repaint();
							stars.clear();
						}
						else{
							JOptionPane.showMessageDialog(null, "rating failed.");
						}
					}
				}
			}
		});

	}


	/**
	 * Setter for the currentJob
	 * @param Job
	 */
	public void setFinishedJobGUI(Job currentJob) {
		this.finishedJobGUI = currentJob;
	}


	/**
	 * The getter for currentJobGUI
	 * @return Job
	 */
	public Job getFinishedJobGUI() {
		return finishedJobGUI;
	}

	public void setLabelText() {
		driverName.setText("Name: " + finishedJobGUI.getDriver().getFirstName() + " " + finishedJobGUI.getDriver().getLastName());
		destination.setText("Destination: " + finishedJobGUI.getTo());
	}
}
