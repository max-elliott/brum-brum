package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import clientside.Client;
import clientside.CustomerClient;
import communication.Job;
import java.util.List;

/**
 * This page will display a user's ride history.
 * 
 * If the user is logged into the system as a customer, they will see a table of their 
 * completed rides with information including the pick-up and drop-off locations, the name
 * of the driver who completed the job and the rating that they gave to the job.
 * 
 * If the user is logged into the system as a driver, they will see a table of all the rides that
 * they have worked and completed, with information including the pick-up and drop-off locations, the
 * name of the customer who requested the ride and the rating that the customer gave the job.
 * 
 * @author Elizabeth Warner
 * @created 2017-03-08
 * @version 2017-03-13
 */

public class UserHistGUI extends JPanel {
	BrumBrumGUI frame;
	String[] columnNames = {"From", "To", "User", "Customer Rating", "Driver Rating", "Date Completed"};
	JTable newTable;
	private static final long serialVersionUID = 1L;

	public UserHistGUI(BrumBrumGUI frame) {
		this.frame = frame;

		if(frame.getClient() != null) {
			Boolean isCustomer = frame.getClient().getClass().equals(CustomerClient.class);
			columnNames[2] = (!isCustomer) ? "Customer" : "Driver";
		}
		String[][] tableData = getJobTableData();
		if (tableData == null) {
			JOptionPane.showMessageDialog(frame.Cards, "Server is unavailable!\nPlease try again later");
		} 
		else {

			//set panel look
			setBackground(Color.WHITE);
			setLayout(null);


			//add the logo
			try {
				URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
				BufferedImage bufImg = ImageIO.read(url);
				Image scaled = bufImg.getScaledInstance(40, 45, Image.SCALE_SMOOTH);
				JLabel logo = new JLabel(new ImageIcon(scaled));

				logo.setBounds(125, 10, 40, 45);
				add(logo);

			} catch (IOException e) {
				e.printStackTrace();
			}


			//JLabel for the name of the company
			JLabel name = new JLabel("BrumBrum");
			name.setBounds(115, 55, 100, 20);
			Font brum = new Font("SansSerif", Font.BOLD, 10);
			name.setFont(brum);
			add(name);


			/**
			 * Create a table filled with details of all the rides the user has in their rides history
			 *  - for either the driver or the customer
			 */

			//Set column names that are the same for customers and drivers
			String[] columnNames = {"From", "To", "User", "Cust. Rating", "Driver Rating", "Date Completed"};

			class MyModel extends AbstractTableModel {
				private String[] columnNames = (frame.getClient() == null) ? 
						new String[6] : BrumBrumGUI.getHistory().getColumnNames();
				private String[][] tableData = (frame.getClient() == null) ?
						new String[1][6] : BrumBrumGUI.getHistory().getJobTableData();
				
				public MyModel() {
					super();
					
					if(frame.getClient() != null) {
					columnNames[2] = (frame.getClient().getClass().equals(CustomerClient.class)) ? 
							"Driver" : "Customer";
					}
				}
				
				@Override
				public int getRowCount() {
					return tableData.length;
				}

				@Override
				public int getColumnCount() {
					return columnNames.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					return tableData[rowIndex][columnIndex];
				}
				
				@Override
				public String getColumnName(int col) {
					return columnNames[col];
				}
				
				@Override
			    public Class getColumnClass(int c) {
			        return getValueAt(0, c).getClass();
			    }
				
			}
			
			newTable = new JTable(new MyModel());
			newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			int[] columnWidth = {100,100,100,100,100,150};
			for(int i = 0; i < columnNames.length; i++) {
				newTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
			}

			JScrollPane scrollPane = new JScrollPane(newTable,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			newTable.setFillsViewportHeight(true);

			scrollPane.setBounds(25, 90, 250, 320);
			add(scrollPane);


			//create back button
			Font font = new Font("SansSerif", Font.PLAIN, 13);
			Color buttonCol = new Color(200, 200, 200);

			//back button takes user back to settings page
			JButton back = new JButton("Back");
			back.setBounds(115, 420, 50, 20);
			back.setFont(font);
			back.setBackground(buttonCol);
			back.setBorder(null);
			add(back);



			/**
			 * Action listener
			 */
			//back button action listener	
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e1) {
//					System.out.println("back button pressed");
					CardLayout cardLayout = (CardLayout)(frame.Cards.getLayout());
					cardLayout.show(frame.Cards, "settings");
					frame.Cards.revalidate();
					frame.Cards.repaint();
				}
			});
		}
	}

	public String[][] getJobTableData() {
		String[][] data; 

		//if the user is logged in as a customer they will see the name of the driver who completed each ride
		if(frame.getClient() == null) {
			//			System.out.println("Creating empty job table now.");
			data = new String[1][columnNames.length];
			data[0][0] = "Client"; data[0][1] = "is"; data[0][2] = "null.";
		} 

		else if(frame.getClient().getClass().getSuperclass().equals(Client.class)) {
			Boolean isCustomer = frame.getClient().getClass().equals(CustomerClient.class);

//			System.out.println("Filling " + columnNames[2] + " job table now.");
			List<Job> history = frame.getClient().requestHistory();
			data = new String[history.size()][columnNames.length];

			if (!frame.getClient().isValid()) {
				return null;
			}
			else {				
				int i = 0;

				for(Job j : history) {
					data[i][0] = j.getFrom();
					data[i][1] = j.getTo();
					data[i][2] = !isCustomer ? j.getCustomer().getLastName() + ", " + j.getCustomer().getFirstName()
							: j.getDriver().getLastName() + ", " + j.getDriver().getFirstName();
					data[i][3] = Integer.toString(j.getCustomerRating());
					data[i][4] = Integer.toString(j.getDriverRating());
					data[i][5] = j.getEndTime();
					i++;
				}
			}
		} 
		else {

			data = new String[1][columnNames.length];
			data[0][0] = "Unexpected"; data[0][1] = "client"; data[0][2] = "object.";
		}

		return data;
	}

	/**
	 * The getter for columnNames
	 * @return String[]
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
	
	
}

