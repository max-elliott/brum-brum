package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.DriverClient;
import communication.Job;

/**
 * This page is displayed to the driver once they have pressed the 'Start' button on the AcceptedJobDrGUI page. 
 * 
 * It will display the details of the current running job and the customer including from location,
 * to location, additional details, customer name and telephone number.
 * 
 * A 'Finish' button will be available to be pressed by the driver when they have completed the journey,
 * which will create the finish time for the job and take the driver to a new page where they can rate the customer.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-15
 * @version 2017-03-15
 */

public class RunningJobDrGUI extends JPanel {
	BrumBrumGUI frame;
	JLabel locationFrom;
	JLabel locationTo;
	JLabel what;
	JTextField extraDetails;
	JLabel customerName;
	JLabel customerPhone;
	Job currentJobGUI;

	private static final long serialVersionUID = 1L;

	public RunningJobDrGUI(BrumBrumGUI frame) {
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


		/**
		 * Running job details
		 */
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//locationFrom
		locationFrom = new JLabel();
		locationFrom.setBounds(30, 110, frame.getWidth() - 30, 20);
		locationFrom.setFont(font);
		add(locationFrom);

		//locationTo
		locationTo = new JLabel();
		locationTo.setBounds(30, 140, frame.getWidth() - 30, 20);
		locationTo.setFont(font);
		add(locationTo);

		//what
		what = new JLabel();
		what.setBounds(30, 170, frame.getWidth() - 30, 20);
		what.setFont(font);
		add(what);


		JLabel detailsLabel = new JLabel("Extra Details:");
		detailsLabel.setBounds(30, 200, frame.getWidth() - 30, 20);
		detailsLabel.setFont(font);
		add(detailsLabel);

		/**
		 * NOT SURE IF THIS WORKS YET - NEED TO ADD DETAILS TO A RIDE TO SEE WHETHER THE JTEXTFIELD
		 * WORKS WITH A SCROLL PANE
		 */
		//extraDetails
		extraDetails = new JTextField();
		extraDetails.setEditable(false);
		//		extraDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//		extraDetails.setLineWrap(true);
		//		extraDetails.setWrapStyleWord(true);
		extraDetails.setFont(font);

		/**
		 * JTextField within JScrollPane 
		 */
		JScrollPane scrollPane = new JScrollPane(extraDetails);
		scrollPane.getVerticalScrollBar();
		//		scrollPane.getHorizontalScrollBar();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(30, 230, 240, 70);
		add(scrollPane, BorderLayout.CENTER);

		//customerName
		customerName = new JLabel();
		customerName.setBounds(30, 310, 240, 20);
		customerName.setFont(font);
		add(customerName);

		//customer phone number
		customerPhone = new JLabel();
		customerPhone.setBounds(30, 340, 240, 20);
		customerPhone.setFont(font);
		add(customerPhone);

		/**
		 * Start button to start job
		 */
		Color buttonCol = new Color(200, 200, 200);

		//start button
		JButton finish = new JButton("Finish");
		finish.setBounds(100, 380, 100, 50);
		finish.setFont(font);
		finish.setBackground(buttonCol);
		finish.setBorder(null);
		add(finish);


		/**
		 * Action listener
		 */
		//finish button action listener
		finish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				DriverClient client = (DriverClient) frame.getClient();

				boolean finishResult = client.finishJob(currentJobGUI);
				if (!frame.getClient().isValid()) { 
					JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nPlease try again later\nInform the office if the problem persists");
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.setClient(null);
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} else {
					if(finishResult) {
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "finishedJobDr");
						frame.finishedJobDr.setFinishedJobGUI(frame.runningJobDr.getCurrentJobGUI());
						frame.finishedJobDr.setLabelText();
						frame.Cards.revalidate();
						frame.Cards.repaint();
					} 
					else {
						JOptionPane.showMessageDialog(null, "Finish failed.");
					}
				}
			}
		});

	}

	/**
	 * Setter for the currentJob
	 * @param Job
	 */
	public void setCurrentJobGUI(Job currentJob) {
		this.currentJobGUI = currentJob;
		//		setLabelText();
	}


	/**
	 * The getter for currentJobGUI
	 * @return Job
	 */
	public Job getCurrentJobGUI() {
		return currentJobGUI;
	}

	public void setLabelText() {
		locationFrom.setText("FROM: " + currentJobGUI.getFrom());
		locationTo.setText("TO: " + currentJobGUI.getTo());

		if(currentJobGUI.getDetails() == null) {
			extraDetails.setText("No additional details");
		}
		else {
			extraDetails.setText(currentJobGUI.getDetails());
		}

		customerName.setText("Name: " + currentJobGUI.getCustomer().getFirstName() + " " + currentJobGUI.getCustomer().getLastName());
		customerPhone.setText("Tel no: " + currentJobGUI.getCustomer().getTelNumber());
	}

}
