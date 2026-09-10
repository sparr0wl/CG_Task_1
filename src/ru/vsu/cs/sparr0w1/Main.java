package ru.vsu.cs.sparr0w1;

import ru.vsu.cs.sparr0w1.UI.MainWindow;
import ru.vsu.cs.sparr0w1.Render.Renderer;
import ru.vsu.cs.sparr0w1.Render.Scene;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new Scene(800, 600);
                Renderer renderer = new Renderer(scene);
                renderer.renderFrame();
                new MainWindow(scene);
            }
        }
        );
    }
}
