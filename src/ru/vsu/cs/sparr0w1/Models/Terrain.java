package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terrain extends Model {
    private final List<Point2D> horizon = new ArrayList<>();
    private final List<GroundDetail> details = new ArrayList<>();

    public Terrain(int x, int y, int width, int height) {
        this(x, y, width, height, System.nanoTime());
    }

    public Terrain(int x, int y, int width, int height, long seed) {
        super(x, y, width, height);
        generate(seed);
    }

    public final void generate(long seed) {
        horizon.clear();
        details.clear();
        Random random = new Random(seed);

        int horizonPoints = Math.max(12, width / 45);
        for (int i = 0; i <= horizonPoints; i++) {
            double x = (double) i * width / horizonPoints;
            double wave = Math.sin(i * 0.82) * height * 0.025;
            double y = height * (0.075 + random.nextDouble() * 0.055) + wave;
            horizon.add(new Point2D.Double(x, y));
        }

        for (int i = 0; i < Math.max(55, width / 9); i++) {
            double depth = 0.18 + random.nextDouble() * 0.78;
            double x = random.nextDouble() * width;
            double y = height * depth;
            double size = 1.0 + depth * (2.5 + random.nextDouble() * 3.0);
            details.add(new GroundDetail(x, y, size, random.nextBoolean()));
        }
    }

    @Override
    public void render(Graphics2D g) {
        Path2D ground = createGroundPath();
        Graphics2D terrainGraphics = (Graphics2D) g.create();
        try {
            terrainGraphics.setPaint(new GradientPaint(positionX, positionY, new Color(95, 143, 74),
                    positionX, positionY + height, new Color(29, 79, 43)));
            terrainGraphics.fill(ground);

            terrainGraphics.clip(ground);
            drawGroundDetails(terrainGraphics);
        } finally {
            terrainGraphics.dispose();
        }
    }

    private Path2D createGroundPath() {
        Path2D path = new Path2D.Double();
        Point2D first = horizon.get(0);
        path.moveTo(positionX + first.getX(), positionY + first.getY());
        for (int i = 1; i < horizon.size(); i++) {
            Point2D point = horizon.get(i);
            path.lineTo(positionX + point.getX(), positionY + point.getY());
        }
        path.lineTo(positionX + width, positionY + height);
        path.lineTo(positionX, positionY + height);
        path.closePath();
        return path;
    }

    private void drawGroundDetails(Graphics2D g) {
        for (GroundDetail detail : details) {
            int x = (int) Math.round(positionX + detail.x);
            int y = (int) Math.round(positionY + detail.y);
            int size = Math.max(1, (int) Math.round(detail.size));

            if (detail.isGrass) {
                g.setColor(new Color(18, 67, 34, 95));
                g.drawLine(x, y, x - size, y - size * 2);
                g.drawLine(x + size, y, x + size * 2, y - size);
                g.setColor(new Color(153, 190, 94, 65));
                g.drawLine(x, y, x + size, y - size * 2);
            } else {
                g.setColor(new Color(50, 94, 48, 85));
                g.fillOval(x - size, y - Math.max(1, size / 2), size * 2, Math.max(1, size));
            }
        }
    }

    private static class GroundDetail {
        private final double x;
        private final double y;
        private final double size;
        private final boolean isGrass;

        private GroundDetail(double x, double y, double size, boolean isGrass) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.isGrass = isGrass;
        }
    }
}
