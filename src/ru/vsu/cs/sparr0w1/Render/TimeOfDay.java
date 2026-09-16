package ru.vsu.cs.sparr0w1.Render;

public class TimeOfDay {
    private double time;
    private final double speed;

    public TimeOfDay(double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Animation speed must be positive");
        }
        this.speed = speed;
    }

    public void update() {
        setTime(time + speed);
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time % 1.0;
        if (this.time < 0) {
            this.time += 1.0;
        }
    }

}
