package com.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameField extends JPanel {

    public ArrayList<Rectangle> rects = new ArrayList<>();
    public ArrayList<Rectangle> clicked = new ArrayList<>();
    public Thread main;

    public GameField() {
        this.setSize(new Dimension(340, 380));
    }

    public void play() {
        rects.add(new Rectangle((int)(Math.random()*(getWidth()-25)), ((int) (Math.random() * (getHeight() / 2))), 25, 25));
        repaint();
        for (Rectangle curr : rects) {
            main = new Thread(() -> {
                int x = curr.x;
                int y = curr.y;
                while (true) {
                    curr.setLocation(x, y++);
                    repaint();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            });
            main.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.orange);

        for (Rectangle curr : rects) {
            g.setColor(Color.BLUE);
            g.fillRect(curr.x, curr.y, curr.width, curr.height);

        }
    }
}
