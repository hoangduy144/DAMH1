package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Dict;

public class RandomForm extends JPanel {
	
	public RandomForm(Dict d) {
		dict = d;
		setLayout(new GridBagLayout());
		getWord = new JButton("Click!");
		label = new JLabel("On this day slang word", JLabel.CENTER);
		slang = new JLabel();
		definition = new JLabel();
		
		GridBagConstraints c = new GridBagConstraints ();
		c.insets = new Insets (10, 0, 0, 0);
		add(getWord, c);
		c.gridy = 1;
        add(label, c);
        c.gridy = 2;
        add(slang, c);
        c.gridy++;
        add(definition, c);
        
		getWord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				slang.setText(dict.randomSlang());
				String defs = "";
				for (Iterator iterator = dict.searchSlang(slang.getText()).iterator(); iterator.hasNext();) {
					String str = (String) iterator.next();
					defs += str + ", ";
				}
				definition.setText(defs.substring(0, defs.length()-2));
			}
		});
	}
    JButton getWord;
	JLabel label;
	JLabel slang;
	Dict dict;
	JLabel definition;
}