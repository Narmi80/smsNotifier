package smsr;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.prefs.Preferences;

import javax.mail.Folder;
import javax.mail.MessagingException;

/**
 * 
 * KnockThread.java
 * 
 * @author Collin Price
 * @contact collin.price@gmail.com
 * @website http://collinprice.com
 * @date 2010-06-05
 */

public class KnockThread extends Thread {

	private Folder folder;
	private TrayIcon trayIcon;
	private boolean stop;
	private boolean gaveNotification;
	private int rate;
	private Image read = Toolkit.getDefaultToolkit().getImage("./bulb-off.gif");
	private Image unread = Toolkit.getDefaultToolkit().getImage("./bulb-on.gif");
	private boolean willNotify;
	
	
	public KnockThread(Folder folder, TrayIcon trayIcon) {
		Preferences prefs = Preferences.userRoot().node("smsSettings");
		this.folder = folder;
		this.trayIcon = trayIcon;
		rate = Integer.parseInt(prefs.get("rate", "5"));
		willNotify = prefs.getBoolean("notify", false);
	} // constructor
	
	@Override
	public void run() {
		stop = true;
		gaveNotification = false;
		while (stop) {
			try {
				folder.getMessageCount();
				System.out.println(folder.getUnreadMessageCount());
				if (folder.getUnreadMessageCount() > 0) {
					System.out.println("unread > 0");
					trayIcon.setImage(unread);
					trayIcon.setToolTip(folder.getUnreadMessageCount() + " unread email");
					if (willNotify == true && gaveNotification == false) {
						trayIcon.displayMessage(null, "New email.", TrayIcon.MessageType.INFO);
						gaveNotification = true;
					}
				} else {
					trayIcon.setImage(read);
					trayIcon.setToolTip("no unread email");
					gaveNotification = false;
				}
				synchronized(this) {
					this.wait(rate * 1000);
				}
			} catch (MessagingException e) {
				stop = false;
				e.printStackTrace();
			} catch (InterruptedException e) {
				stop = false;
				e.printStackTrace();
			}
		}
	} // run

	public void stopKnocking() {
		stop = false;
	} // stopKnocking
	
	/**
	 * 
	 * setKnockRate
	 * 
	 * default rate is 5 second.
	 *
	 * @param r in seconds, the wait time between knocking the mail server. must be between 1 and 10 inclusively.
	 * @return true if rate has changed.
	 */
	public boolean setKnockRate(int r) {
		if (r < 0 || r > 10) {
			return false;
		} else {
			rate = r;
			return true;
		}
	} // setKnockRate
	
	public void setNotification(boolean notify) {
		willNotify = notify;
	} // setNotification
	
	public void forceNotification() {
		gaveNotification = false;
	} // forceNotification
	
	public boolean isKnocking() {
		return stop;
	} // isKnocking
	
} // KnockThread
