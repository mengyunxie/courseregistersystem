/**
 * File: AppDriver.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a AppDriver class, the entry of the system.
 */

package courseregistersystem.main;

import courseregistersystem.main.ui.*;
import java.awt.event.*;

public class AppDriver {
	public static void main(String args[]) {
		
		GUI pane = new GUI(); // Declare and Instantiate
		pane.setVisible(true);
		
		pane.addWindowListener(new WindowAdapter() { // listening the exit action
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
