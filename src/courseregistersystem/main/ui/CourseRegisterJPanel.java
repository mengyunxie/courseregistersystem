/**
 * File: CourseRegisterJPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a CourseRegisterJPanel class, a JPanel serves as a course register page.
 */

package courseregistersystem.main.ui;

import courseregistersystem.main.database.SqliteDatabase;
import courseregistersystem.main.model.CourseEntry;
import courseregistersystem.main.model.UserEntry;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CourseRegisterJPanel extends JPanel implements JTablePanelWithJButtonCallBack {

	// Register a new course page
	private JTablePanelWithOneJButton table;
	private UserEntry user;
	private GUI gui;

	/**
	 * This is the constructor of CourseRegisterJPanel.
	 * 
	 * @param _gui  it is the main container, an instance of GUI class
	 * @param _user the current user's entity
	 */
	public CourseRegisterJPanel(GUI _gui, UserEntry _user) {
		setLayout(null);
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);

		// Initiate data
		gui = _gui;
		user = _user;

		JButton gobackButton = new JButton("Goback");
		gobackButton.setSize(GUIConstants.WIDTH_80, GUIConstants.HEIGHT_20);
		gobackButton.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_30);
		// Click the goBack button and jump to the home page
		gobackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.openNewWindow(gui.getCourseRegisterJPanel(), gui.getHomeJPanel(), "Home");
			}
		});
		add(gobackButton);

		// Build the JTable
		JPanel tablePanel = buildTablePanel();
		add(tablePanel);
	}

	/**
	 * Build the JTable panel.
	 * 
	 * @return JPanel
	 */
	private JPanel buildTablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setLocation(GUIConstants.MARGIN_50, GUIConstants.MARGIN_70);
		panel.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_350);

		JLabel tableTitleLabel = new JLabel("All Courses List");
		tableTitleLabel.setFont(new Font("Arial", Font.BOLD, GUIConstants.FRONTSIZE_M));
		tableTitleLabel.setSize(GUIConstants.WIDTH_150, GUIConstants.HEIGHT_20);
		tableTitleLabel.setLocation(0, GUIConstants.MARGIN_10);
		panel.add(tableTitleLabel);

		table = new JTablePanelWithOneJButton(getTableData(user.getId()), getTableColumnNames(),
				new String[] { "Request", "Pending", "Enrolled" }, this);
		table.setOpaque(true);
		table.setSize(GUIConstants.WIDTH_700, GUIConstants.HEIGHT_280);
		table.setLocation(0, GUIConstants.MARGIN_40);
		panel.add(table);

		return panel;
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
	private String[][] getTableData(long studentid) {
		List<CourseEntry> courseEntries = SqliteDatabase.instance(gui).getCourseList(studentid);
		String[][] data;
		if (courseEntries != null && courseEntries.size() > 0) {
			data = new String[courseEntries.size()][getTableColumnNames().length];
			for (int i = 0; i < courseEntries.size(); i++) {
				CourseEntry item = courseEntries.get(i);
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
	 * Issue the click event of the button in the JTable.
	 * 
	 */
	@Override
	public void initCallBack(long rowId, String label) {
		if (label == "Request") {
			SqliteDatabase.instance(gui).registerCourse(user.getId(), rowId);
			table.updateDataVector(getTableData(user.getId()), getTableColumnNames());
			gui.getHomeJPanel().updateTable();
		}
	}
}
