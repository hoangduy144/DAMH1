package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Dict;

public class SlangApp{
	JFrame frame;
	JTabbedPane tabbedPane;
	SlangForm slangPanel;
	SearchForm definitionPanel;
	JPanel findDefiPanel;
	JMenuItem reset;
	Dict dict;
	
	public SlangApp() {
		dict = new Dict();
	    frame = new JFrame("Slang app");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    addControls();
	    addEvents();
	    
	    frame.pack();
	    frame.setVisible(true);
	}
	
	private void addControls() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Clear History");
		
		reset = new JMenuItem("Reset Dictionary");
		fileMenu.add(reset);
		menuBar.add(fileMenu);
		
		slangPanel = new SlangForm(dict);
		definitionPanel = new SearchForm(dict);
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Slang Form", slangPanel);
		tabbedPane.addTab("Search definition", new SearchForm(dict));
		tabbedPane.addTab("On this day", new RandomForm(dict));
        tabbedPane.addTab("Slang Game", new SlangGame(dict));
        
		frame.setJMenuBar(menuBar);
		frame.add(tabbedPane, BorderLayout.CENTER);
	}
	
	private void addEvents() {
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Here");
				dict.reset();
				slangPanel.clearHistory();
				definitionPanel.clearHistory();
			}
		});
	}	
}
