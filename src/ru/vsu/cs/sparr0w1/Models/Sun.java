package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import ru.vsu.cs.sparr0w1.Render.TimeAware;

public class Sun extends Model implements TimeAware {
    private boolean visible;
    public Sun(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    @Override
    public void render(Graphics2D g) {
        if (!visible) {
            return;
        }

        Graphics2D sunGraphics = (Graphics2D) g.create();
        try {
            int centerX = positionX + width / 2;
            int centerY = positionY + height / 2;
            float radius = Math.max(width, height) / 2.0f;

            // A translucent halo makes the sun read as a light source, not a flat circle.
            sunGraphics.setPaint(new RadialGradientPaint(centerX, centerY, radius * 2.3f,
                    new float[]{0.0f, 0.42f, 1.0f},
                    new Color[]{new Color(255, 235, 59, 105), new Color(255, 193, 7, 35),
                            new Color(255, 193, 7, 0)}));
            sunGraphics.fillOval(Math.round(centerX - radius * 2.3f), Math.round(centerY - radius * 2.3f),
                    Math.round(radius * 4.6f), Math.round(radius * 4.6f));

            sunGraphics.setPaint(new RadialGradientPaint(centerX - radius * 0.25f, centerY - radius * 0.3f,
                    radius * 1.25f, new float[]{0.0f, 0.65f, 1.0f},
                    new Color[]{new Color(255, 255, 235), new Color(255, 235, 59), new Color(255, 152, 0)}));
            sunGraphics.fillOval(positionX, positionY, width, height);
        } finally {
            sunGraphics.dispose();
        }
    }

    @Override
    public void setTimeOfDay(double timeOfDay, int sceneWidth, int sceneHeight) {
        setOrbitPosition(timeOfDay, sceneWidth, sceneHeight);
    }

    private void setOrbitPosition(double timeOfDay, int sceneWidth, int sceneHeight) {
        double angle = 2.0 * Math.PI * (timeOfDay - 0.25);
        double altitude = Math.sin(angle);
        visible = altitude > 0.0;
        positionX = (int) Math.round(sceneWidth * 0.5 + Math.cos(angle) * sceneWidth * 0.42 - width / 2.0);
        positionY = (int) Math.round(sceneHeight * 0.78 - altitude * sceneHeight * 0.55 - height / 2.0);
    }
}
