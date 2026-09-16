package ru.vsu.cs.sparr0w1;

import ru.vsu.cs.sparr0w1.Models.Mountain;
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
                Layer backgroundLayer = new Layer(0, 300, 1);
                Mountain firstMountain = new Mountain(0, -150, 800, 300, 15, 0.65, 0.10, System.currentTimeMillis() / 1000);
                firstMountain.generateVertex();
                backgroundLayer.addElement(firstMountain);
                scene.getSkybox().addLayer(skyLayer);
                scene.getBackground().addLayer(backgroundLayer);

                Renderer renderer = new Renderer(scene, new TimeOfDay(1.0 / 720.0));
                renderer.renderFrame();
                new MainWindow(scene, renderer);
            }
        }
        );
    }
}
