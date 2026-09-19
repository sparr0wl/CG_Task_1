package ru.vsu.cs.sparr0w1;

import ru.vsu.cs.sparr0w1.Models.Mountain;
import ru.vsu.cs.sparr0w1.Models.Sky;
import ru.vsu.cs.sparr0w1.Models.Sun;
import ru.vsu.cs.sparr0w1.Models.Moon;
import ru.vsu.cs.sparr0w1.Models.Stars;
import ru.vsu.cs.sparr0w1.Models.Terrain;
import ru.vsu.cs.sparr0w1.Render.Layer;
import ru.vsu.cs.sparr0w1.UI.MainWindow;
import ru.vsu.cs.sparr0w1.Render.Renderer;
import ru.vsu.cs.sparr0w1.Render.Scene;
import ru.vsu.cs.sparr0w1.Render.TimeOfDay;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Main {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new Scene(800, 600);
                Layer skyLayer = new Layer(0, 0, 0);
                skyLayer.addElement(new Sky(0, 0, 800, 600));
                skyLayer.addElement(new Stars(0, 0, 800, 600, 150, System.currentTimeMillis() / 1000));
                skyLayer.addElement(new Sun(0, 0, 60, 60));
                skyLayer.addElement(new Moon(0, 0, 48, 48));
                Layer distantLayer = new Layer(0, 330, 1);
                Mountain distantMountain = new Mountain(-30, -95, 860, 270, 22, 0.48, 0.06, System.currentTimeMillis() / 1000,
                        new Color(128, 146, 181), new Color(47, 65, 100), true);
                distantMountain.generateVertex();
                distantLayer.addElement(distantMountain);

                Layer backgroundLayer = new Layer(0, 300, 0);
                Mountain mainMountain = new Mountain(0, -150, 800, 320, 18, 0.68, 0.09, System.currentTimeMillis() / 1000,
                        new Color(83, 108, 156), new Color(37, 57, 93), false);
                mainMountain.generateVertex();
                backgroundLayer.addElement(mainMountain);

                Layer foregroundLayer = new Layer(0, 440, 2);
                foregroundLayer.addElement(new Terrain(0, 0, 800, 160, System.currentTimeMillis() / 1000));
                scene.getSkybox().addLayer(skyLayer);
                scene.getBackground().addLayer(distantLayer);
                scene.getBackground().addLayer(backgroundLayer);
                scene.getForeground().addLayer(foregroundLayer);

                Renderer renderer = new Renderer(scene, new TimeOfDay(1.0/720.0));
                renderer.renderFrame();
                new MainWindow(scene, renderer);
            }
        }
        );
    }
}
