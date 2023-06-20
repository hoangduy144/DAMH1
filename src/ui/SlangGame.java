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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Dict;

public class SlangGame extends JPanel implements ActionListener {
    JLabel word, messageLabel, solution;
    Dict dict;
    DefaultListModel<String> listModel;
    JButton slangGame, submitButton;
    JPanel createPanel;
    JRadioButton A, B, C, D;
    ButtonGroup answerGroup;
    String key = "";
    boolean isSlang = false;

    public SlangGame(Dict d) {
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

        Border borderSlang = BorderFactory.createLineBorder(Color.BLUE);
        TitledBorder borderTitleSlang = new TitledBorder(borderSlang, "SlangWord Game");
        borderTitleSlang.setTitleJustification(TitledBorder.CENTER);
        borderTitleSlang.setTitleColor(Color.RED);
        pnCenter.setBorder(borderTitleSlang);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;

        slangGame = new JButton("Create new slang game");
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

        submitButton = new JButton("Submit");

        pnCenter.add(createPanel, c);
        c.gridy = 1;
        pnCenter.add(word, c);
        c.gridy = 2;
        pnCenter.add(answerPanel, c);
        c.gridy = 3;
        pnCenter.add(messageLabel, c);
        c.gridy = 4;
        pnCenter.add(submitButton, c);
        c.gridy = 5;
        pnCenter.add(solution, c);

        pnCenterWrapper.add(pnCenter, BorderLayout.NORTH);
        add(pnCenterWrapper, BorderLayout.CENTER);
    }

    private void addEvents() {
        slangGame.addActionListener(this);
        submitButton.addActionListener(this);
        A.addActionListener(this);
        B.addActionListener(this);
        C.addActionListener(this);
        D.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == slangGame) {
            startSlangGame();
        } else if (e.getSource() == submitButton) {
            checkAnswer();
        } else if (e.getSource() == A || e.getSource() == B || e.getSource() == C || e.getSource() == D) {
            submitButton.setEnabled(true);
        }
    }

    private void startSlangGame() {
        submitButton.setEnabled(false);
        A.setEnabled(true);
        B.setEnabled(true);
        C.setEnabled(true);
        D.setEnabled(true);
        answerGroup.clearSelection();
        isSlang = true;
        messageLabel.setText("");
        solution.setText("");
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

    private void checkAnswer() {
        submitButton.setEnabled(false);
        A.setEnabled(false);
        B.setEnabled(false);
        C.setEnabled(false);
        D.setEnabled(false);
        boolean isCorrect = false;
        String sol = "";

        if (isSlang) {
            sol = dict.searchSlang(key).iterator().next();
            if (A.isSelected() && A.getText().equals(sol)) {
                isCorrect = true;
            } else if (B.isSelected() && B.getText().equals(sol)) {
                isCorrect = true;
            } else if (C.isSelected() && C.getText().equals(sol)) {
                isCorrect = true;
            } else if (D.isSelected() && D.getText().equals(sol)) {
                isCorrect = true;
            }
        }

        if (isCorrect) {
            messageLabel.setText("Correct");
            messageLabel.setForeground(Color.RED);
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Incorrect");
            solution.setText("Answer: " + sol);
            solution.setForeground(Color.MAGENTA);
        }
    }
}
