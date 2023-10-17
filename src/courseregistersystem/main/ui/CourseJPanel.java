/**
 * File: CourseJPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a CourseJPanel class, a JPanel serves as a course information page.
 */

package courseregistersystem.main.ui;

import courseregistersystem.main.database.SqliteDatabase;
import courseregistersystem.main.model.CourseEntry;
import courseregistersystem.main.model.UserEntry;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CourseJPanel extends JPanel implements JTablePanelWithJButtonCallBack {

	// Course Info page
	private JTextFieldPanelWithLabel courseNameTextField;
	private JTextFieldPanelWithLabel courseHoursTextField;
	private JTextFieldPanelWithLabel courseBuildingTextField;
	private JRadioButtonPanel optPanel;
	private JButton gobackButton;
	private JButton editCourseButton;
	private JButton saveCourseButton;
	private JButton cancelEditButton;
	private JLabel actionLabel;
	private JLabel tableTitleLabel;
	private JTablePanelWithTwoJButtons table;

	private UserEntry user;
	private CourseEntry course;
	private long courseId;
	private GUI gui;

	/**
	 * This is the constructor of CourseJPanel.
	 * 
	 * @param _gui      it is the main container, an instance of GUI class
	 * @param _user     the current user's entity
	 * @param _courseId the current course's id
	 */
	public CourseJPanel(GUI _gui, UserEntry _user, long _courseId) {
		setLayout(null);
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);

		gui = _gui;
		user = _user;
		courseId = _courseId;
		getCourseData(courseId);

		gobackButton = new JButton("Goback");
		gobackButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		gobackButton.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_30);
		// Click the goBack button and jump to the home page
		gobackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.openNewWindow(gui.getCourseJPanel(), gui.getHomeJPanel(), "Home");
			}
		});
		add(gobackButton);

		JPanel formPanel = buildFormPanel();
		add(formPanel);

		// Display this JTable, if the current user's role is "Instructor"
		if (user != null && user.getRole().equals("Instructor")) {
			JPanel tablePanel = buildTablePanel();
			add(tablePanel);
		}
	}

	/**
	 * Build the course information form panel.
	 * 
	 * @return JPanel
	 */
	private JPanel buildFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_70);
		panel.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_250);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.GRAY, 1));

		JLabel panelTitle = new JLabel("Course Information");
		panelTitle.setFont(new Font("Arial", Font.BOLD, GUIConstants.FRONTSIZE_M));
		panelTitle.setSize(GUIConstants.WIDTH_150, GUIConstants.HEIGHT_30);
		panelTitle.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_15);
		panel.add(panelTitle);

		courseNameTextField = new JTextFieldPanelWithLabel("Course Name:");
		courseNameTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_60);
		courseNameTextField.setValue(course.getCourseName());
		panel.add(courseNameTextField);

		courseHoursTextField = new JTextFieldPanelWithLabel("Course Hours:");
		courseHoursTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_90);
		courseHoursTextField.setValue(course.getCourseHours());
		panel.add(courseHoursTextField);

		courseBuildingTextField = new JTextFieldPanelWithLabel("Building:");
		courseBuildingTextField.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_120);
		courseBuildingTextField.setValue(course.getBuilding());
		panel.add(courseBuildingTextField);

		optPanel = new JRadioButtonPanel("Course Type: ", new String[] { "Online", "Ground" }, false);
		optPanel.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_150);
		optPanel.setSelected(course.getCourseType());
		panel.add(optPanel);

		if (user != null && user.getRole().equals("Instructor")) { // The current user's role is "Instructor"

			// Build the action label
			actionLabel = new JLabel("Action: ");
			actionLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_M));
			actionLabel.setSize(GUIConstants.WIDTH_110, GUIConstants.HEIGHT_20);
			actionLabel.setLocation(GUIConstants.MARGIN_30, GUIConstants.MARGIN_200);
			panel.add(actionLabel);

			editCourseButton = new JButton("Edit");
			editCourseButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
			editCourseButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
			editCourseButton.setLocation(GUIConstants.MARGIN_140, GUIConstants.MARGIN_200);
			panel.add(editCourseButton);

			cancelEditButton = new JButton("Cancel");
			cancelEditButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
			cancelEditButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
			cancelEditButton.setLocation(GUIConstants.MARGIN_140, GUIConstants.MARGIN_200);
			panel.add(cancelEditButton);

			saveCourseButton = new JButton("Save");
			saveCourseButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
			saveCourseButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
			saveCourseButton.setLocation(GUIConstants.MARGIN_140 + GUIConstants.MARGIN_110, GUIConstants.MARGIN_200);
			panel.add(saveCourseButton);

			// Click the edit button to make the course information form editable
			editCourseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switchEditStatus(true);
				}
			});

			// Click the cancel button to cancel this modification and make the course
			// information form uneditable
			cancelEditButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					courseNameTextField.setValue(course.getCourseName());
					courseHoursTextField.setValue(course.getCourseHours());
					courseBuildingTextField.setValue(course.getBuilding());
					optPanel.setSelected(course.getCourseType());
					switchEditStatus(false);
				}
			});

			// Click the save button to update the course's information
			saveCourseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Map<String, String> form = getForm();
					if (isValidForm()) {
						SqliteDatabase.instance(gui).updateCourse(courseId, getForm());
						updateCourseData(form);
						switchEditStatus(false);
						gui.getHomeJPanel().updateTable();
					} else {
						Toast.show(gui, "Form is not valid!", Color.RED);
					}

				}
			});
		}

		switchEditStatus(false);

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
		panel.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_320);
		panel.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_280);

		tableTitleLabel = new JLabel("Students");
		tableTitleLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_M));
		tableTitleLabel.setSize(GUIConstants.WIDTH_150, GUIConstants.HEIGHT_20);
		tableTitleLabel.setLocation(0, GUIConstants.MARGIN_20);
		panel.add(tableTitleLabel);

		table = new JTablePanelWithTwoJButtons(getTableData(), getTableColumnNames(),
				new String[] { "", "Approve/Decline", "Enrolled" }, this);
		table.setOpaque(true);
		table.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_160);
		table.setLocation(0, GUIConstants.MARGIN_50);
		panel.add(table);

		return panel;
	}

	/**
	 * Toggles the editable or not state of the course information form panel.
	 * 
	 */
	private void switchEditStatus(boolean isEditing) {
		courseNameTextField.switchEditStatus(isEditing);
		courseHoursTextField.switchEditStatus(isEditing);
		courseBuildingTextField.switchEditStatus(isEditing);
		optPanel.switchEditStatus(isEditing);

		// The current user's role is "Instructor".
		if (user != null && user.getRole().equals("Instructor")) {
			editCourseButton.setVisible(!isEditing);
			saveCourseButton.setVisible(isEditing);
			cancelEditButton.setVisible(isEditing);
		}
	}

	/**
	 * Get the current course's data.
	 * 
	 */
	private void getCourseData(long id) {
		// Clear the course
		course = null;
		// Initiate the course
		course = SqliteDatabase.instance(gui).getCourse(id);
	}

	/**
	 * Get the columnNames for the JTable.
	 * 
	 * @return columnNames it is String[].
	 */
	private String[] getTableColumnNames() {
		// Column Names
		String[] columnNames = { "ID", "Name", "Email", "Action" };
		return columnNames;
	}

	/**
	 * Get the data for the JTable.
	 * 
	 * @return data it is String[][].
	 */
	private String[][] getTableData() {
		List<UserEntry> userEntries = SqliteDatabase.instance(gui).getUserListByCourseId(courseId);
		String[][] data;
		if (userEntries != null && userEntries.size() > 0) {
			data = new String[userEntries.size()][getTableColumnNames().length];
			for (int i = 0; i < userEntries.size(); i++) {
				UserEntry item = userEntries.get(i);
				for (int j = 0; j < data[i].length - 1; j++) {
					data[i][j] = item.getValueByIndex(j).toString();
				}
				data[i][data[i].length - 1] = item.getActionState();
			}
		} else {
			data = null;
		}
		return data;
	}

	/**
	 * Returns the values of the form.
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getForm() {
		Map<String, String> form = new HashMap<>();
		form.put("course_name", courseNameTextField.getValue());
		form.put("course_hours", courseHoursTextField.getValue());
		form.put("course_building", courseBuildingTextField.getValue());
		form.put("course_type", optPanel.getSelectedValue());
		return form;
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
	 * Update the data and view of the course information form panel.
	 * 
	 */
	private void updateCourseData(Map<String, String> form) {
		course.setCourseName(form.get("course_name"));
		course.setCourseHours(form.get("course_hours"));
		course.setCourseBuilding(form.get("course_building"));
		course.setCourseType(form.get("course_type"));

		courseNameTextField.setValue(course.getCourseName());
		courseHoursTextField.setValue(course.getCourseHours());
		courseBuildingTextField.setValue(course.getBuilding());
		optPanel.setSelected(course.getCourseType());
	}

	/**
	 * Issue the click event of the button in the JTable.
	 * 
	 */
	@Override
	public void initCallBack(long rowId, String label) {
		if (label == "Approve") {
			SqliteDatabase.instance(gui).approveCourseRequest(rowId, courseId);
		} else if (label == "Decline") {
			SqliteDatabase.instance(gui).declineCourseRequest(rowId, courseId);
		}
		table.updateDataVector(getTableData(), getTableColumnNames());
		gui.getHomeJPanel().updateTable();
	}

}
