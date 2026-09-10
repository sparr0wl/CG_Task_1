package ru.vsu.cs.sparr0w1.Render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LayerManager {
    private final int width;
    private final int height;
    private final List<Layer> layers = new ArrayList<>();
    private volatile BufferedImage currentFrame;

    public LayerManager(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public synchronized void addLayer(Layer layer) {
        layers.add(layer);
        layers.sort(Comparator.comparingInt(Layer::getZIndex));
    }

    public synchronized void renderFrame() {
        BufferedImage nextFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = nextFrame.createGraphics();
        try {
            for (Layer layer : layers) {
                layer.render(graphics);
            }
        } finally {
            graphics.dispose();
        }
        currentFrame = nextFrame;
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }
}
