package ru.vsu.cs.sparr0w1.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.vsu.cs.sparr0w1.Render.Scene;
import ru.vsu.cs.sparr0w1.Render.Renderer;

public class DrawPanel extends JPanel implements ActionListener {
  private final Scene scene;
  private final Renderer renderer;
  private final Timer timer;
  public DrawPanel(int panelWidth, int panelHeight, Scene scene, Renderer renderer) {
      this.scene = scene;
      this.renderer = renderer;
      setPreferredSize(new Dimension(panelWidth, panelHeight));
      timer = new Timer(1000 / 60, this);
      timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    scene.render((Graphics2D) g);
  }
  @Override
  public void actionPerformed(final ActionEvent e) {
      if (e.getSource() == timer) {
        renderer.renderFrame();
        repaint();
      }
  }
}
