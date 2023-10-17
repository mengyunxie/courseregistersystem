/**
 * File: LoginJPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a LoginJPanel class as a login page.
 */

package courseregistersystem.main.ui;

import courseregistersystem.main.database.SqliteDatabase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class LoginJPanel extends JPanel {

	// Login page
	private JTextFieldPanelWithLabel usernameTextField;
	private JTextFieldPanelWithLabel passwordTextField;
	private GUI gui;

	/**
	 * This is the constructor of LoginJPanel.
	 * 
	 * @param _gui it is the main container, an instance of GUI class
	 */
	public LoginJPanel(GUI _gui) {
		setLayout(null);
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);

		// Initiate data
		gui = _gui;

		// Build the page title
		JLabel pageTitleLabel = new JLabel("Login", SwingConstants.CENTER);
		pageTitleLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_XL));
		pageTitleLabel.setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.HEIGHT_30);
		pageTitleLabel.setLocation(0, GUIConstants.MARGIN_70);
		add(pageTitleLabel);

		// Build the form panel
		JPanel formPanel = buildFormPanel();
		add(formPanel);
	}

	/**
	 * Build the form panel.
	 * 
	 * @return JPanel
	 */
	private JPanel buildFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setLocation(GUIConstants.MARGIN_150, GUIConstants.MARGIN_120);
		panel.setSize(GUIConstants.WIDTH_500, GUIConstants.HEIGHT_200);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.GRAY, 1));

		usernameTextField = new JTextFieldPanelWithLabel("UserName: ", true);
		usernameTextField.setLocation(GUIConstants.MARGIN_100, GUIConstants.MARGIN_60);
		panel.add(usernameTextField);

		passwordTextField = new JTextFieldPanelWithLabel("Password: ", true, true);
		passwordTextField.setLocation(GUIConstants.MARGIN_100, GUIConstants.MARGIN_90);
		panel.add(passwordTextField);

		JButton loginButton = new JButton("Login");
		loginButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		loginButton.setLocation(GUIConstants.MARGIN_100 + GUIConstants.WIDTH_110, GUIConstants.MARGIN_140);
		// Click the login button, jumping to the home page by this user
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidForm()) {
					long userId = SqliteDatabase.instance(gui).userLogin(getForm());
					if (userId != -1) {
						clearForm();
						gui.initHomeJPanel(userId);
						gui.openNewWindow(gui.getLoginJPanel(), gui.getHomeJPanel(), "Home");
					}
				} else {
					Toast.show(gui, "Form is not valid!", Color.RED);
				}

			}
		});
		panel.add(loginButton);

		JButton registerButton = new JButton("Register");
		registerButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		registerButton.setLocation(GUIConstants.MARGIN_100 + GUIConstants.WIDTH_110 * 2, GUIConstants.MARGIN_140);
		// Click the register button, jumping to register page
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				gui.openNewWindow(gui.getLoginJPanel(), gui.getRegisterJPanel(), "Course Registration System");
			}
		});
		panel.add(registerButton);

		return panel;
	}

	/**
	 * Clear the values of the form.
	 * 
	 */
	private void clearForm() {
		usernameTextField.clear();
		passwordTextField.clear();
	}

	/**
	 * Check the validity of the form.
	 * 
	 * @return true if all of the form is filled out; Otherwise, return false.
	 */
	private boolean isValidForm() {
		if (usernameTextField.getValue().isEmpty() || passwordTextField.getValue().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the values of the form.
	 * 
	 * @return Map<String, String>
	 */
	private Map<String, String> getForm() {
		System.out.println(passwordTextField.getValue());
		Map<String, String> form = new HashMap<>();
		form.put("username", usernameTextField.getValue());
		form.put("password", passwordTextField.getValue());
		return form;
	}
}
