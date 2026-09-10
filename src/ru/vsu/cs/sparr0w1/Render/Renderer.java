package ru.vsu.cs.sparr0w1.Render;

public class Renderer {
    private final Scene scene;

    public Renderer(Scene scene) {
        this.scene = scene;
    }

    public void renderFrame() {
        scene.getSkybox().renderFrame();
        scene.getBackground().renderFrame();
        scene.getForeground().renderFrame();
        scene.composeFrame();
    }
}
