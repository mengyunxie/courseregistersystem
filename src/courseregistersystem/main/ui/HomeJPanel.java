/**
 * File: HomeJPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a HomeJPanel class, a JPanel serves as a home page.
 */

package courseregistersystem.main.ui;

import courseregistersystem.main.database.SqliteDatabase;
import courseregistersystem.main.model.CourseEntry;
import courseregistersystem.main.model.UserEntry;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class HomeJPanel extends JPanel implements JTablePanelWithJButtonCallBack {

	// Home page
	private JTextFieldPanelWithLabel usernameTextField;
	private JTextFieldPanelWithLabel emailTextField;
	private JTextFieldPanelWithLabel addressTextField;
	private JTextFieldPanelWithLabel firstnameTextField;
	private JTextFieldPanelWithLabel lastnameTextField;
	private JTextFieldPanelWithLabel birthdayTextField;
	private JButton editUserButton;
	private JButton saveUserButton;
	private JButton cancelEditButton;
	private JTablePanelWithOneJButton instructorCourseTableWithOneBtn;
	private JTablePanelWithTwoJButtons studentCourseTableWithTwoBtn;

	private GUI gui;
	private UserEntry user;

	/**
	 * This is the constructor of HomeJPanel.
	 * 
	 * @param _gui   it is the main container, an instance of GUI class
	 * @param userId the current user's id
	 */
	public HomeJPanel(GUI _gui, long userId) {
		setLayout(null);
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);

		// Initiate data
		gui = _gui;
		getUserData(userId);

		// Build the userInfo panel
		JPanel userInfoPanel = buildUserInfoPanel();
		add(userInfoPanel);

		// Build the JTable
		JPanel tablePanel = buildTablePanel();
		add(tablePanel);
	}

	/**
	 * Build the user information form panel.
	 * 
	 * @return JPanel
	 */
	private JPanel buildUserInfoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_30);
		panel.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_200);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.GRAY, 1));

		JLabel panelTitle = new JLabel("Account Information");
		panelTitle.setFont(new Font("Arial", Font.BOLD, GUIConstants.FRONTSIZE_M));
		panelTitle.setSize(GUIConstants.WIDTH_200, GUIConstants.HEIGHT_30);
		panelTitle.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_10);
		panel.add(panelTitle);

		usernameTextField = new JTextFieldPanelWithLabel("UserName: ");
		usernameTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_60);
		usernameTextField.setValue(user.getUserName());
		panel.add(usernameTextField);

		firstnameTextField = new JTextFieldPanelWithLabel("First Name: ");
		firstnameTextField.setLocation(GUIConstants.MARGIN_370, GUIConstants.MARGIN_60);
		firstnameTextField.setValue(user.getFirstName());
		panel.add(firstnameTextField);

		emailTextField = new JTextFieldPanelWithLabel("Email: ");
		emailTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_90);
		emailTextField.setValue(user.getEmail());
		panel.add(emailTextField);

		lastnameTextField = new JTextFieldPanelWithLabel("Last Name: ");
		lastnameTextField.setLocation(GUIConstants.MARGIN_370, GUIConstants.MARGIN_90);
		lastnameTextField.setValue(user.getLastName());
		panel.add(lastnameTextField);

		addressTextField = new JTextFieldPanelWithLabel("Address: ");
		addressTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_120);
		addressTextField.setValue(user.getAddress());
		panel.add(addressTextField);

		birthdayTextField = new JTextFieldPanelWithLabel("Birthday: ");
		birthdayTextField.setLocation(GUIConstants.MARGIN_370, GUIConstants.MARGIN_120);
		birthdayTextField.setValue(user.getBirthday());
		panel.add(birthdayTextField);

		// Build the action label
		JLabel actionLabel = new JLabel("Action: ");
		actionLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_M));
		actionLabel.setSize(GUIConstants.WIDTH_110, GUIConstants.HEIGHT_20);
		actionLabel.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_160);
		panel.add(actionLabel);

		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		logoutButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		logoutButton.setLocation(GUIConstants.MARGIN_30 + GUIConstants.WIDTH_110, GUIConstants.MARGIN_160);
		// Click the logout button and jump to the login page
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = null;
				gui.openNewWindow(gui.getHomeJPanel(), gui.getLoginJPanel(), "Course Registration System");
				gui.resetJPanels();
			}
		});
		panel.add(logoutButton);

		editUserButton = new JButton("Edit");
		editUserButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		editUserButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		editUserButton.setLocation(GUIConstants.MARGIN_30 + GUIConstants.WIDTH_110 * 2, GUIConstants.MARGIN_160);
		panel.add(editUserButton);

		cancelEditButton = new JButton("Cancel");
		cancelEditButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		cancelEditButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		cancelEditButton.setLocation(GUIConstants.MARGIN_30 + GUIConstants.WIDTH_110 * 2, GUIConstants.MARGIN_160);
		panel.add(cancelEditButton);

		saveUserButton = new JButton("Save");
		saveUserButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		saveUserButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		saveUserButton.setLocation(GUIConstants.MARGIN_30 + GUIConstants.WIDTH_110 * 3, GUIConstants.MARGIN_160);
		panel.add(saveUserButton);

		switchEditStatus(false);

		// Click the edit button to make the user information form editable
		editUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchEditStatus(true);
			}
		});

		// Click the save button to update the user's information
		saveUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> form = getUserDataForm();
				SqliteDatabase.instance(gui).updateUser(user.getId(), form);
				updateUserData(form);
				switchEditStatus(false);
			}
		});

		// Click the cancel button to cancel this modification and make the user
		// information form uneditable
		cancelEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstnameTextField.setValue(user.getFirstName());
				lastnameTextField.setValue(user.getLastName());
				addressTextField.setValue(user.getAddress());
				birthdayTextField.setValue(user.getBirthday());
				switchEditStatus(false);
			}
		});

		return panel;
	}

	/**
	 * Build the JTable panel.
	 * 
	 * @return JPanel
	 */
	private JPanel buildTablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_260);
		panel.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_280);

		JLabel tableTitleLabel = new JLabel("Course List");
		tableTitleLabel.setFont(new Font("Arial", Font.BOLD, GUIConstants.FRONTSIZE_M));
		tableTitleLabel.setSize(GUIConstants.WIDTH_150, GUIConstants.HEIGHT_20);
		tableTitleLabel.setLocation(0, GUIConstants.MARGIN_20);
		panel.add(tableTitleLabel);

		JLabel tableButtonLabel = new JLabel("", SwingConstants.RIGHT);
		tableButtonLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		tableButtonLabel.setSize(GUIConstants.WIDTH_150, GUIConstants.HEIGHT_20);
		tableButtonLabel.setLocation(GUIConstants.MARGIN_520, GUIConstants.MARGIN_20);
		panel.add(tableButtonLabel);

		JButton addCourseButton = new JButton("+");
		JButton registerCourseButton = new JButton("+");

		if (user != null && user.getRole().equals("Instructor")) { // The current user's role is "Instructor".
			tableButtonLabel.setText("Create a new course");

			addCourseButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
			addCourseButton.setSize(GUIConstants.WIDTH_20, GUIConstants.HEIGHT_20);
			addCourseButton.setLocation(GUIConstants.MARGIN_680, GUIConstants.MARGIN_20);
			// Click the add course button, jump to the course create page.
			addCourseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gui.initCourseCreateJPanel(user);
					gui.openNewWindow(gui.getHomeJPanel(), gui.getCourseCreateJPanel(), "Create a new course");
				}
			});
			panel.add(addCourseButton);

			instructorCourseTableWithOneBtn = new JTablePanelWithOneJButton(getTableData(), getTableColumnNames(),
					new String[] { "View" }, this);
			instructorCourseTableWithOneBtn.setOpaque(true);
			instructorCourseTableWithOneBtn.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_200);
			instructorCourseTableWithOneBtn.setLocation(0, GUIConstants.MARGIN_50);
			panel.add(instructorCourseTableWithOneBtn);

		} else { // The current user's role is "Student".
			tableButtonLabel.setText("Register a new course");

			registerCourseButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
			registerCourseButton.setSize(GUIConstants.WIDTH_20, GUIConstants.HEIGHT_20);
			registerCourseButton.setLocation(GUIConstants.MARGIN_680, GUIConstants.MARGIN_20);
			// Click the register course button, jump to the course register page.
			registerCourseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gui.initCourseRegisterJPanel(user);
					gui.openNewWindow(gui.getHomeJPanel(), gui.getCourseRegisterJPanel(), "Register a new course");
				}
			});
			panel.add(registerCourseButton);

			studentCourseTableWithTwoBtn = new JTablePanelWithTwoJButtons(getTableData(), getTableColumnNames(),
					new String[] { "View", "Pending", "Drop" }, this);
			studentCourseTableWithTwoBtn.setOpaque(true);
			studentCourseTableWithTwoBtn.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_200);
			studentCourseTableWithTwoBtn.setLocation(0, GUIConstants.MARGIN_50);
			panel.add(studentCourseTableWithTwoBtn);
		}

		return panel;
	}

	/**
	 * Toggles the editable or not state of the user information form panel.
	 * 
	 */
	private void switchEditStatus(boolean isEditing) {
		firstnameTextField.switchEditStatus(isEditing);
		lastnameTextField.switchEditStatus(isEditing);
		addressTextField.switchEditStatus(isEditing);
		birthdayTextField.switchEditStatus(isEditing);

		editUserButton.setVisible(!isEditing);
		saveUserButton.setVisible(isEditing);
		cancelEditButton.setVisible(isEditing);
	}

	/**
	 * Get the current user's data.
	 * 
	 */
	private void getUserData(long userId) {
		// Clear the user
		user = null;
		// Initiate the user
		user = SqliteDatabase.instance(gui).getUser(userId);
	}

	/**
	 * Update the data and view of the user information form panel.
	 * 
	 */
	private void updateUserData(Map<String, String> form) {
		user.setFirstName(form.get("first_name"));
		user.setLastName(form.get("last_name"));
		user.setAddress(form.get("address"));
		user.setBirthday(form.get("birthday"));

		firstnameTextField.setValue(user.getFirstName());
		lastnameTextField.setValue(user.getLastName());
		addressTextField.setValue(user.getAddress());
		birthdayTextField.setValue(user.getBirthday());
	}

	/**
	 * Returns the values of the form.
	 * 
	 * @return Map<String, String>
	 */
	private Map<String, String> getUserDataForm() {
		Map<String, String> form = new HashMap<>();
		form.put("first_name", firstnameTextField.getValue());
		form.put("last_name", lastnameTextField.getValue());
		form.put("address", addressTextField.getValue());
		form.put("birthday", birthdayTextField.getValue());
		return form;
	}

	/**
	 * Get the columnNames for the JTable.
	 * 
	 * @return columnNames it is String[].
	 */
	private String[] getTableColumnNames() {
		// Column Names
		String[] columnNames = { "ID", "Name", "Hours", "Type", "Building", "Action" };
		return columnNames;
	}

	/**
	 * Get the data for the JTable.
	 * 
	 * @return data it is String[][].
	 */
	private String[][] getTableData() {
		List<CourseEntry> courseEntries = new ArrayList<>();
		if (user != null && user.getRole().equals("Instructor")) {
			courseEntries = SqliteDatabase.instance(gui).getInstructorCourseList(user.getId());
		} else {
			courseEntries = SqliteDatabase.instance(gui).getStudentCourseList(user.getId());
		}

		String[][] data = null;
		if (courseEntries != null && courseEntries.size() > 0) {
			data = new String[courseEntries.size()][getTableColumnNames().length];
			for (int i = 0; i < courseEntries.size(); i++) {
				CourseEntry item = courseEntries.get(i);
				for (int j = 0; j < data[i].length - 1; j++) {
					data[i][j] = item.getValueByIndex(j).toString();
				}
				data[i][data[i].length - 1] = item.getActionState();
			}
		}
		return data;
	}

	/**
	 * Update the data for the JTable.
	 * 
	 */
	public void updateTable() {
		if (user != null && user.getRole().equals("Instructor")) { // The current user's role is "Instructor".
			instructorCourseTableWithOneBtn.updateDataVector(getTableData(), getTableColumnNames());
		} else { // The current user's role is "Student".
			studentCourseTableWithTwoBtn.updateDataVector(getTableData(), getTableColumnNames());
		}
	}

	/**
	 * Issue the click event of the button in the JTable.
	 * 
	 */
	@Override
	public void initCallBack(long rowId, String label) {
		if (label == "View") {
			gui.initCourseJPanel(this.user, rowId);
			gui.openNewWindow(gui.getHomeJPanel(), gui.getCourseJPanel(), "Course Info");
		} else if (label == "Drop") {
			SqliteDatabase.instance(gui).dropCourse(user.getId(), rowId);
			studentCourseTableWithTwoBtn.updateDataVector(getTableData(), getTableColumnNames());
		}
	}

}
