package ru.vsu.cs.sparr0w1.Models;

import java.awt.*;


public abstract class Model{
  protected int positionX = 0;
  protected int positionY = 0;
  protected int width = 300;
  protected int height = 300;

  protected Model(int x, int y, int width, int height) {
    positionX = x;
    positionY = y;
    this.width = width;
    this.height = height;
  }

  public int getXPosition() {
    return this.positionX;
  }
  public int getYPosition() {
    return this.positionY;
  }
  public int getWidth() {
    return this.width;
  }
  public int getHeight() {
    return this.height;
  }
  public void setXPosition(int x) {
    this.positionX = x;
  }
  public void setYPosition(int y) {
    this.positionY = y;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  public void setHeight(int height) {
    this.height = height;
  }

  public abstract void render(Graphics2D g);

}
