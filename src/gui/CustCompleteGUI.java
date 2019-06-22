package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import clientside.CustomerClient;
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

public class CustCompleteGUI extends JPanel {
	BrumBrumGUI frame;
	private JLabel label;
	private JLabel thankYou;

	private static final long serialVersionUID = 1L;

	public CustCompleteGUI(BrumBrumGUI frame) {
		this.frame = frame;

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);

		ImageIcon icon = new ImageIcon("Brumsplosion.png");
		
		label = new JLabel(icon, JLabel.CENTER);
		label.setBounds(20, 30, 250, 214);
		add(label);
		
		thankYou = new JLabel("Thanks for using BrumBrum!");
		thankYou.setBounds(45, 250, 240, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 15);
		thankYou.setFont(brum);
		add(thankYou);


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

		JButton request = new JButton("Another ride?");
		request.setBounds(100, 300, 100, 50);
		request.setFont(font);
		request.setBackground(buttonCol);
		request.setBorder(null);
		add(request);


		//request job button action listener
		request.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				CustomerClient client = (CustomerClient) frame.getClient();
				BrumBrumGUI.getCustMain().setAccepted(false);
				BrumBrumGUI.getCustMain().setOngoing(false);
				JFrame message = new JFrame();

				if (!frame.getClient().isValid()) { 
					JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nAutomatically logged out");
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "main");
					frame.setClient(null);
					frame.Cards.revalidate();
					frame.Cards.repaint();
				} else {
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "custMain");		
					frame.Cards.revalidate();
					frame.Cards.repaint();
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
