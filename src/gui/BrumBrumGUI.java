package gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import com.sun.java.swing.*;

import clientside.Client;

/**
 * This class sets the JFrame for the GUI and adds all the different panels
 * that will be used throughout the program
 * 
 * @author Elizabeth Warner
 * @created 2017-03-06
 * @version 2017-03-16
 */

public class BrumBrumGUI extends JFrame {
	private Client myClient;

	private static final long serialVersionUID = 1L;

	JPanel Cards;

	public static MainPageGUI main;
	public static DriverLoginGUI drLogin;
	public static CustLoginGUI custLogin;
	public static NewCustRegGUI custReg;
	public static CustMainGUI custMain;
	public static WaitingCuGUI waitingCu;
	public static DriverMainGUI drMain;
	public static DriverJobGUI drJob;
	public static AcceptedJobDrGUI acceptedJobDr;
	public static RunningJobDrGUI runningJobDr;
	public static RunningJobCuGUI runningJobCu;
	public static FinishedJobDrGUI finishedJobDr;
	public static FinishedJobCuGUI finishedJobCu;
	public static CustCompleteGUI custComplete;
	public static SettingsGUI settings;
	public static EditUserGUI editUsr;
	public static ContactGUI contact;
	public static UserHistGUI history;
	public static OngoingJobCuGUI ongoingJobCu;
	public String ip = "localhost";
	public String port = "7001";

	public Client getClient() {
		return myClient;
	}

	public void setClient(Client myClient) {
		this.myClient = myClient;
	}
	
	public String getIP() {
		return ip;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setIP(String ip) {
		this.ip = ip;
	}
	
	public void setPort(String port) {
		this.port = port;
	}

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrumBrumGUI frame = new BrumBrumGUI();
					if (args.length == 2) {
						frame.setIP(args[0]);
						frame.setPort(args[1]);
					} 
					main = new MainPageGUI(frame); // Done
					drLogin = new DriverLoginGUI(frame); // Need to check the server status in the threadpool
					custLogin = new CustLoginGUI(frame); // Done
					custReg = new NewCustRegGUI(frame); // Done
					custMain = new CustMainGUI(frame); // Done
					waitingCu = new WaitingCuGUI(frame); // Nothing to be done
					drMain = new DriverMainGUI(frame); // Done
					drJob = new DriverJobGUI(frame);
					acceptedJobDr = new AcceptedJobDrGUI(frame);
					runningJobDr = new RunningJobDrGUI(frame);
					runningJobCu = new RunningJobCuGUI(frame);
					finishedJobDr = new FinishedJobDrGUI(frame); //Done
					finishedJobCu = new FinishedJobCuGUI(frame); //Done
					settings = new SettingsGUI(frame); // Done
					editUsr = new EditUserGUI(frame); // Done
					contact = new ContactGUI(frame); // Featureless
					history = new UserHistGUI(frame); // Done
					ongoingJobCu = new OngoingJobCuGUI(frame);
					custComplete = new CustCompleteGUI(frame);

					frame.Cards.add(main, "main"); //done
					frame.Cards.add(drLogin, "drLogin"); //threadpool needs doing
					frame.Cards.add(custLogin, "custLogin"); //threadpool needs doing
					frame.Cards.add(custReg, "custReg"); //done
					frame.Cards.add(custMain, "custMain");
					frame.Cards.add(waitingCu, "waitingCu");
					frame.Cards.add(drMain, "drMain");
					frame.Cards.add(drJob, "drJob");
					frame.Cards.add(acceptedJobDr, "acceptedJobDr");
					frame.Cards.add(runningJobDr, "runningJobDr");
					frame.Cards.add(runningJobCu, "runningJobCu");
					frame.Cards.add(finishedJobDr, "finishedJobDr");
					frame.Cards.add(finishedJobCu, "finishedJobCuGUI");
					frame.Cards.add(settings, "settings"); //done
					frame.Cards.add(editUsr, "editUsr"); //done
					frame.Cards.add(contact, "contact"); //nothing to do
					frame.Cards.add(history, "history");
					frame.Cards.add(ongoingJobCu, "ongoingJobCu");
					frame.Cards.add(custComplete, "custComplete");

					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
				}
			}
		});
	}

	public BrumBrumGUI() {

		//set the JPanels
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		Cards = new JPanel();
		Cards.setLayout(new CardLayout(0, 0));
		setContentPane(Cards);
	}

	/**
	 * Recreates the history frame.
	 */
	public void refreshHistory() {
		this.Cards.remove(history);
		this.Cards.add(new UserHistGUI(this), "history");
	}
	
	/**
	 * Recreates the edit User form
	 */
	public void refreshEditUsr() {
		this.Cards.remove(editUsr);
		this.Cards.add(new EditUserGUI(this), "editUsr");
	}
	
	public void refreshDriverLogin() {
		this.Cards.remove(drLogin);
		this.Cards.add(new DriverLoginGUI(this), "drLogin");
	}
	
	public void refreshCustomerLogin() {
		this.Cards.remove(custLogin);
		this.Cards.add(new CustLoginGUI(this), "custLogin");
	}

	/**
	 * The getter for drJob
	 * @return DriverJobGUI
	 */
	public static DriverJobGUI getDrJob() {
		return drJob;
	}


	public static AcceptedJobDrGUI getAcceptedJobDr() {
		return acceptedJobDr;
	}

	/**
	 * The getter for runningJobDr
	 * @return RunningJobDrGUI
	 */
	public static RunningJobDrGUI getRunningJobDr() {
		return runningJobDr;
	}

	/**
	 * The getter for custMain
	 * @return CustMainGUI
	 */
	public static CustMainGUI getCustMain() {
		return custMain;
	}

	/**
	 * The getter for runningJobCu
	 * @return RunningJobCuGUI
	 */
	public static RunningJobCuGUI getRunningJobCu() {
		return runningJobCu;
	}

	/**
	 * Setter for the runningJobCu
	 * @param RunningJobCuGUI
	 */
	public static void setRunningJobCu(RunningJobCuGUI runningJobCu) {
		BrumBrumGUI.runningJobCu = runningJobCu;
	}

	/**
	 * The getter for ongoingJobCu
	 * @return OngoingJobCuGUI
	 */
	public static OngoingJobCuGUI getOngoingJobCu() {
		return ongoingJobCu;
	}

	/**
	 * The getter for history
	 * @return UserHistGUI
	 */
	public static UserHistGUI getHistory() {
		return history;
	}

	/**
	 * Setter for the history
	 * @param UserHistGUI
	 */
	public static void setHistory(UserHistGUI history) {
		BrumBrumGUI.history = history;
	}
	
	
	
}