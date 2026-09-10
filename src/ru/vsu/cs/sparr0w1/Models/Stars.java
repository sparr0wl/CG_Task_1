package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Stars extends Model{
    private Map<Integer, Integer[]> positionStars = new HashMap<Integer, Integer[]>();
    public Stars(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Graphics2D g) {
        
    }
}
