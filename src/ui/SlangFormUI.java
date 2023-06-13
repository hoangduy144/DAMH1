package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;

public class SlangFormUI extends JPanel implements ActionListener {
	JButton addButton, editButton, clearButton, searchButton, deleteButton;
	JList resultList;
	DefaultListModel<String> listModel;
	Dict dict;
	JLabel slangLabel;
	JLabel definitionLabel;
	JTextField slangField;
	JTextField definitionField;
	JScrollPane resultPane;
	JLabel searchSlangLabel;
	JTextField searchSlangField;
	JScrollPane historyPanel;
	JTextArea historyArea;

	public SlangFormUI(Dict d) {
		dict = d;
		setLayout(new BorderLayout());

		JPanel pnLeft = new JPanel();
		pnLeft.setLayout(new BoxLayout(pnLeft, BoxLayout.Y_AXIS));
		pnLeft.setPreferredSize(new Dimension(300, 0));
		JPanel pnRight = new JPanel();
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
		pnRight.setLayout(new BoxLayout(pnRight, BoxLayout.Y_AXIS));
		add(sp, BorderLayout.CENTER);

		/////// TOP///////////
		JPanel pnTop = new JPanel();
		pnTop.setLayout(new FlowLayout());
		JLabel lblTop = new JLabel("Tu Dien Slang Word - 21424073");
		lblTop.setFont(new Font("Verdana", Font.PLAIN, 18));
		pnTop.add(lblTop);
		add(pnTop, BorderLayout.NORTH);

		//// Left//////////

		Border borderCRUD = BorderFactory.createLineBorder(Color.BLUE);
		TitledBorder borderTitleCRUD = new TitledBorder(borderCRUD, "CRUD APP");
		borderTitleCRUD.setTitleJustification(TitledBorder.CENTER);
		borderTitleCRUD.setTitleColor(Color.RED);
		pnLeft.setBorder(borderTitleCRUD);

		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout());
		slangLabel = new JLabel("SlangWord");
		slangField = new JTextField(15);
		pnMa.add(slangLabel);
		pnMa.add(slangField);
		pnLeft.add(pnMa);

		JPanel pnDef = new JPanel();
		pnDef.setLayout(new FlowLayout());
		definitionLabel = new JLabel("Definition");
		definitionField = new JTextField(15);
		pnDef.add(definitionLabel);
		pnDef.add(definitionField);
		pnLeft.add(pnDef);

		JPanel pnLeftBtn = new JPanel();
		pnLeftBtn.setLayout(new FlowLayout());
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		editButton = new JButton("Update");
		editButton.addActionListener(this);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		pnLeftBtn.add(addButton);
		pnLeftBtn.add(editButton);
		pnLeftBtn.add(clearButton);
		pnLeft.add(pnLeftBtn);

		/////// Right/////////////
		JPanel pnSlang = new JPanel();

		Border borderSlang = BorderFactory.createLineBorder(Color.BLUE);
		TitledBorder borderTitleSlang = new TitledBorder(borderSlang, "Search SlangWord");
		borderTitleSlang.setTitleJustification(TitledBorder.CENTER);
		borderTitleSlang.setTitleColor(Color.RED);
		pnRight.setBorder(borderTitleSlang);

		pnSlang.setLayout(new FlowLayout());
		searchSlangLabel = new JLabel("SlangWord");
		searchSlangField = new JTextField(15);
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		pnSlang.add(searchSlangLabel);
		pnSlang.add(searchSlangField);
		pnSlang.add(searchButton);
		pnRight.add(pnSlang);

		listModel = new DefaultListModel<>();
		resultList = new JList(listModel);
		resultList.addListSelectionListener((ListSelectionEvent e) -> {
			
            definitionField.setText((String) resultList.getSelectedValue());
            slangField.setText(searchSlangField.getText());
        });
		resultPane = new JScrollPane(resultList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		pnRight.add(resultPane);
		pnRight.add(deleteButton);

		//////// BOTTOM//////////
		JPanel pnHistory = new JPanel();

		Border borderHistory = BorderFactory.createLineBorder(Color.RED);
		TitledBorder borderTitleHistory = new TitledBorder(borderHistory, "Lich su tim kiem");
		borderTitleHistory.setTitleJustification(TitledBorder.CENTER);
		borderTitleHistory.setTitleColor(Color.BLUE);
		pnHistory.setBorder(borderTitleHistory);

		pnHistory.setLayout(new BoxLayout(pnHistory, BoxLayout.Y_AXIS));
		historyArea = new JTextArea();
		historyArea.setEditable(false);
		historyPanel = new JScrollPane(historyArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		historyPanel.setPreferredSize(new Dimension(0, 150));

		pnHistory.add(historyPanel);

		LinkedList<String> history = dict.getHistory();
		for (String string : history) {
			historyArea.append("\n" + string);
		}
		add(pnHistory, BorderLayout.SOUTH);

		definitionLabel.setPreferredSize(slangLabel.getPreferredSize());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			String slang = slangField.getText();
			String definition = definitionField.getText();
			if (dict.hasSlang(slang)) {
				String[] options = { "Tạo mới", "Ghi đè" };
				int choice = JOptionPane.showOptionDialog(null, "Slangword đã tồn tại", "Slangword đã tồn tại",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if (choice == 0) {
					dict.AddDefinition(slang, definition);
					if (slang.equals(searchSlangField.getText())) {
						listModel.addElement(definition);
					}
				} else {
					dict.AddNew(slang, definition);
					if (slang.equals(searchSlangField.getText())) {
						listModel.removeAllElements();
						listModel.addElement(definition);
					}
				}

			} else {
				dict.AddNew(slang, definition);
				JOptionPane.showMessageDialog(null, "Thêm thành công");
			}
		} else if (e.getSource() == editButton) {
			if (dict.EditSlang(searchSlangField.getText(), (String) resultList.getSelectedValue(),
					definitionField.getText())) {
				listModel.addElement(definitionField.getText());
				listModel.removeElement(resultList.getSelectedValue());
			} else {
				JOptionPane.showMessageDialog(addButton, "Không tìm thấy slangword");
			}
		} else if (e.getSource() == deleteButton) {
			int choice = JOptionPane.showConfirmDialog(deleteButton, "Slangword sẽ được xóa", "Xóa",
					JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				dict.deleteSlang(searchSlangField.getText());
				listModel.removeAllElements();
				slangField.setText("");
				searchSlangField.setText("");
			}
		} else if (e.getSource() == searchButton) {
			listModel.removeAllElements();
			HashSet<String> defSet = dict.searchSlang(searchSlangField.getText());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String time = formatter.format(date);
			String history = time + " |   " + searchSlangField.getText();
			if (defSet != null) {
				for (String s : defSet) {
					listModel.addElement(s.strip());
				}
			} else {
				JOptionPane.showMessageDialog(resultList, "Slangword is not in dictionary");
			}
			dict.AddHistory(history);
			historyArea.append("\n" + history);
		} else if (e.getSource() == clearButton) {
			slangField.setText("");
			definitionField.setText("");
		}
	}
	
	public void clearHistory() {
		historyArea.setText("");
	}

}
