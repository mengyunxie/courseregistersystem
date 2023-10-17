/**
 * File: Toast.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a Toast class, it is a toast and used to display various message 
 */

package courseregistersystem.main.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Toast extends JDialog {

	private static int milliseconds = 2000;
	private JFrame gui;
	private String message;
	private Color color = Color.WHITE;

	/**
	 * This is the constructor of Toast.
	 * 
	 * @param _gui   it is the main container, an instance of GUI class
	 * @param _message The contents of the label in the toast
	 * @param _color the color of the label in the toast
	 */
	private Toast(GUI _gui, String _message, Color _color) {
		gui = _gui;
		message = _message;
		color = _color;
		buildComponent();
	}

	/**
	 * This is the method to show a toast.
	 * 
	 * @param _gui     it is the main container, an instance of GUI class
	 * @param _message The contents of the label in the toast
	 * @param _color   the color of the label in the toast
	 */
	public static void show(GUI _gui, String _message, Color _color) {
		JDialog dialog = new Toast(_gui, _message, _color);

		// Create a timer and turn off the toast after a few milliseconds
		Timer timer = new Timer(milliseconds, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);
	}

	/**
	 * Build the toast.
	 * 
	 */
	private void buildComponent() {

		setLayout(new GridBagLayout());
		setFocusableWindowState(false);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setSize(new Dimension(300, 50));
		setLocationRelativeTo(gui);
		getContentPane().setBackground(Color.DARK_GRAY);
		setOpacity(0.9f);
		// Give the window an rounded rect shape.
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
			}
		});

		JLabel messageLabel = new JLabel(message);
		messageLabel.setForeground(color);
		add(messageLabel);
	}
}
