/**
 * File: JTablePanelWithTwoJButtons.java
 * @author Mengyun Xie
 * Date: Dec 06, 2022
 * Description: Define a JTablePanelWithTwoJButtons class, A JPanel contains a JTable, and two buttons serves as a custom JTable.
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

public class JTablePanelWithTwoJButtons extends JPanel {
	private Object[][] data;
	private Object[] columnNames;
	private JTablePanelWithJButtonCallBack callBack;
	private MultiButtonModel tableModel;
	private JTable table;
	private String[] buttonLabelArray;

	/**
	 * This is the constructor of JTablePanelWithTwoJButtons.
	 * 
	 * @param _data             it is the data of the JTable
	 * @param _columnNames      it is the column name of the JTable
	 * @param _buttonLabelArray it is the various action of the button in the JTable
	 * @param _callBack         it is the callback after clicking the button
	 */
	public JTablePanelWithTwoJButtons(Object[][] _data, Object[] _columnNames, String[] _buttonLabelArray,
			JTablePanelWithJButtonCallBack _callBack) {
		super(new GridLayout(1, 0));

		// Initiate data
		data = _data;
		columnNames = _columnNames;
		callBack = _callBack;
		buttonLabelArray = _buttonLabelArray;

		// Create a custom table model for the JTable
		tableModel = new MultiButtonModel(columnNames.length);
		tableModel.setDataVector(data, columnNames);

		// Build the JTable
		table = new JTable(tableModel);
		table.getColumn("Action").setCellRenderer(new MultiButtonRenderer(buttonLabelArray));
		table.getColumn("Action").setCellEditor(new MultiButtonEditor(buttonLabelArray, callBack));
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
	 * @return MultiButtonsPanel
	 */
	class MultiButtonsPanel extends JPanel {
		private JButton leftButton;
		private JButton rightButton;

		public MultiButtonsPanel(String[] _buttonLabelArray) {
			setLayout(null);
			setBackground(Color.WHITE);
			this.leftButton = new JButton(_buttonLabelArray[0]);
			this.leftButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_XS));
			this.leftButton.setBounds(0, 0, GUIConstants.WIDTH_60, GUIConstants.HEIGHT_14);
			this.leftButton.setHorizontalAlignment(SwingConstants.LEFT);
			this.leftButton.setOpaque(true);
			this.leftButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
			this.leftButton.setFocusPainted(false);
			this.leftButton.setBackground(Color.WHITE);
			this.leftButton.setForeground(Color.BLUE);

			this.rightButton = new JButton(_buttonLabelArray[0]);
			this.rightButton.setFont(new Font("Arial", Font.PLAIN, GUIConstants.FRONTSIZE_XS));
			this.rightButton.setBounds(61, 0, GUIConstants.WIDTH_60, GUIConstants.HEIGHT_14);
			this.rightButton.setHorizontalAlignment(SwingConstants.LEFT);
			this.rightButton.setOpaque(true);
			this.rightButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
			this.rightButton.setFocusPainted(false);
			this.rightButton.setBackground(Color.WHITE);
			this.rightButton.setForeground(Color.BLUE);

			add(leftButton);
			add(rightButton);
		}

		public JButton getLeftButton() {
			return this.leftButton;
		}

		public JButton getRightButton() {
			return this.rightButton;
		}

	}

	/**
	 * Create a table model for the JTable with two buttons
	 * 
	 * @return MultiButtonModel
	 */
	class MultiButtonModel extends DefaultTableModel {
		private int colCount;

		public MultiButtonModel(int _colCount) {
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
	 * Create a table cell renderer for the JTable with two buttons
	 * <p>
	 * It is used to render components
	 * 
	 * @return MultiButtonRenderer
	 */
	class MultiButtonRenderer implements TableCellRenderer {

		private MultiButtonsPanel multiButtonsPanel;
		private String[] buttonLabelArray;

		public MultiButtonRenderer(String[] _buttonLabelArray) {
			buttonLabelArray = _buttonLabelArray;
			multiButtonsPanel = new MultiButtonsPanel(buttonLabelArray);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// Issue the content of the button
			if (value.toString().equals("1") && buttonLabelArray[1] == "Pending") {
				multiButtonsPanel.getLeftButton().setText(buttonLabelArray[1]);
				multiButtonsPanel.getLeftButton().setEnabled(false);
				multiButtonsPanel.getRightButton().setEnabled(true);
			} else if (value.toString().equals("2") && buttonLabelArray[2] == "Drop") {
				multiButtonsPanel.getLeftButton().setText(buttonLabelArray[2]);
				multiButtonsPanel.getLeftButton().setEnabled(true);
				multiButtonsPanel.getRightButton().setEnabled(true);
			} else if (value.toString().equals("1") && buttonLabelArray[1] == "Approve/Decline") {
				multiButtonsPanel.getLeftButton().setText("Approve");
				multiButtonsPanel.getRightButton().setText("Decline");
				multiButtonsPanel.getLeftButton().setEnabled(true);
				multiButtonsPanel.getRightButton().setEnabled(true);
			} else if (value.toString().equals("2") && buttonLabelArray[2] == "Enrolled") {
				multiButtonsPanel.getLeftButton().setText(buttonLabelArray[2]);
				multiButtonsPanel.getRightButton().setText("");
				multiButtonsPanel.getLeftButton().setEnabled(false);
				multiButtonsPanel.getRightButton().setEnabled(false);
			}
			return this.multiButtonsPanel;
		}

	}

	/**
	 * Create a table cell editor for the JTable with two buttons
	 * <p>
	 * It is used to issue the events of the components
	 * 
	 * @return MultiButtonEditor
	 */
	class MultiButtonEditor extends DefaultCellEditor {

		private MultiButtonsPanel multiButtonsPanel;
		private String label;
		private JTablePanelWithJButtonCallBack callBack;
		private int rowId;
		private String[] buttonLabelArray;

		public MultiButtonEditor(String[] _buttonLabelArray, JTablePanelWithJButtonCallBack _callBack) {
			super(new JCheckBox());

			buttonLabelArray = _buttonLabelArray;
			this.callBack = _callBack;
			rowId = -1;
			multiButtonsPanel = new MultiButtonsPanel(buttonLabelArray);
			multiButtonsPanel.getLeftButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					callBack.initCallBack(rowId, multiButtonsPanel.getLeftButton().getText());
				}
			});

			multiButtonsPanel.getRightButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					callBack.initCallBack(rowId, multiButtonsPanel.getRightButton().getText());
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			rowId = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
			// Issue the content of the button
			if (value.toString().equals("1") && buttonLabelArray[1] == "Pending") {
				multiButtonsPanel.getLeftButton().setText(buttonLabelArray[1]);
				multiButtonsPanel.getLeftButton().setEnabled(false);
				multiButtonsPanel.getRightButton().setEnabled(true);
			} else if (value.toString().equals("2") && buttonLabelArray[2] == "Drop") {
				multiButtonsPanel.getLeftButton().setText(buttonLabelArray[2]);
				multiButtonsPanel.getLeftButton().setEnabled(true);
				multiButtonsPanel.getRightButton().setEnabled(true);
			} else if (value.toString().equals("1") && buttonLabelArray[1] == "Approve/Decline") {
				multiButtonsPanel.getLeftButton().setText("Approve");
				multiButtonsPanel.getRightButton().setText("Decline");
				multiButtonsPanel.getLeftButton().setEnabled(true);
				multiButtonsPanel.getRightButton().setEnabled(true);
			} else if (value.toString().equals("2") && buttonLabelArray[2] == "Enrolled") {
				multiButtonsPanel.getLeftButton().setText(buttonLabelArray[2]);
				multiButtonsPanel.getRightButton().setText("");
				multiButtonsPanel.getLeftButton().setEnabled(false);
				multiButtonsPanel.getRightButton().setEnabled(false);
			}
			label = (value == null) ? "" : value.toString();
			return this.multiButtonsPanel;
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
			if (i == table.getColumnCount() - 1) {
				currentColumn.setPreferredWidth(120);
			} else {
				currentColumn.setPreferredWidth(currentHeaderWidth);
			}
		}
	}

	/**
	 * This method update the data and view of the JTable.
	 */
	public void updateDataVector(Object[][] _data, Object[] _columnNames) {
		tableModel.setDataVector(_data, _columnNames);
		table.getColumn("Action").setCellRenderer(new MultiButtonRenderer(buttonLabelArray));
		table.getColumn("Action").setCellEditor(new MultiButtonEditor(buttonLabelArray, callBack));
	}
}
