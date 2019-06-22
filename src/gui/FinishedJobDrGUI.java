package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.DriverClient;
import communication.Job;

/**
 * This page is displayed to the driver once they have finished a job.
 * 
 * It allows the driver to rate the customer and then return to the DriverMainPageGUI where they can
 * request a new job.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-15
 * @version 2017-03-20
 */

public class FinishedJobDrGUI extends JPanel {
	BrumBrumGUI frame;
	JLabel customerName;
	private JLabel destination;
	private Job finishedJobGUI;

	private static final long serialVersionUID = 1L;

	public FinishedJobDrGUI(BrumBrumGUI frame) {
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

		//rate customer
		JLabel rate = new JLabel("Please rate your customer out of 5");
		rate.setBounds(40, 140, frame.getWidth() - (40*2), 20);
		rate.setFont(font);
		add(rate);

		//customerName
		customerName = new JLabel();
		customerName.setBounds(35, 230, frame.getWidth() - (30*2), 20);
		customerName.setFont(font);
		add(customerName);
		
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
					JOptionPane.showMessageDialog(null, "Please enter a rating for your 							Customer.");
				} else {
					rating++;
				DriverClient client = (DriverClient) frame.getClient();

				//					If you reach this page immediately after logging in (due to previous server problem)
				//					Then the currentJobGUI has already been set and the runningJobDr will contain a null job
				if (BrumBrumGUI.getRunningJobDr().getCurrentJobGUI() != null) {
					finishedJobGUI = BrumBrumGUI.getRunningJobDr().getCurrentJobGUI();
				}

//				System.out.println("FinishedJobDrGUI.java -> rating = " + rating);
				//					finishedJobGUI.setCustomerRating(Integer.parseInt(rating));
				finishedJobGUI.setCustomerRating(rating);

				boolean ratingResult = client.rateCustomer(new Job(finishedJobGUI.getFrom(), finishedJobGUI.getTo(),
						finishedJobGUI.getJobType(), finishedJobGUI.getCustomer(), 
						finishedJobGUI.getCustomerRating(), finishedJobGUI.getJobID()));
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
						cardLayout.show(frame.Cards, "drMain");
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
		customerName.setText("Name: " + finishedJobGUI.getCustomer().getFirstName() + " " + finishedJobGUI.getCustomer().getLastName());
				destination.setText("Destination: " + finishedJobGUI.getTo());
	}
}
