package ru.vsu.cs.sparr0w1;

import ru.vsu.cs.sparr0w1.Models.Sky;
import ru.vsu.cs.sparr0w1.Models.Sun;
import ru.vsu.cs.sparr0w1.Models.Moon;
import ru.vsu.cs.sparr0w1.Render.Layer;
import ru.vsu.cs.sparr0w1.UI.MainWindow;
import ru.vsu.cs.sparr0w1.Render.Renderer;
import ru.vsu.cs.sparr0w1.Render.Scene;
import ru.vsu.cs.sparr0w1.Render.TimeOfDay;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new Scene(800, 600);
                Layer skyLayer = new Layer(0, 0, 0);
                skyLayer.addElement(new Sky(0, 0, 800, 600));
                skyLayer.addElement(new Sun(0, 0, 60, 60));
                skyLayer.addElement(new Moon(0, 0, 48, 48));
                scene.getSkybox().addLayer(skyLayer);

                Renderer renderer = new Renderer(scene, new TimeOfDay(1.0 / 720.0));
                renderer.renderFrame();
                new MainWindow(scene, renderer);
            }
        }
        );
    }
}
