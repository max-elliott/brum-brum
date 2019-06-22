//package server;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//public class ServerStartUp extends JPanel {
//	
//	private static final long serialVersionUID = 1L;
//	
//	private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
//	
//	ServerStartUp(){
//		
//		//super();
//		
//		JLabel serverStatus = new JLabel("Server not running.");
//		
//		JTextField portNumber = new JTextField("Enter port number...");
//		
//		JButton startButton = new JButton("Start server");
//		
//		JButton stopButton = new JButton("Stop server");
//		stopButton.setEnabled(false);
//		
//		JLabel driverCount = new JLabel("Drivers online: 0");
//		
//		JLabel customerCount = new JLabel("Customers online: 0");
//		
//		JButton refresh = new JButton("refresh");
//		
//		startButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//				try{
//					ServerGUI.server = new Server();
//					ServerGUI.server.setPort(portNumber.getText());
//
//					exec.execute(new ServerStartUpThread());
//					
//					serverStatus.setText("Server running on port number " + portNumber.getText());
//					startButton.setEnabled(false);
//					stopButton.setEnabled(true);
//				}
//				catch(NumberFormatException e1){
//					JOptionPane.showMessageDialog(null, "Enter a valid port number");
//				}
//				catch(Exception e1){
//					e1.printStackTrace();
//					
//				}
//			}
//		});
//		
//		stopButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//				try{
//					//ServerGUI.server.setIsStopped(true);
//					ServerGUI.server.stop();
//					System.out.println("ServerStartUp: isStopped = " + ServerGUI.server.getIsStopped());
//					exec.shutdownNow();
//					//System.out.println("ServerStartUp: executor shutdown");
//					serverStatus.setText("Server not running.");
//					startButton.setEnabled(true);
//					stopButton.setEnabled(false);
//				}
//				catch(Exception e1){
//					e1.printStackTrace();
//				}
//			}
//		});
//		
//		refresh.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//				try{
//					customerCount.setText("Customers online: " + ServerGUI.server.connectedCustomers.size());
//					driverCount.setText("Drivers online: " + ServerGUI.server.connectedDrivers.size());
//				}
//				catch(Exception e1){
//					e1.printStackTrace();
//				}
//			}
//		});
//
//		add(serverStatus);
//		add(portNumber);
//		add(startButton);
//		add(stopButton);
//		add(driverCount);
//		add(customerCount);
//		add(refresh);
//	}
//}
