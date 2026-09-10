package ru.vsu.cs.sparr0w1.Render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Scene {
    private final int width;
    private final int height;
    private final LayerManager skybox;
    private final LayerManager background;
    private final LayerManager foreground;
    private volatile BufferedImage currentFrame;

    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        skybox = new LayerManager(width, height);
        background = new LayerManager(width, height);
        foreground = new LayerManager(width, height);
    }

    public LayerManager getSkybox() {
        return skybox;
    }

    public LayerManager getBackground() {
        return background;
    }

    public LayerManager getForeground() {
        return foreground;
    }

    public synchronized void composeFrame() {
        BufferedImage nextFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = nextFrame.createGraphics();
        try {
            drawFrame(graphics, skybox.getCurrentFrame());
            drawFrame(graphics, background.getCurrentFrame());
            drawFrame(graphics, foreground.getCurrentFrame());
        } finally {
            graphics.dispose();
        }
        currentFrame = nextFrame;
    }

    public void render(Graphics2D graphics) {
        BufferedImage frame = currentFrame;
        if (frame != null) {
            graphics.drawImage(frame, 0, 0, null);
        }
    }

    private void drawFrame(Graphics2D graphics, BufferedImage frame) {
        if (frame != null) {
            graphics.drawImage(frame, 0, 0, null);
        }
    }
}
