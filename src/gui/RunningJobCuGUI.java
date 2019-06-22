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
 * This page will be visible to the customer when a driver has accepted their ride request.
 * 
 *  A pop-up message over the customer's main page will inform the customer that their request 
 *  has been accepted, and they will be taken to this page.
 *  
 *  They will be able to see the details of the driver who has accepted the request, and selected details
 *  about the ride.
 *  
 *  Once the driver has selected the 'Finish' button to complete the ride, a pop-up message will appear
 *  informing the customer that the ride has been completed. They will then be taken to a new page to rate the
 *  driver. Upon submitting the rating they will be returned to the customer's main page where they can
 *  request a new page.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-15
 * @version 2017-03-15
 */

public class RunningJobCuGUI extends JPanel {
	BrumBrumGUI frame;
	JLabel locationFrom;
	JLabel locationTo;
	JLabel driverName;
	JLabel driverPhone;
	JLabel driverReg;
	JLabel driverCar;
	Job currentJobGUI;

	private static final long serialVersionUID = 1L;

	public RunningJobCuGUI(BrumBrumGUI frame) {
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
		locationFrom.setBounds(30, 110, 240, 20);
		locationFrom.setFont(font);
		add(locationFrom);

		//locationTo
		locationTo = new JLabel();
		locationTo.setBounds(30, 140, 240, 20);
		locationTo.setFont(font);
		add(locationTo);

		//driverName
		driverName = new JLabel();
		driverName.setBounds(30, 230, 240, 20);
		driverName.setFont(font);
		add(driverName);

		//driver phone number
		driverPhone = new JLabel();
		driverPhone.setBounds(30, 260, 240, 20);
		driverPhone.setFont(font);
		add(driverPhone);
		//driver car - consists of car make and model
		driverCar = new JLabel();
		driverCar.setBounds(30, 290, 240, 20);
		driverCar.setFont(font);
		add(driverCar);
		
		//driver registration number
		driverReg = new JLabel();
		driverReg.setBounds(30, 320, 240, 20);
		driverReg.setFont(font);
		add(driverReg);
	}

	
	/**
	 * NEED TO ADD SOMETHING THAT WILL CREATE A POP UP IF DRIVER HAS COMPLETED JOB - NOT SURE HOW 
	 * TO IMPLEMENT THIS? 
	 */
	
	
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
		driverName.setText("DRIVER: " + currentJobGUI.getDriver().getFirstName() + " " + currentJobGUI.getDriver().getLastName());
		driverPhone.setText("TEL NO.:" + currentJobGUI.getDriver().getTelNumber());
		driverCar.setText("CAR: " + currentJobGUI.getDriver().getCar_make() + " " + currentJobGUI.getDriver().getCar_model());
		driverReg.setText("REG: " + currentJobGUI.getDriver().getReg_no());
	}


}
