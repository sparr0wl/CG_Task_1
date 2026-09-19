package ru.vsu.cs.sparr0w1.Render;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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
        renderFrame(1.0f);
    }

    public synchronized void renderFrame(float brightness) {
        BufferedImage nextFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = nextFrame.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            for (Layer layer : layers) {
                layer.render(graphics);
            }
        } finally {
            graphics.dispose();
        }
        if (brightness < 1.0f) {
            new RescaleOp(new float[]{brightness, brightness, brightness, 1.0f},
                    new float[]{0, 0, 0, 0}, null).filter(nextFrame, nextFrame);
        }
        currentFrame = nextFrame;
    }

    public synchronized void updateTimeOfDay(double timeOfDay) {
        for (Layer layer : layers) {
            layer.updateTimeOfDay(timeOfDay, width, height);
        }
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }
}
