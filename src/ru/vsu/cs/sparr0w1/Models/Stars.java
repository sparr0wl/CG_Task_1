package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.vsu.cs.sparr0w1.Render.TimeAware;

public class Stars extends Model implements TimeAware {
    private final List<Star> stars = new ArrayList<>();
    private double timeOfDay;

    public Stars(int x, int y, int width, int height) {
        this(x, y, width, height, 140, System.nanoTime());
    }

    public Stars(int x, int y, int width, int height, int count, long seed) {
        super(x, y, width, height);
        if (count < 0) {
            throw new IllegalArgumentException("Star count must not be negative");
        }

        Random random = new Random(seed);
        for (int i = 0; i < count; i++) {
            double starX = random.nextDouble() * width;
            double starY = random.nextDouble() * height * 0.78;
            float radius = 0.7f + random.nextFloat() * 1.9f;
            float baseBrightness = 0.45f + random.nextFloat() * 0.55f;
            float twinkleSpeed = 5.0f + random.nextFloat() * 13.0f;
            float phase = random.nextFloat() * (float) (2.0 * Math.PI);
            Color color = random.nextFloat() < 0.18f
                    ? new Color(190, 220, 255)
                    : new Color(255, 248, 218);
            stars.add(new Star(starX, starY, radius, baseBrightness, twinkleSpeed, phase, color));
        }
    }

    @Override
    public void render(Graphics2D g) {
        double nightOpacity = getNightOpacity();
        if (nightOpacity <= 0.0) {
            return;
        }

        Graphics2D starGraphics = (Graphics2D) g.create();
        try {
            for (Star star : stars) {
                double flicker = 0.70 + 0.30 * Math.sin(timeOfDay * 2.0 * Math.PI * star.twinkleSpeed + star.phase);
                float brightness = (float) Math.min(1.0, star.baseBrightness * flicker * nightOpacity);
                if (brightness < 0.03f) {
                    continue;
                }

                float x = (float) (positionX + star.x);
                float y = (float) (positionY + star.y);
                float glowRadius = star.radius * (2.4f + brightness * 2.0f);
                int alpha = Math.round(brightness * 135);
                starGraphics.setPaint(new RadialGradientPaint(x, y, glowRadius,
                        new float[]{0.0f, 0.35f, 1.0f},
                        new Color[]{withAlpha(star.color, alpha), withAlpha(star.color, alpha / 3),
                                withAlpha(star.color, 0)}));
                starGraphics.fillOval(Math.round(x - glowRadius), Math.round(y - glowRadius),
                        Math.round(glowRadius * 2), Math.round(glowRadius * 2));

                starGraphics.setColor(withAlpha(star.color, Math.round(brightness * 255)));
                float coreRadius = Math.max(0.6f, star.radius * brightness);
                starGraphics.fillOval(Math.round(x - coreRadius), Math.round(y - coreRadius),
                        Math.max(1, Math.round(coreRadius * 2)), Math.max(1, Math.round(coreRadius * 2)));

                if (brightness > 0.78f && star.radius > 1.7f) {
                    starGraphics.setColor(withAlpha(star.color, Math.round(brightness * 130)));
                    int ray = Math.round(star.radius * 2.5f);
                    starGraphics.drawLine(Math.round(x - ray), Math.round(y), Math.round(x + ray), Math.round(y));
                    starGraphics.drawLine(Math.round(x), Math.round(y - ray), Math.round(x), Math.round(y + ray));
                }
            }
        } finally {
            starGraphics.dispose();
        }
    }

    @Override
    public void setTimeOfDay(double timeOfDay, int sceneWidth, int sceneHeight) {
        this.timeOfDay = timeOfDay;
    }

    private double getNightOpacity() {
        double sunHeight = Math.sin(2.0 * Math.PI * (timeOfDay - 0.25));
        return Math.max(0.0, Math.min(1.0, (-sunHeight - 0.03) / 0.55));
    }

    private Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, alpha)));
    }

    private static class Star {
        private final double x;
        private final double y;
        private final float radius;
        private final float baseBrightness;
        private final float twinkleSpeed;
        private final float phase;
        private final Color color;

        private Star(double x, double y, float radius, float baseBrightness, float twinkleSpeed, float phase,
                     Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.baseBrightness = baseBrightness;
            this.twinkleSpeed = twinkleSpeed;
            this.phase = phase;
            this.color = color;
        }
    }
}
