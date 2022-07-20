package com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class MyFrame extends JFrame {

    public MyFrame() {
        GameField gameField = new GameField();

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.995;
        this.add(gameField, c);


        JLabel score = new JLabel("Current score: ");
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setBorder(BorderFactory.createTitledBorder("Score"));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.weighty = 0.005;
        this.add(score, c);

        Thread makeRect = new Thread(() -> {
            while (true) {
                gameField.play();

                score.setText("Current score: " + ((double) gameField.clicked.size() / ((double) (gameField.rects.size() + gameField.clicked.size()))) * 100 + "%");
                try {
                    Thread.sleep((int) (Math.random() * 1500) + 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                for (int i = 0; i < gameField.rects.size(); i++) {
                    if ((e.getX() >= gameField.rects.get(i).x && e.getX() <= gameField.rects.get(i).x + 25) &&
                            (e.getY() >= gameField.rects.get(i).y && e.getY() <= gameField.rects.get(i).y + 25)) {
                        gameField.clicked.add(gameField.rects.remove(gameField.rects.indexOf(gameField.rects.get(i))));
                        score.setText("Current score: " + ((double) gameField.clicked.size() / ((double) (gameField.rects.size() + gameField.clicked.size()))) * 100 + "%");
                    }
                }
            }
        });

        makeRect.start();

        Timer timer = new Timer( 60 * 1000, evt -> {
            removeAll();
            int close;
            if ((((double) gameField.clicked.size() / ((double) (gameField.rects.size() + gameField.clicked.size()))) * 100) >= 50) {
                close = JOptionPane.showConfirmDialog(getParent(), "You Win!", "Result", JOptionPane.DEFAULT_OPTION);
            } else {
                close = JOptionPane.showConfirmDialog(getParent(), "You Lose", "Result", JOptionPane.DEFAULT_OPTION);
            }

            if (close == JOptionPane.OK_OPTION) {
                System.exit(0);
            }

        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(340, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        timer.start();
    }
}



