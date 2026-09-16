package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import ru.vsu.cs.sparr0w1.Render.TimeAware;

public class Sky extends Model implements TimeAware {
    private static final Color NIGHT_TOP = new Color(13, 27, 61);
    private static final Color NIGHT_BOTTOM = new Color(30, 46, 80);
    private static final Color TWILIGHT_TOP = new Color(69, 39, 160);
    private static final Color TWILIGHT_BOTTOM = new Color(255, 112, 67);
    private static final Color DAY_TOP = new Color(25, 118, 210);
    private static final Color DAY_BOTTOM = new Color(129, 212, 250);
    private double timeOfDay = 0;
    public Sky(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public double getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @Override
    public void setTimeOfDay(double timeOfDay, int sceneWidth, int sceneHeight) {
        setTimeOfDay(timeOfDay);
    }

    @Override
    public void render(Graphics2D g) {
        Color top = skyColor(NIGHT_TOP, TWILIGHT_TOP, DAY_TOP);
        Color bottom = skyColor(NIGHT_BOTTOM, TWILIGHT_BOTTOM, DAY_BOTTOM);
        GradientPaint skyBackground = new GradientPaint(0, 0, top, 0, this.height, bottom);
        g.setPaint(skyBackground);
        g.fillRect(this.positionX, this.positionY, this.width, this.height);
    }

    private Color skyColor(Color night, Color twilight, Color day) {
        double daylight = Math.max(0.0, Math.sin(2.0 * Math.PI * (timeOfDay - 0.25)));
        double twilightAmount = Math.max(0.0, 1.0 - Math.abs(Math.sin(2.0 * Math.PI * (timeOfDay - 0.25))) * 3.0);
        return blend(blend(night, twilight, twilightAmount), day, daylight);
    }

    private Color blend(Color from, Color to, double amount) {
        amount = Math.max(0.0, Math.min(1.0, amount));
        return new Color(
                (int) Math.round(from.getRed() + (to.getRed() - from.getRed()) * amount),
                (int) Math.round(from.getGreen() + (to.getGreen() - from.getGreen()) * amount),
                (int) Math.round(from.getBlue() + (to.getBlue() - from.getBlue()) * amount));
    }
}
