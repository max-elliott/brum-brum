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
 * This page is displayed to the driver once they have pressed the accept button
 * on the DriverJobGUI page. 
 * 
 * It will display the details of the job and the customer including from location,
 * to location, additional details, customer name and telephone number.
 * 
 * A 'Start' button will be available to be pressed by the driver when they pick up the customer
 * or their package, which will create the start time for the job and take the driver to a new page
 * with a 'Finish' button.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-15
 * @version 2017-03-15
 */

public class AcceptedJobDrGUI extends JPanel {
	BrumBrumGUI frame;
	JLabel locationFrom;
	JLabel locationTo;
	JLabel what;
	JTextArea extraDetails;
	JLabel customerName;
	JLabel customerPhone;
	Job currentJobGUI;

	private static final long serialVersionUID = 1L;

	public AcceptedJobDrGUI(BrumBrumGUI frame) {
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
		 * Accepted job details
		 */
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//locationFrom
		locationFrom = new JLabel();//need to get info from oldest available job in database
		locationFrom.setBounds(30, 110, 210, 20);
		locationFrom.setFont(font);
		add(locationFrom);

		//locationTo
		locationTo = new JLabel();//need to get info from oldest available job in database
		locationTo.setBounds(30, 140, 210, 20);
		locationTo.setFont(font);
		add(locationTo);

		//what
		what = new JLabel();//need to get info from oldest available job in database
		what.setBounds(30, 170, 210, 20);
		what.setFont(font);
		add(what);

		JLabel detailsLabel = new JLabel("Extra Details:");
		detailsLabel.setBounds(30, 200, 210, 20);
		detailsLabel.setFont(font);
		add(detailsLabel);
		
		//extraDetails
		extraDetails = new JTextArea();//need to get info from oldest available job in database
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
		customerName = new JLabel();//need to get info from oldest available job in database
		customerName.setBounds(30, 300, 210, 20);
		customerName.setFont(font);
		add(customerName);

		//customer phone number
		customerPhone = new JLabel();
		customerPhone.setBounds(30, 330, 210, 20);
		customerPhone.setFont(font);
		add(customerPhone);

		/**
		 * Start button to start job
		 */
		Color buttonCol = new Color(200, 200, 200);

		//start button
		JButton start = new JButton("Start");
		start.setBounds(100, 360, 100, 50);
		start.setFont(font);
		start.setBackground(buttonCol);
		start.setBorder(null);
		add(start);


		/**
		 * Action listener
		 */
		//start button action listener
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				
				DriverClient client = (DriverClient) frame.getClient();
				if(client.startJob(getCurrentJobGUI())) {
					
					BrumBrumGUI.getRunningJobDr().setCurrentJobGUI(BrumBrumGUI.getAcceptedJobDr().getCurrentJobGUI());
					BrumBrumGUI.getRunningJobDr().setLabelText();
					
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "runningJobDr");
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} 
				
				else {
					JOptionPane.showMessageDialog(null, "Start failed.");
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
		what.setText("TYPE: " + currentJobGUI.getJobType());
		extraDetails.setText(currentJobGUI.getDetails());
		customerName.setText("Name: " + currentJobGUI.getCustomer().getFirstName() + " " + currentJobGUI.getCustomer().getLastName());
		customerPhone.setText("Tel no: " + currentJobGUI.getCustomer().getTelNumber());

	}

}
