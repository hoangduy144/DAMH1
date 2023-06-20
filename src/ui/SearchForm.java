package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import model.Dict;

public class SearchForm extends JPanel{
	JButton searchButton;
	JList<String> resultList;
	DefaultListModel<String> listModel;
	Dict dict;
	JLabel slangLabel, definitionLabel, searchDefinitionLabel;
	JTextField slangField, definitionField, searchDefinitionField;
	JScrollPane resultPane, historyPanel;
	JTextArea historyArea;

	public SearchForm(Dict d) {
		dict = d;
		addControls();
		addEvents();
	}

	private void addControls() {
		
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
		slangLabel = new JLabel("Definition");
		slangField = new JTextField(15);
		pnMa.add(slangLabel);
		pnMa.add(slangField);
		pnLeft.add(pnMa);

		JPanel pnDef = new JPanel();
		pnDef.setLayout(new FlowLayout());
		definitionLabel = new JLabel("SlangWord");
		definitionField = new JTextField(15);
		pnDef.add(definitionLabel);
		pnDef.add(definitionField);
		pnLeft.add(pnDef);


		/////// Right/////////////
		JPanel pnSlang = new JPanel();

		Border borderSlang = BorderFactory.createLineBorder(Color.BLUE);
		TitledBorder borderTitleSlang = new TitledBorder(borderSlang, "Search Definition");
		borderTitleSlang.setTitleJustification(TitledBorder.CENTER);
		borderTitleSlang.setTitleColor(Color.RED);
		pnRight.setBorder(borderTitleSlang);

		pnSlang.setLayout(new FlowLayout());
		searchDefinitionLabel = new JLabel("Definition");
		searchDefinitionField = new JTextField(15);
		searchButton = new JButton("Search");
		pnSlang.add(searchDefinitionLabel);
		pnSlang.add(searchDefinitionField);
		pnSlang.add(searchButton);
		pnRight.add(pnSlang);

		listModel = new DefaultListModel<String>();
		
		resultList = new JList<String>(listModel);
		resultList.addListSelectionListener((ListSelectionEvent e) -> {

			definitionField.setText((String) resultList.getSelectedValue());
			slangField.setText(searchDefinitionField.getText());
		});
		resultPane = new JScrollPane(resultList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnRight.add(resultPane);

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

		slangLabel.setPreferredSize(definitionLabel.getPreferredSize());
	}

	private void addEvents() {
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listModel.removeAllElements();
				ArrayList<String> slangList = dict.searchDefinition(searchDefinitionField.getText());
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String time = formatter.format(date);
				String history = time + " |   " + searchDefinitionField.getText();
				if (slangList.size() != 0) {				
					for(String s : slangList) {
						listModel.addElement(s);
					}
				}
				else {
					JOptionPane.showMessageDialog(resultList, "Slang is not in dictionary");
				}
				dict.addHistory(history);
				historyArea.append("\n" + history);
			}
		});
	}

	public void clearHistory() {
		historyArea.setText("");
	}
}
