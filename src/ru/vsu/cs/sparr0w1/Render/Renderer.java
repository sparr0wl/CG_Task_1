package ru.vsu.cs.sparr0w1.Render;

public class Renderer {
    private final Scene scene;
    private final TimeOfDay timeOfDay;

    public Renderer(Scene scene) {
        this(scene, new TimeOfDay(1.0 / 720.0));
    }

    public Renderer(Scene scene, TimeOfDay timeOfDay) {
        this.scene = scene;
        this.timeOfDay = timeOfDay;
    }

    public void renderFrame() {
        timeOfDay.update();
        double time = timeOfDay.getTime();
        scene.setTimeOfDay(time);
        scene.getSkybox().renderFrame();
        float brightness = scene.getAmbientBrightness(time);
        scene.getBackground().renderFrame(brightness);
        scene.getForeground().renderFrame(brightness);
        scene.composeFrame();
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }
}
