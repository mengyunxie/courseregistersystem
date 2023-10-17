/**
 * File: JTablePanelWithOneJButton.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a JTablePanelWithOneJButton class, A JPanel contains a JTable, and one button serves as a custom JTable.
 */

package courseregistersystem.main.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class JTablePanelWithOneJButton extends JPanel {
	private Object[][] data;
	private Object[] columnNames;
	private JTablePanelWithJButtonCallBack callBack;
	private OneButtonModel tableModel;
	private JTable table;
	private String[] buttonLabelArray;

	/**
	 * This is the constructor of JTablePanelWithOneJButton.
	 * 
	 * @param _data             it is the data of the JTable
	 * @param _columnNames      it is the column name of the JTable
	 * @param _buttonLabelArray it is the various action of the button in the JTable
	 * @param _callBack         it is the callback after clicking the button
	 */
	public JTablePanelWithOneJButton(Object[][] _data, Object[] _columnNames, String[] _buttonLabelArray,
			JTablePanelWithJButtonCallBack _callBack) {
		super(new GridLayout(1, 0));

		// Initiate data
		data = _data;
		columnNames = _columnNames;
		callBack = _callBack;
		buttonLabelArray = _buttonLabelArray;

		// Create a custom table model for the JTable
		tableModel = new OneButtonModel(columnNames.length);
		tableModel.setDataVector(data, columnNames);

		// Build the JTable
		table = new JTable(tableModel);
		table.getColumn("Action").setCellRenderer(new OneButtonRenderer(buttonLabelArray));
		table.getColumn("Action").setCellEditor(new OneButtonEditor(buttonLabelArray, callBack));
		table.setFillsViewportHeight(true);
		table.setSelectionBackground(Color.WHITE);
		table.setSelectionForeground(Color.BLACK);

		// Set the reasonable width of each column of the JTable
		initColumnSizes(table);

		// Create the scroll pane and add the JTable to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	/**
	 * Build the button panel.
	 * 
	 * @return OneButtonPanel
	 */
	class OneButtonPanel extends JPanel {
		private JButton button;

		public OneButtonPanel(String[] _buttonLabelArray) {
			setLayout(null);
			setBackground(Color.WHITE);
			this.button = new JButton(_buttonLabelArray[0]);
			this.button.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_XS));
			this.button.setBounds(0, 0, GUIConstants.WIDTH_60, GUIConstants.HEIGHT_14);
			this.button.setHorizontalAlignment(SwingConstants.LEFT);
			this.button.setOpaque(true);
			this.button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
			this.button.setFocusPainted(false);
			this.button.setBackground(Color.WHITE);
			this.button.setForeground(Color.BLUE);
			add(button);
		}

		public JButton getButton() {
			return this.button;
		}
	}

	/**
	 * Create a table model for the JTable with one button
	 * 
	 * @return OneButtonModel
	 */
	class OneButtonModel extends DefaultTableModel {
		private int colCount;

		public OneButtonModel(int _colCount) {
			colCount = _colCount;
		}

		public boolean isCellEditable(int row, int column) {
			int lastColIndex = colCount - 1;
			if (column == lastColIndex) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Create a table cell renderer for the JTable with one button
	 * <p>
	 * It is used to render components
	 * 
	 * @return OneButtonRenderer
	 */
	class OneButtonRenderer implements TableCellRenderer {

		private OneButtonPanel oneButtonPanel;
		private String[] buttonLabelArray;

		public OneButtonRenderer(String[] _buttonLabelArray) {
			buttonLabelArray = _buttonLabelArray;
			oneButtonPanel = new OneButtonPanel(buttonLabelArray);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// Issue the content of the button
			if (buttonLabelArray.length == 1) {
				oneButtonPanel.getButton().setText(buttonLabelArray[0]);
				oneButtonPanel.getButton().setEnabled(true);
			} else if (buttonLabelArray.length > 1 && value.toString().equals("0")
					&& buttonLabelArray[0] == "Request") {
				oneButtonPanel.getButton().setText(buttonLabelArray[0]);
				oneButtonPanel.getButton().setEnabled(true);
			} else if (buttonLabelArray.length > 1 && value.toString().equals("1")
					&& buttonLabelArray[1] == "Pending") {
				oneButtonPanel.getButton().setText(buttonLabelArray[1]);
				oneButtonPanel.getButton().setEnabled(false);
			} else if (buttonLabelArray.length > 1 && value.toString().equals("2")
					&& buttonLabelArray[2] == "Enrolled") {
				oneButtonPanel.getButton().setText(buttonLabelArray[2]);
				oneButtonPanel.getButton().setEnabled(false);
			}
			return this.oneButtonPanel;
		}
	}

	/**
	 * Create a table cell editor for the JTable with one button
	 * <p>
	 * It is used to issue the events of the components
	 * 
	 * @return OneButtonEditor
	 */
	class OneButtonEditor extends DefaultCellEditor {

		private OneButtonPanel oneButtonPanel;
		private String label;
		private String[] buttonLabelArray;
		private JTablePanelWithJButtonCallBack callBack;
		private int rowId;

		public OneButtonEditor(String[] _buttonLabelArray, JTablePanelWithJButtonCallBack _callBack) {
			super(new JCheckBox());
			buttonLabelArray = _buttonLabelArray;
			this.callBack = _callBack;
			oneButtonPanel = new OneButtonPanel(buttonLabelArray);
			oneButtonPanel.getButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					callBack.initCallBack(rowId, oneButtonPanel.getButton().getText());
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			rowId = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
			// Issue the content of the button
			if (buttonLabelArray.length == 1) {
				oneButtonPanel.getButton().setText(buttonLabelArray[0]);
				oneButtonPanel.getButton().setEnabled(true);
			} else if (buttonLabelArray.length > 1 && value.toString().equals("0")
					&& buttonLabelArray[0] == "Request") {
				oneButtonPanel.getButton().setText(buttonLabelArray[0]);
				oneButtonPanel.getButton().setEnabled(true);
			} else if (buttonLabelArray.length > 1 && value.toString().equals("1")
					&& buttonLabelArray[1] == "Pending") {
				oneButtonPanel.getButton().setText(buttonLabelArray[1]);
				oneButtonPanel.getButton().setEnabled(false);
			} else if (buttonLabelArray.length > 1 && value.toString().equals("2")
					&& buttonLabelArray[2] == "Enrolled") {
				oneButtonPanel.getButton().setText(buttonLabelArray[2]);
				oneButtonPanel.getButton().setEnabled(false);
			}
			label = (value == null) ? "" : value.toString();
			return this.oneButtonPanel;
		}

		public Object getCellEditorValue() {
			return new String(label);
		}

		public boolean stopCellEditing() {
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}

	}

	/**
	 * This method picks good column sizes.
	 */
	private void initColumnSizes(JTable table) {
		TableColumn currentColumn = null;
		Component currentComp = null;
		int currentHeaderWidth = 0;
		TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();

		for (int i = 0; i < table.getColumnCount(); i++) {
			currentColumn = table.getColumnModel().getColumn(i);
			currentComp = headerRenderer.getTableCellRendererComponent(null, currentColumn.getHeaderValue(), false,
					false, 0, 0);
			currentHeaderWidth = currentComp.getPreferredSize().width;
			currentColumn.setPreferredWidth(currentHeaderWidth);
		}
	}

	/**
	 * This method update the data and view of the JTable.
	 */
	public void updateDataVector(Object[][] _data, Object[] _columnNames) {
		tableModel.setDataVector(_data, _columnNames);
		table.getColumn("Action").setCellRenderer(new OneButtonRenderer(buttonLabelArray));
		table.getColumn("Action").setCellEditor(new OneButtonEditor(buttonLabelArray, callBack));
	}
}
