package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class Mountain extends Model{
    private final List<Point2D> vertex = new ArrayList<>();
    private final int vertexCount;
    private final double peakHeight;
    private final double roughRidge;
    private final Random generator;
    public Mountain(int x, int y, int width, int height, int vertexCount, double peakHeight, double roughRidge, long seed){
        super(x, y, width, height);
        this.vertexCount = vertexCount;
        this.peakHeight = peakHeight;
        this.roughRidge = roughRidge;
        this.generator = new Random(seed);
    }


    public void generateVertex() {
        if (vertexCount < 3) {
            throw new IllegalArgumentException("a mountain needs at least three ridge vertices");
        }
        if (peakHeight <= 0.0 || peakHeight >= 1.0 || roughRidge < 0.0) {
            throw new IllegalArgumentException("peakHeight must be in >0 <1, roughRidge must be positive");
        }

        vertex.clear();

        double baseY = height;
        double peakX = width * (0.35 + generator.nextDouble() * 0.30);
        double peakY = height * (1.0 - peakHeight);

        vertex.add(new Point2D.Double(0, baseY));

        for (int i = 0; i < vertexCount; i++) {
            double x = (double) i * width / (vertexCount - 1);
            Point2D point = new Point2D.Double();
            double ridgeY;
            if (x <= peakX) {
                ridgeY = baseY + (peakY - baseY) * x / peakX;
            } else {
                ridgeY = peakY + (baseY - peakY) * (x - peakX) / (width - peakX);
            }
            double edgeFactor = Math.sin(Math.PI * x / width);
            double noise = (generator.nextDouble() * 2.0 - 1.0)
                    * roughRidge * height * edgeFactor;

            vertex.add(new Point2D.Double(x, Math.max(0, Math.min(baseY, ridgeY + noise))));
        }

        vertex.add(new Point2D.Double(width, baseY));
    }

    @Override
    public void render(Graphics2D g) {
        if (vertex.isEmpty()) {
            return;
        }

        Path2D path = createMountainPath();
        Graphics2D mountainGraphics = (Graphics2D) g.create();
        try {
            mountainGraphics.setPaint(new GradientPaint(positionX, positionY, new Color(112, 130, 138),
                    positionX, positionY + height, new Color(43, 58, 64)));
            mountainGraphics.fill(path);

            mountainGraphics.clip(path);
            mountainGraphics.setPaint(new GradientPaint(positionX + width * 0.42f, positionY + height * 0.18f,
                    new Color(20, 35, 42, 0), positionX + width, positionY + height,
                    new Color(14, 25, 30, 145)));
            mountainGraphics.fillRect(positionX, positionY, width, height);

            Path2D snowCap = createSnowCap();
            mountainGraphics.setPaint(new GradientPaint(positionX, positionY, new Color(250, 253, 255),
                    positionX, positionY + height * 0.35f, new Color(187, 211, 223)));
            mountainGraphics.fill(snowCap);
        } finally {
            mountainGraphics.dispose();
        }
    }

    private Path2D createMountainPath() {
        Path2D path = new Path2D.Double();
        Point2D first = vertex.get(0);
        path.moveTo(positionX + first.getX(), positionY + first.getY());

        for (int i = 1; i < vertex.size(); i++) {
            Point2D point = vertex.get(i);
            path.lineTo(positionX + point.getX(), positionY + point.getY());
        }

        path.closePath();
        return path;
    }

    private Path2D createSnowCap() {
        int peakIndex = 0;
        for (int i = 1; i < vertex.size(); i++) {
            if (vertex.get(i).getY() < vertex.get(peakIndex).getY()) {
                peakIndex = i;
            }
        }

        double snowDepth = height * 0.25;
        double snowLimit = vertex.get(peakIndex).getY() + snowDepth;
        int left = peakIndex;
        int right = peakIndex;
        while (left > 0 && vertex.get(left -1).getY() <= snowLimit) {
            left--;
        }
        while (right < vertex.size() - 1 && vertex.get(right + 1).getY() <= snowLimit) {
            right++;
        }

        Path2D cap = new Path2D.Double();
        Point2D start = vertex.get(left);
        cap.moveTo(positionX + start.getX(), positionY + start.getY());
        for (int i = left + 1; i <= right; i++) {
            Point2D point = vertex.get(i);
            cap.lineTo(positionX + point.getX(), positionY + point.getY());
        }
        for (int i = right; i >= left; i--) {
            Point2D point = vertex.get(i);
            double wave = 0.65 + 0.35 * Math.sin(i * 2.31);
            cap.lineTo(positionX + point.getX(), positionY + Math.min(height, point.getY() + snowDepth * wave));
        }
        cap.closePath();
        return cap;
    }
}
