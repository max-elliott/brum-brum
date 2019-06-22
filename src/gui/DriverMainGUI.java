package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.DriverClient;
import communication.*;

/**
 * This is the main page that a driver will see after they have logged into the system.
 * 
 * It is a very simple page that allows the driver to access the settings page and 
 * request a new job.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-08
 * @version 2017-03-11
 */

public class DriverMainGUI extends JPanel {
	BrumBrumGUI frame;

	private static final long serialVersionUID = 1L;

	public DriverMainGUI(BrumBrumGUI frame) {
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
		name.setBounds(105, 150, 210, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 16);
		name.setFont(brum);
		add(name);


		//set the JButton that will take users to the settings page and add a settings logo.
		//The logo is a standard gear from an icon repository online.
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


		//request job button
		Font font = new Font("SansSerif", Font.PLAIN, 13);
		Color buttonCol = new Color(200, 200, 200);

		JButton request = new JButton("Request Job");
		request.setBounds(100, 300, 100, 50);
		request.setFont(font);
		request.setBackground(buttonCol);
		request.setBorder(null);
		add(request);


		//request job button action listener
		request.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				DriverClient client = (DriverClient) frame.getClient();
				client.getDriver().setCurrentJob(null);
				JFrame message = new JFrame();

				Job search = client.requestJob(new JobSearch(client.getDriver().getId()));

				if (!frame.getClient().isValid()) { 
					JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nAutomatically logged out");
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.setClient(null);
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} else {
					if (search != null) {
						JOptionPane.showMessageDialog(frame, "New job available");
						search.setDriver(client.getDriver());
						client.getDriver().setCurrentJob(search);
						System.out.println("DriverMainGUI -> Received Job\nCustomer: " 
								+ search.getCustomer().getFirstName() 
								+ " From: " + search.getFrom() 
								+ " To: " + search.getTo());
						BrumBrumGUI.getDrJob().setCurrentJobGUI(search);
						BrumBrumGUI.getDrJob().setLabelText();
						CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
						cardLayout.show(frame.Cards, "drJob");
						frame.Cards.revalidate();
						frame.Cards.repaint();
					} else {
						JOptionPane.showMessageDialog(frame, "No Jobs Available");
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
	 * The getter for frame
	 * @return BrumBrumGUI
	 */
	public BrumBrumGUI getFrame() {
		return frame;
	}
}
