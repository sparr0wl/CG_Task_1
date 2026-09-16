package ru.vsu.cs.sparr0w1.UI;


import javax.swing.*;
import ru.vsu.cs.sparr0w1.Render.Scene;
import ru.vsu.cs.sparr0w1.Render.Renderer;

public class MainWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final DrawPanel drawPanel;

    public MainWindow(Scene scene, Renderer renderer) {
        drawPanel = new DrawPanel(WINDOW_WIDTH, WINDOW_HEIGHT, scene, renderer);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(drawPanel);
        this.pack();
        this.setVisible(true);
    }
}
