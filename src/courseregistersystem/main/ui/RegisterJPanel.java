/**
 * File: RegisterJPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a RegisterJPanel class as a user create page.
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

public class RegisterJPanel extends JPanel {

	// Register page
	private JLabel pageTitleLabel;
	private JTextFieldPanelWithLabel usernameTextField;
	private JTextFieldPanelWithLabel passwordTextField;
	private JTextFieldPanelWithLabel emailTextField;
	private JRadioButtonPanel optPanel;
	private JButton loginButton;
	private JButton registerButton;
	private GUI gui;

	/**
	 * This is the constructor of RegisterJPanel.
	 * 
	 * @param _gui it is the main container, an instance of GUI class
	 */
	public RegisterJPanel(GUI _gui) {
		setLayout(null);
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
		// Initiate data
		gui = _gui;

		// Build the page title
		pageTitleLabel = new JLabel("Create a new account", SwingConstants.CENTER);
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
		panel.setSize(GUIConstants.WIDTH_500, GUIConstants.HEIGHT_280);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.GRAY, 1));

		usernameTextField = new JTextFieldPanelWithLabel("UserName: ", true);
		usernameTextField.setLocation(GUIConstants.MARGIN_100, GUIConstants.MARGIN_60);
		panel.add(usernameTextField);

		passwordTextField = new JTextFieldPanelWithLabel("Password: ", true, true);
		passwordTextField.setLocation(GUIConstants.MARGIN_100, GUIConstants.MARGIN_90);
		panel.add(passwordTextField);

		emailTextField = new JTextFieldPanelWithLabel("Email: ", true);
		emailTextField.setLocation(GUIConstants.MARGIN_100, GUIConstants.MARGIN_120);
		panel.add(emailTextField);

		optPanel = new JRadioButtonPanel("Role: ", new String[] { "Instructor", "Student" });
		optPanel.setLocation(GUIConstants.MARGIN_100, GUIConstants.MARGIN_150);
		panel.add(optPanel);

		loginButton = new JButton("Login");
		loginButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		loginButton.setLocation(GUIConstants.MARGIN_100 + GUIConstants.WIDTH_110, GUIConstants.MARGIN_200);
		// Click the login button, jumping to login page
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				gui.openNewWindow(gui.getRegisterJPanel(), gui.getLoginJPanel(), "Course Registration System");
			}
		});
		panel.add(loginButton);

		registerButton = new JButton("Register");
		registerButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		registerButton.setLocation(GUIConstants.MARGIN_100 + GUIConstants.WIDTH_110 * 2, GUIConstants.MARGIN_200);
		// Click the register button, jumping to the home page by this user
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidForm()) {
					long userId = SqliteDatabase.instance(gui).createUser(getForm());
					if (userId != -1) {
						clearForm();
						gui.initHomeJPanel(userId);
						gui.openNewWindow(gui.getRegisterJPanel(), gui.getHomeJPanel(), "Home");
					}
				} else {
					Toast.show(gui, "Form is not valid!", Color.RED);
				}

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
		emailTextField.clear();
		optPanel.setSelected("Instructor");
	}

	/**
	 * Check the validity of the form
	 * 
	 * @return true if all of the form is filled out; Otherwise, return false.
	 */
	private boolean isValidForm() {
		if (usernameTextField.getValue().isEmpty() || passwordTextField.getValue().isEmpty()
				|| emailTextField.getValue().isEmpty()) {
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
		Map<String, String> form = new HashMap<>();
		form.put("username", usernameTextField.getValue());
		form.put("password", passwordTextField.getValue());
		form.put("email", emailTextField.getValue());
		form.put("role", optPanel.getSelectedValue());
		return form;
	}
}
