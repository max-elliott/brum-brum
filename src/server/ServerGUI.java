package server;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**GUI class to launch the sever. You can set the server port number, and see
 * all users currently online.
 * @author Max Elliott
 * @version 2017-03-21
 */
public class ServerGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	//public static ServerStartUp serverStartUp;
	protected static Server server = new Server();
	private int port;
	
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				try{
					JFrame frame = new JFrame("Server");
					
					//serverStartUp = new ServerStartUp();
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setSize(400, 600);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					serverStartUp(frame.getContentPane());
					//frame.pack();
					//frame.add(serverStartUp);
					frame.setVisible(true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
			
		});
	}
	
	
	public static void serverStartUp(Container pane){
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		
		
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		//super();
	
		try {
			URL url = new URL("http://i67.tinypic.com/rw8dw6.png");
			BufferedImage bufImg = ImageIO.read(url);
			Image scaled = bufImg.getScaledInstance(100, 110, Image.SCALE_SMOOTH);
			JLabel logo = new JLabel(new ImageIcon(scaled));
			addComponent(logo, pane);

		} catch (IOException e) {
			e.printStackTrace();
		}


		//JLabel for the name of the company
		JLabel name = new JLabel("BrumBrumServer");
		name.setBounds(105, 150, 100, 20);
		Font brum = new Font("SansSerif", Font.BOLD, 16);
		name.setFont(brum);
		
		JLabel serverStatus = new JLabel("Server not running.");
		Dimension d1 = new Dimension(500,100);
		serverStatus.setMinimumSize(d1);
		
		Dimension d2 = new Dimension(100,50);
		JTextField portNumber = new JTextField("Enter port number...");
		portNumber.setMaximumSize(d2);
		
		JButton startButton = new JButton("Start server");
		
		JButton stopButton = new JButton("Stop server");
		stopButton.setEnabled(false);
		
		JLabel driverCount = new JLabel("Drivers online: -");
		
		JLabel customerCount = new JLabel("Customers online: -");
		
		JButton refresh = new JButton("refresh");
		refresh.setEnabled(false);
		
		JLabel customersOnline = new JLabel("Customers online:");
		
		JLabel driversOnline = new JLabel("Drivers online:");
		
		JTextArea drivers = new JTextArea();
		drivers.setEditable(false);
		
		JScrollPane driversScrollPane = new JScrollPane(drivers,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		driversScrollPane.setSize(300, 300);
		
		JTextArea customers = new JTextArea();
		customers.setEditable(false);
		
		JScrollPane customersScrollPane = new JScrollPane(customers,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		driversScrollPane.setSize(300, 300);
		
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					ServerGUI.server = new Server();
					ServerGUI.server.setPort(portNumber.getText());

					exec.execute(new ServerStartUpThread());
					
					serverStatus.setText("Server running on port number " + portNumber.getText());
					startButton.setEnabled(false);
					stopButton.setEnabled(true);
					refresh.setEnabled(true);
				}
				catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(null, "Enter a valid port number");
				}
				catch(Exception e1){
					e1.printStackTrace();
					
				}
			}
		});
		

		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					//ServerGUI.server.setIsStopped(true);
					ServerGUI.server.stop();
					System.out.println("ServerStartUp: isStopped = " + ServerGUI.server.getIsStopped());
					exec.shutdownNow();
					//System.out.println("ServerStartUp: executor shutdown");
					serverStatus.setText("Server not running.");
					startButton.setEnabled(true);
					stopButton.setEnabled(false);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					customerCount.setText("Customers online: " + ServerGUI.server.connectedCustomers.size());
					driverCount.setText("Drivers online: " + ServerGUI.server.connectedDrivers.size());
					
					Iterator<String> driverIterator = server.connectedDrivers.iterator();
					String allDrivers = "";
				     while(driverIterator.hasNext()){
				       allDrivers += driverIterator.next() + "\n";
				     }
					drivers.setText(allDrivers);
					
					Iterator<String> customerIterator = server.connectedCustomers.iterator();
					String allCustomers = "";
				     while(customerIterator.hasNext()){
				       allCustomers += customerIterator.next() + "\n";
				     }
					customers.setText(allCustomers);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		
		addComponent(name, pane);
		addComponent(serverStatus, pane);
		addComponent(portNumber, pane);
		addComponent(startButton, pane);
		addComponent(stopButton, pane);
		addComponent(driverCount, pane);
		addComponent(customerCount, pane);
		addComponent(refresh, pane);
		addComponent(driversOnline, pane);
		addComponent(driversScrollPane, pane);
		addComponent(customersOnline, pane);
		addComponent(customersScrollPane, pane);
	}
	
	public static void addComponent(JComponent c, Container pane){
		pane.add(c);
		c.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	
	
}
