/**
 * 
 */
package smsr;

import java.awt.GridLayout;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * OptionPanel.java
 * 
 * @author Collin Price
 * @contact collin.price@gmail.com
 * @website http://collinprice.com
 * @date 2010-06-24
 */
public class OptionPanel {

	public OptionPanel() {
		
	} // constructor
	
	public static JFrame getOptionPanel() {
		Preferences prefs = Preferences.userRoot().node("smsSettings");
		
		JFrame frame = new JFrame("Options");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		JPanel mainPanel = new JPanel(new GridLayout(3,1));
		
		JPanel login = new JPanel(new GridLayout(2,2));
		login.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Login"), BorderFactory.createEmptyBorder(5,5,5,5)));
		
		JLabel label1 = new JLabel("Username:");
		JLabel label2 = new JLabel("Password:");
		JTextField username = new JTextField(6);
		username.setText(prefs.get("user", null));
		JPasswordField password = new JPasswordField(6);
		password.setText(prefs.get("pass", null));
		login.add(label1);
		login.add(username);
		login.add(label2);
		login.add(password);

		JPanel settings = new JPanel(new GridLayout(2,2));
		settings.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Settings"), BorderFactory.createEmptyBorder(5,5,5,5)));
		
		JLabel label3 = new JLabel("Ping Rate:");
		JLabel label4 = new JLabel("Pop-up Notifications:");
		JTextField rate = new JTextField();
		rate.setText(prefs.get("rate", "5"));
		JCheckBox box = new JCheckBox();
		box.setSelected(prefs.getBoolean("notify", true));
		settings.add(label3);
		settings.add(rate);
		settings.add(label4);
		settings.add(box);
		
		JPanel buttons = new JPanel(new GridLayout(1,2));
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		buttons.add(save);
		buttons.add(cancel);
		
		mainPanel.add(login);
		mainPanel.add(settings);
		mainPanel.add(buttons);
		frame.add(mainPanel);
		//frame.pack();
		frame.setLocationRelativeTo(null);
		return frame;
	} // getOptionPanel
	
} // OptionPanel
