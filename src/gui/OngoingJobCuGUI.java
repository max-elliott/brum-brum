package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import communication.Job;

public class OngoingJobCuGUI extends JPanel {
	BrumBrumGUI frame;
	private JLabel label;
	private JLabel to;
	private JLabel from;
	private JLabel driverFullName;

	public OngoingJobCuGUI(BrumBrumGUI frame) {
		this.frame = frame;
		
		setBackground(Color.WHITE);
		setLayout(null);

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
		name.setBounds(120, 55, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 10);
		name.setFont(brum);
		add(name);
		
		Font font = new Font("SansSerif", Font.PLAIN, 13);

		//locationFrom
		from = new JLabel();
		from.setBounds(30, 110, 240, 20);
		from.setFont(font);
		add(from);

		//locationTo
		to = new JLabel();
		to.setBounds(30, 140, 240, 20);
		to.setFont(font);
		add(to);

		//driverName
		driverFullName = new JLabel();
		driverFullName.setBounds(30, 230, 240, 20);
		driverFullName.setFont(font);
		add(driverFullName);
		
		ImageIcon icon = new ImageIcon("car.gif");
		
		label = new JLabel(icon, JLabel.CENTER);
		label.setBounds(0, 270, 300, 20);
		label.setFont(font);
		add(label);
	}
	
	public void setLabelText() {
		Job job = BrumBrumGUI.getCustMain().getCustomerJob();
		from.setText("START: " + job.getFrom());
		to.setText("DESTINATION: " + job.getTo());
		driverFullName.setText("DRIVER NAME: " + job.getDriver().getFirstName() + " " + job.getDriver().getLastName());
	}
	
}
