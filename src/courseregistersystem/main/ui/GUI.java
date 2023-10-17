/**
 * File: GUI.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a GUI class, the main container to contain different pages, it’s a JFrame.
 */

package courseregistersystem.main.ui;

import courseregistersystem.main.model.UserEntry;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

	private LoginJPanel loginPage;
	private RegisterJPanel registerPage;
	private HomeJPanel homePage;
	private CourseJPanel coursePage;
	private CourseRegisterJPanel courseRegisterPage;
	private CourseCreateJPanel courseCreatePage;

	/**
	 * This is the constructor of GUI.
	 * 
	 */
	public GUI() {

		setTitle("Course Registration System"); // Set the title for the window
		setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT); // Set the location and size for the window
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		Container c = getContentPane();
		c.setLayout(null);

		// Build the login page
		loginPage = new LoginJPanel(this);
		c.add(loginPage);

		// Build the user register page
		registerPage = new RegisterJPanel(this);
	}

	/**
	 * Build the home page.
	 * 
	 * @param userId the current user's id
	 */
	public void initHomeJPanel(long userId) {
		homePage = new HomeJPanel(this, userId);
	}

	/**
	 * Build the course information page.
	 * 
	 * @param user     the current user's entity
	 * @param courseId the current course's id
	 */
	public void initCourseJPanel(UserEntry user, long courseId) {
		coursePage = new CourseJPanel(this, user, courseId);
	}

	/**
	 * Build the course register page.
	 * 
	 * @param user the current user's entity
	 */
	public void initCourseRegisterJPanel(UserEntry user) {
		courseRegisterPage = new CourseRegisterJPanel(this, user);
	}

	/**
	 * Build the course create page.
	 * 
	 * @param user the current user's entity
	 */
	public void initCourseCreateJPanel(UserEntry user) {
		courseCreatePage = new CourseCreateJPanel(this, user);
	}

	/**
	 * Get the login page.
	 * 
	 */
	public LoginJPanel getLoginJPanel() {
		return loginPage;
	}

	/**
	 * Get the user register page.
	 * 
	 */
	public RegisterJPanel getRegisterJPanel() {
		return registerPage;
	}

	/**
	 * Get the home page.
	 * 
	 * @return HomeJPanel
	 */
	public HomeJPanel getHomeJPanel() {
		return homePage;
	}

	/**
	 * Get course information page.
	 * 
	 * @return CourseJPanel
	 */
	public CourseJPanel getCourseJPanel() {
		return coursePage;
	}

	/**
	 * Get course create page.
	 * 
	 * @return CourseCreateJPanel
	 */
	public CourseCreateJPanel getCourseCreateJPanel() {
		return courseCreatePage;
	}

	/**
	 * Get course register page.
	 * 
	 * @return CourseRegisterJPanel
	 */
	public CourseRegisterJPanel getCourseRegisterJPanel() {
		return courseRegisterPage;
	}

	/**
	 * Clear related pages for logout.
	 * 
	 */
	public void resetJPanels() {
		homePage = null;
		coursePage = null;
		courseCreatePage = null;
		courseRegisterPage = null;
	}

	/**
	 * This method implements page hopping.
	 * 
	 */
	public void openNewWindow(JPanel fromPanel, JPanel toPanel, String title) {
		setTitle(title);
		fromPanel.setVisible(false);
		toPanel.setVisible(true);
		getContentPane().add(toPanel);
	}
}
