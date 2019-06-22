package gui;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


/*
 * This is the main page of the application, visible when the application is first opened.
 * 
 * This page allows users to choose whether to log in as a driver or a customer. The driver and 
 * customer login buttons will take users to the appropriate page to log in to the system.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-06
 */

public class MainPageGUI extends JPanel {
	BrumBrumGUI frame;

	private static final long serialVersionUID = 1L;

	public MainPageGUI(BrumBrumGUI frame) {
		this.frame = frame;

//		try {
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//		}
//
//		catch(UnsupportedLookAndFeelException | 
//				ClassNotFoundException | 
//				InstantiationException | 
//				IllegalAccessException e) {
//		}

		//set panel look
		setBackground(Color.WHITE);
		setLayout(null);


		//add the company logo
		try {
			URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image scaled = bufImg.getScaledInstance(170, 190, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel(new ImageIcon(scaled));

			logo.setBounds(65, 20, 170, 200);
			add(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}


		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrum");
		name.setBounds(105, 215, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 16);
		name.setFont(brum);
		add(name);


		//create the driver and cutomer login buttons
		Font font = new Font("SansSerif", Font.PLAIN, 13);
		Color buttonCol = new Color(200, 200, 200);

		//customer login button
		JButton cu = new JButton("Customer Login");
		cu.setBounds(95, 300, 110, 50);
		cu.setFont(font);
		cu.setBackground(buttonCol);
		cu.setBorder(null);
		add(cu);

		//driver login button
		JButton dr = new JButton("Driver Login");
		dr.setBounds(95, 370, 110, 50);
		dr.setFont(font);
		dr.setBackground(buttonCol);
		dr.setBorder(null);
		add(dr);


		//customer login button action listener - takes user to customer login page
		cu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				frame.refreshCustomerLogin();
				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "custLogin");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});

		//driver login button action listener - takes user to driver login page
		dr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				frame.refreshDriverLogin();
				CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
				cardLayout.show(frame.Cards, "drLogin");
				frame.Cards.revalidate();
				frame.Cards.repaint();
			}
		});

	}
}
