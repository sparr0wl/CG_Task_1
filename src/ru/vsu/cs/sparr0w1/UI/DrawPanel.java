package ru.vsu.cs.sparr0w1.UI;

import javax.swing.*;
import java.awt.*;

import ru.vsu.cs.sparr0w1.Render.Scene;

public class DrawPanel extends JPanel {
  private final Scene scene;

  public DrawPanel(int panelWidth, int panelHeight, Scene scene) {
      this.scene = scene;
      setPreferredSize(new Dimension(panelWidth, panelHeight));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    scene.render((Graphics2D) g);
  }
}
