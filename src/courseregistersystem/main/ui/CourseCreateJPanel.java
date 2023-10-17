/**
 * File: CourseCreateJPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a CourseCreateJPanel class as a course create page.
 */

package courseregistersystem.main.ui;

import courseregistersystem.main.database.SqliteDatabase;
import courseregistersystem.main.model.UserEntry;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CourseCreateJPanel extends JPanel {

	private JTextFieldPanelWithLabel courseNameTextField;
	private JTextFieldPanelWithLabel courseHoursTextField;
	private JTextFieldPanelWithLabel courseBuildingTextField;
	private JRadioButtonPanel optPanel;
	private JButton saveButton;
	private JButton cancelButton;
	private GUI gui;
	private UserEntry user;

	/**
	 * This is the constructor of CourseCreateJPanel.
	 * 
	 * @param _gui  it is the main container, an instance of GUI class
	 * @param _user the user data, it is an instance of UserEntry class
	 */
	public CourseCreateJPanel(GUI _gui, UserEntry _user) {
		setLayout(null);
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);

		// Initiate data
		gui = _gui;
		user = _user;

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
		panel.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_50);
		panel.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_280);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.GRAY, 1));

		JLabel panelTitle = new JLabel("Course Information");
		panelTitle.setFont(new Font("Arial", Font.BOLD, GUIConstants.FRONTSIZE_M));
		panelTitle.setSize(GUIConstants.WIDTH_150, GUIConstants.HEIGHT_30);
		panelTitle.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_10);
		panel.add(panelTitle);

		courseNameTextField = new JTextFieldPanelWithLabel("Course Name:", true);
		courseNameTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_60);
		panel.add(courseNameTextField);

		courseHoursTextField = new JTextFieldPanelWithLabel("Course Hours:", true);
		courseHoursTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_90);
		panel.add(courseHoursTextField);

		courseBuildingTextField = new JTextFieldPanelWithLabel("Building:", true);
		courseBuildingTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_120);
		panel.add(courseBuildingTextField);

		optPanel = new JRadioButtonPanel("Course Type: ", new String[] { "Online", "Ground" });
		optPanel.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_150);
		panel.add(optPanel);

		cancelButton = new JButton("Cancel");
		cancelButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		cancelButton.setLocation(GUIConstants.MARGIN_30 + GUIConstants.MARGIN_110, GUIConstants.MARGIN_200);
		// Click the cancel button
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.openNewWindow(gui.getCourseCreateJPanel(), gui.getHomeJPanel(), "Home");
			}
		});
		panel.add(cancelButton);

		saveButton = new JButton("Save");
		saveButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		saveButton.setLocation(GUIConstants.MARGIN_30 + GUIConstants.MARGIN_110 * 2, GUIConstants.MARGIN_200);
		// Click the save button, create a new course
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidForm()) {
					long id = SqliteDatabase.instance(gui).createCourse(user.getId(), getForm());
					if (id != -1) {
						gui.openNewWindow(gui.getCourseCreateJPanel(), gui.getHomeJPanel(), "Home");
						gui.getHomeJPanel().updateTable();
					}
				} else { // Some TextFields have no input values.
					Toast.show(gui, "Form is not valid!", Color.RED);
				}

			}
		});
		panel.add(saveButton);

		return panel;
	}

	/**
	 * Check the validity of the form.
	 * 
	 * @return true if all of the form is filled out; Otherwise, return false.
	 */
	private boolean isValidForm() {
		if (courseNameTextField.getValue().isEmpty() || courseHoursTextField.getValue().isEmpty()
				|| courseBuildingTextField.getValue().isEmpty()) {
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
		form.put("course_name", courseNameTextField.getValue());
		form.put("course_hours", courseHoursTextField.getValue());
		form.put("course_building", courseBuildingTextField.getValue());
		form.put("course_type", optPanel.getSelectedValue());
		return form;
	}
}
