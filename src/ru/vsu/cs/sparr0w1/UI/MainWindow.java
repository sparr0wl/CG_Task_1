package ru.vsu.cs.sparr0w1.UI;


import javax.swing.*;
import ru.vsu.cs.sparr0w1.Render.Scene;
import ru.vsu.cs.sparr0w1.Render.Renderer;
import ru.vsu.cs.sparr0w1.Render.TimeOfDay;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final DrawPanel drawPanel;

    public MainWindow(Scene scene, Renderer renderer) {
        drawPanel = new DrawPanel(WINDOW_WIDTH, WINDOW_HEIGHT, scene, renderer);
        TimeOfDay time = renderer.getTimeOfDay();
        KeyStroke timeUp = KeyStroke.getKeyStroke((char) KeyEvent.VK_1);
        KeyStroke timeDown = KeyStroke.getKeyStroke((char) KeyEvent.VK_2);

        Action timeUpAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time.updateSpeed(time.getSpeed() / 2);
                System.out.println(time.getSpeed());
            }
        };

        Action timeDownAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time.updateSpeed(time.getSpeed() * 2);
                System.out.println(time.getSpeed());
            }
        };

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        drawPanel.getInputMap(condition).put(timeUp, "timeUpKey");
        drawPanel.getActionMap().put("timeUpKey", timeUpAction);
        drawPanel.getInputMap(condition).put(timeDown, "timeDownKey");
        drawPanel.getActionMap().put("timeDownKey", timeDownAction);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(drawPanel);
        this.pack();
        this.setVisible(true);
    }
}
