package ru.vsu.cs.sparr0w1.UI;


import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;

    public MainWindow() {
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(false);
        this.setVisible(true);
    }

}
