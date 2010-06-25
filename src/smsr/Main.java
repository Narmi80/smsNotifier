/**
 * 
 */
package smsr;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

/**
 * Main.java
 * 
 * @author Collin Price
 * @contact collin.price@gmail.com
 * @website http://collinprice.com
 * @date 2010-06-22
 */
public class Main {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("using arguments <username> <password> <cellphone-email>");
			System.exit(1);
		}
		
		if (!SystemTray.isSupported()) {
			System.err.println("SystemTray is not supported. Exiting...");
			System.exit(1);
		}
		
		SystemTray tray = SystemTray.getSystemTray();
		TrayIcon trayIcon = null;
		final Image nomail = Toolkit.getDefaultToolkit().getImage("./bulb-off.gif");
		trayIcon = new TrayIcon(nomail);
		PopupMenu menu = new PopupMenu();
		
		MenuItem miCheck = new MenuItem("Check Mail");
		MenuItem miTell = new MenuItem("Tell Me Again");
		MenuItem miView = new MenuItem("View Mail");
		MenuItem miLogin = new MenuItem("Options");
		MenuItem miExit = new MenuItem("Exit");
		
		menu.add(miCheck);
		menu.add(miTell);
		menu.add(miView);
		menu.add(miLogin);
		menu.add(miExit);
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println("Could not add tray icon to tray.");
			e.printStackTrace();
			System.exit(1);
		}
		
		trayIcon.setPopupMenu(menu);
		final GmailNotifier gNotifier = new GmailNotifier(args[0], args[1], args[2], trayIcon);
		
		trayIcon.addActionListener(new ActionListener() {
			/**
			 * Opens the default browser to the gmail inbox.
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("Double-click");
				openBrowser();
			}
		});
		
		miCheck.addActionListener(new ActionListener() {
			/**
			 * Checks email for new messages.
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("Check");
				gNotifier.notifyDetector();
			}
		});
		
		miTell.addActionListener(new ActionListener() {
			/**
			 * Repeats pop-up notification for user containing information from unread messages.
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("Tell");
				gNotifier.tellDetector();
			}
		});
		
		
		miView.addActionListener(new ActionListener() {
			/**
			 * Opens the default browser to the gmail inbox.
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("View");
				openBrowser();
			}
		});
		
		miLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Options");
				JFrame f = OptionPanel.getOptionPanel();
				f.pack();
				f.setVisible(true);
			}
		});
		
		miExit.addActionListener(new ActionListener() {
			/**
			 * Closes sms notifier.
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
				gNotifier.exit();
				System.exit(1);
			}
		});
	} // main


	/**
	 * openBrowser
	 *
	 */
	protected static void openBrowser() {
		try {
			//Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "http://mail.google.com");
			// -------------  OR  -----------------
			// http://stackoverflow.com/questions/102325/not-supported-platforms-for-java-awt-desktop-getdesktop
			Desktop.getDesktop().browse(new URI("http://mail.google.com/")); 
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e2) {
			e2.printStackTrace();
		}
	} // openBrowser

} // Main
