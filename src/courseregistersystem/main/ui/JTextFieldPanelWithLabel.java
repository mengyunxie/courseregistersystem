/**
 * File: JTextFieldPanelWithLabel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a JTextFieldPanelWithLabel class, build a component that has one TextField with a title label.
 */

package courseregistersystem.main.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class JTextFieldPanelWithLabel extends JPanel {

	private String title = "Your title: ";
	private JTextField input;
	private JLabel inputLabel;
	private boolean isEditing;
	private boolean isPassword = false;

	/**
	 * This is the constructor of JTextFieldPanelWithLabel with one parameter.
	 * 
	 * @param _title it is a contents of the title label
	 */
	public JTextFieldPanelWithLabel(String _title) {
		title = _title;
		buildComponent();
	}

	/**
	 * This is the constructor of JTextFieldPanelWithLabel with two parameters.
	 * 
	 * @param _title     it is a contents of the title label
	 * @param _isEditing true indicates that JTextField is displayed and values can
	 *                   be entered. Otherwise, it's just a JLabel.
	 */
	public JTextFieldPanelWithLabel(String _title, boolean _isEditing) {
		title = _title;
		isEditing = _isEditing;
		buildComponent();
	}

	/**
	 * This is the constructor of JTextFieldPanelWithLabel with three parameters.
	 * 
	 * @param _title      it is a contents of the title label
	 * @param _isEditing  true indicates that JTextField is displayed and values can
	 *                    be entered. Otherwise, it's just a JLabel.
	 * @param _isPassword true indicates that JPasswordField is displayed, false
	 *                    indicates that JTextField is displayed
	 */
	public JTextFieldPanelWithLabel(String _title, boolean _isEditing, boolean _isPassword) {
		title = _title;
		isEditing = _isEditing;
		isPassword = _isPassword;
		buildComponent();
	}

	/**
	 * Build the component.
	 * 
	 */
	private void buildComponent() {
		setLayout(null);
		setSize(GUIConstants.WIDTH_300, GUIConstants.HEIGHT_20);
		setBackground(Color.WHITE);

		// Build the title label
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_M));
		titleLabel.setSize(GUIConstants.WIDTH_110, GUIConstants.HEIGHT_20);
		titleLabel.setLocation(0, 0);
		add(titleLabel);

		// Build the TextField
		if (isPassword) { // The TextField is for password
			input = new JPasswordField();
		} else {
			input = new JTextField();
		}
		input.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		input.setBorder(new LineBorder(Color.GRAY, 1));
		input.setSize(GUIConstants.WIDTH_190, GUIConstants.HEIGHT_20);
		input.setLocation(GUIConstants.MARGIN_110, 0);
		add(input);

		inputLabel = new JLabel();
		inputLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_S));
		inputLabel.setSize(GUIConstants.WIDTH_190, GUIConstants.HEIGHT_20);
		inputLabel.setLocation(GUIConstants.MARGIN_110, 0);
		add(inputLabel);

		switchEditStatus(isEditing);
	}

	/**
	 * Get the value of the TextField.
	 * 
	 */
	public String getValue() {
		return isEditing ? input.getText() : inputLabel.getText();
	}

	/**
	 * Set the value of the TextField and the label.
	 * 
	 */
	public void setValue(String val) {
		input.setText(val);
		inputLabel.setText(val);
	}

	/**
	 * Clear the value of the TextField and the label.
	 * 
	 */
	public void clear() {
		input.setText("");
		inputLabel.setText("");
	}

	/**
	 * Toggles the editable or not state of the component.
	 * 
	 */
	public void switchEditStatus(boolean _isEditing) {
		isEditing = _isEditing;
		input.setVisible(isEditing);
		inputLabel.setVisible(!isEditing);
	}

}
