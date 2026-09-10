package ru.vsu.cs.sparr0w1;

import ru.vsu.cs.sparr0w1.UI.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        }
        );
    }
}
