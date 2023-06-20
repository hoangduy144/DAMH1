package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Dict;

public class RandomForm extends JPanel {
    JButton btnWord;
    JLabel label, slang, definition;
    Dict dict;

    public RandomForm(Dict d) {
        dict = d;
        addControls();
        addEvents();
    }

    private void addControls() {

        setLayout(new BorderLayout());

        // Panel Top
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout());
        JLabel lblTop = new JLabel("Tu Dien Slang Word - 21424073");
        lblTop.setFont(new Font("Verdana", Font.PLAIN, 18));
        pnTop.add(lblTop);
        add(pnTop, BorderLayout.NORTH);

        // Panel Center
        JPanel pnCenter = new JPanel();
        pnCenter.setLayout(new GridBagLayout());

        // Panel Center Wrapper
        JPanel pnCenterWrapper = new JPanel();
        pnCenterWrapper.setLayout(new BorderLayout());
        pnCenterWrapper.setPreferredSize(new Dimension(200, 300));

        Border borderRandom = BorderFactory.createLineBorder(Color.BLUE);
        TitledBorder borderTitleRandom = new TitledBorder(borderRandom, "Random SlangWord");
        borderTitleRandom.setTitleJustification(TitledBorder.CENTER);
        borderTitleRandom.setTitleColor(Color.RED);
        pnCenter.setBorder(borderTitleRandom);

        btnWord = new JButton("Click!");
        slang = new JLabel();
        slang.setForeground(Color.BLUE);
        slang.setFont(new Font("Verdana", Font.PLAIN, 18));
        definition = new JLabel();
        definition.setForeground(Color.RED);
        definition.setFont(new Font("Verdana", Font.PLAIN, 18));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 0, 0);
        pnCenter.add(btnWord, c);
        c.gridy = 1;
        pnCenter.add(slang, c);
        c.gridy++;
        pnCenter.add(definition, c);

        pnCenterWrapper.add(pnCenter, BorderLayout.NORTH);

        add(pnCenterWrapper, BorderLayout.CENTER);
    }

    private void addEvents() {
        btnWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slang.setText(dict.randomSlang());
                String defs = "";
                for (Iterator<String> iterator = dict.searchSlang(slang.getText()).iterator(); iterator.hasNext();) {
                    String str = iterator.next();
                    defs += str + ", ";
                }
                definition.setText(defs.substring(0, defs.length() - 2));
            }
        });
    }
}
