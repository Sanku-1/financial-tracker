package com.pluralsight;

import javax.swing.*;

public class ProjectWindow {
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame financialTrackerFrame = new JFrame("Financial Tracker");
        financialTrackerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Test");
        financialTrackerFrame.getContentPane().add(label);


        //Display the window.
        financialTrackerFrame.setVisible(true);
        financialTrackerFrame.setSize(1200, 800);
        financialTrackerFrame.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }

        });
    }
}
