package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Dict;

public class SlangGame extends JPanel implements ActionListener {
    JLabel word, messageLabel, solution;
    Dict dict;
    DefaultListModel<String> listModel;
    JButton slangGame;
    JPanel createPanel;
    JRadioButton A, B, C, D;
    ButtonGroup answerGroup;
    String key = "";
    boolean isSlang = false;

    public SlangGame(Dict d) {
        dict = d;
        addControls();
    }
    private void addControls() {
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;

        slangGame = new JButton("Create new slang game");
        slangGame.addActionListener(this);
        createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.X_AXIS));
        createPanel.add(slangGame);

        word = new JLabel();
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerGroup = new ButtonGroup();
        A = new JRadioButton();
        B = new JRadioButton();
        C = new JRadioButton();
        D = new JRadioButton();
        A.addActionListener(this);
        B.addActionListener(this);
        C.addActionListener(this);
        D.addActionListener(this);
        answerGroup.add(A);
        answerGroup.add(B);
        answerGroup.add(C);
        answerGroup.add(D);

        answerPanel.add(A);
        answerPanel.add(B);
        answerPanel.add(C);
        answerPanel.add(D);

        word = new JLabel("", JLabel.CENTER);
        word.setFont(new Font("Verdana", Font.BOLD, 20));
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Verdana", Font.ITALIC, 20));
        solution = new JLabel("", JLabel.CENTER);
        solution.setFont(new Font("Verdana", Font.BOLD, 25));

        add(createPanel, c);
        c.gridy = 1;
        add(word, c);
        c.gridy = 2;
        add(answerPanel, c);
        c.gridy = 3;
        add(messageLabel, c);
        c.gridy = 4;
        add(solution, c);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        A.setEnabled(true);
        B.setEnabled(true);
        C.setEnabled(true);
        D.setEnabled(true);
        solution.setText("");

        if (e.getSource() == slangGame) {
            startSlangGame();
        }

        if (A.isSelected()) {
            checkAnswer(A);
        }
        if (B.isSelected()) {
            checkAnswer(B);
        }
        if (C.isSelected()) {
            checkAnswer(C);
        }
        if (D.isSelected()) {
            checkAnswer(D);
        }
    }

    private void startSlangGame() {
        answerGroup.clearSelection();
        isSlang = true;
        messageLabel.setText("");
        Random r = new Random();
        HashMap<String, LinkedList<String>> myGame = dict.slangGame();
        ArrayList<String> slangs = new ArrayList<String>(myGame.keySet());
        key = slangs.get(r.nextInt(slangs.size()));
        word.setText("Slang: " + key);
        int count = 1;
        for (Entry<String, LinkedList<String>> entry : myGame.entrySet()) {
            LinkedList<String> value = entry.getValue();
            setAnswerOption(count, value.iterator().next());
            count++;
        }
    }

    private void setAnswerOption(int count, String value) {
        switch (count) {
            case 1:
                A.setText(value);
                break;
            case 2:
                B.setText(value);
                break;
            case 3:
                C.setText(value);
                break;
            case 4:
                D.setText(value);
                break;
        }
    }

    private void checkAnswer(JRadioButton selectedButton) {
        A.setEnabled(false);
        B.setEnabled(false);
        C.setEnabled(false);
        D.setEnabled(false);
        messageLabel.setText("");
        boolean isCorrect = false;
        String sol = "";

        if (isSlang) {
            sol = dict.searchSlang(key).iterator().next();
            if (selectedButton.getText() == sol) {
                isCorrect = true;
            }
        }

        if (isCorrect) {
            messageLabel.setText("Correct");
            messageLabel.setForeground(new Color(0, 102, 0));
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Incorrect");
            solution.setText("Answer: " + sol);
            solution.setForeground(new Color(0, 102, 0));
        }
    }
}
