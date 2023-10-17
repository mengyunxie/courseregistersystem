/**
 * File: JRadioButtonPanel.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a JRadioButtonPanel class, a JPanel contains JRadioButton serves as a custom JRadioButton.
 */

package courseregistersystem.main.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.util.Enumeration;

public class JRadioButtonPanel extends JPanel implements ItemListener {

	private String title = "";
	private ButtonGroup optGroup = new ButtonGroup();
	private JRadioButton[] options;
	private String[] optionLabels;
	private String selected;
	private boolean isEditing;

	/**
	 * This is the constructor of JRadioButtonPanel with two parameters.
	 * 
	 * @param _title        it is a contents of the title label
	 * @param _optionLabels the options of the JRadioButton
	 */
	public JRadioButtonPanel(String _title, String[] _optionLabels) {
		title = _title;
		optionLabels = _optionLabels;
		isEditing = true;
		buildComponent();
	}

	/**
	 * This is the constructor of JRadioButtonPanel with three parameters.
	 * 
	 * @param _title        it is a contents of the title label
	 * @param _optionLabels the options of the JRadioButton
	 * @param _isEditing    true indicates that JRadioButton is editable. Otherwise,
	 *                      it can not be edited.
	 */
	public JRadioButtonPanel(String _title, String[] _optionLabels, boolean _isEditing) {
		title = _title;
		optionLabels = _optionLabels;
		isEditing = _isEditing;
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

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_M));
		titleLabel.setSize(GUIConstants.WIDTH_110, GUIConstants.HEIGHT_20);
		titleLabel.setLocation(0, 0);
		add(titleLabel);

		options = new JRadioButton[optionLabels.length];

		for (int i = 0; i < options.length; i++) {
			options[i] = new JRadioButton(optionLabels[i]);
			options[i].setLocation(GUIConstants.WIDTH_110 + (GUIConstants.WIDTH_110 * i), 0);
			options[i].setSize(GUIConstants.WIDTH_110, GUIConstants.HEIGHT_20);
			options[i].addItemListener(this);
			add(options[i]);
			optGroup.add(options[i]);
		}
		options[0].setSelected(true);
		selected = options[0].getText();

		switchEditStatus(isEditing);
	}

	/**
	 * Get the value of the JRadioButton.
	 * 
	 */
	public String getSelectedValue() {
		return selected;
	}

	/**
	 * Set the value of the JRadioButton.
	 * 
	 */
	public void setSelected(String value) {
		for (int i = 0; i < options.length; i++) {
			if (options[i].getText().equals(value)) {
				options[i].setSelected(true);
			}
		}
	}

	/**
	 * Toggles the editable or not state of the component.
	 * 
	 */
	public void switchEditStatus(boolean _isEditing) {
		isEditing = _isEditing;
		Enumeration<AbstractButton> buttons = optGroup.getElements();
		while (buttons.hasMoreElements()) {
			buttons.nextElement().setEnabled(isEditing);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (int i = 0; i < options.length; i++) {
			if (options[i].isSelected()) {
				selected = options[i].getText();
			}
		}
	}

}
