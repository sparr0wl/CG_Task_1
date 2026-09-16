package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import ru.vsu.cs.sparr0w1.Render.TimeAware;

public class Moon extends Model implements TimeAware {
    private boolean visible;
    public Moon(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    @Override
    public void render(Graphics2D g) {
        if (!visible) {
            return;
        }

        Graphics2D moonGraphics = (Graphics2D) g.create();
        try {
            Shape moon = new Ellipse2D.Double(positionX, positionY, width, height);
            moonGraphics.clip(moon);

            float radius = Math.max(width, height) / 2.0f;
            float centerX = positionX + width * 0.43f;
            float centerY = positionY + height * 0.38f;
            moonGraphics.setPaint(new RadialGradientPaint(centerX, centerY, radius * 1.3f,
                    new float[]{0.0f, 0.62f, 1.0f},
                    new Color[]{new Color(250, 250, 240), new Color(210, 211, 202), new Color(127, 132, 136)}));
            moonGraphics.fill(moon);

            // The relative coordinates keep crater placement proportional at any moon size.
            drawCrater(moonGraphics, 0.30, 0.30, 0.18, 0.12, new Color(139, 143, 142, 135));
            drawCrater(moonGraphics, 0.64, 0.25, 0.12, 0.09, new Color(151, 153, 151, 125));
            drawCrater(moonGraphics, 0.57, 0.53, 0.22, 0.15, new Color(130, 134, 134, 145));
            drawCrater(moonGraphics, 0.27, 0.66, 0.13, 0.10, new Color(150, 153, 150, 130));
            drawCrater(moonGraphics, 0.73, 0.70, 0.11, 0.08, new Color(119, 124, 124, 140));

            moonGraphics.setColor(new Color(255, 255, 250, 85));
            moonGraphics.draw(moon);
        } finally {
            moonGraphics.dispose();
        }
    }

    private void drawCrater(Graphics2D graphics, double x, double y, double craterWidth, double craterHeight,
                            Color floorColor) {
        double craterX = positionX + width * x;
        double craterY = positionY + height * y;
        double actualWidth = width * craterWidth;
        double actualHeight = height * craterHeight;

        graphics.setColor(new Color(245, 245, 235, 95));
        graphics.fill(new Ellipse2D.Double(craterX - actualWidth * 0.12, craterY - actualHeight * 0.15,
                actualWidth, actualHeight));
        graphics.setColor(floorColor);
        graphics.fill(new Ellipse2D.Double(craterX, craterY, actualWidth, actualHeight));
    }

    @Override
    public void setTimeOfDay(double timeOfDay, int sceneWidth, int sceneHeight) {
        double angle = 2.0 * Math.PI * (timeOfDay + 0.25);
        double altitude = Math.sin(angle);
        visible = altitude > 0.0;
        positionX = (int) Math.round(sceneWidth * 0.5 + Math.cos(angle) * sceneWidth * 0.42 - width / 2.0);
        positionY = (int) Math.round(sceneHeight * 0.78 - altitude * sceneHeight * 0.55 - height / 2.0);
    }
}
