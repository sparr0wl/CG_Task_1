package ru.vsu.cs.sparr0w1.Render;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import ru.vsu.cs.sparr0w1.Models.Model;

public class Layer {
    private final int positionX;
    private final int positionY;
    private final int zIndex;
    private final List<Model> elements = new ArrayList<>();

    public Layer(int positionX, int positionY, int zIndex) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.zIndex = zIndex;
    }

    public int getZIndex() {
        return zIndex;
    }

    public synchronized void addElement(Model model) {
        elements.add(model);
    }

    public synchronized void render(Graphics2D graphics) {
        Graphics2D layerGraphics = (Graphics2D) graphics.create();
        try {
            layerGraphics.translate(positionX, positionY);
            for (Model element : elements) {
                element.render(layerGraphics);
            }
        } finally {
            layerGraphics.dispose();
        }
    }
}
