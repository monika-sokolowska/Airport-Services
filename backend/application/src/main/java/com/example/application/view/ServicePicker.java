package com.example.application.view;

import javax.swing.*;

public class ServicePicker {
    private JPanel mainPanel;
    private JButton boardingServiceButton;
    private JButton cateringServiceButton;
    private JButton cleaningServiceButton;
    private JButton followMeServiceButton;
    private JButton generalManagerButton;
    private JButton luggageServiceButton;
    private JButton pilotButton;
    private JButton pushbackServiceButton;
    private JButton standManagerButton;
    private JButton tankingServiceButton;

    private static JFrame frame;

    public ServicePicker() {
        boardingServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            BoardingServiceForm.open();
        });
        cateringServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            CateringServiceForm.open();
        });
        cleaningServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            CleaningServiceForm.open();
        });
        followMeServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            FollowMeServiceForm.open();
        });
        generalManagerButton.addActionListener(e -> {
            frame.setVisible(false);
            GeneralManagerForm.open();
        });
        luggageServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            LuggageServiceForm.open();
        });
        pilotButton.addActionListener(e -> {
            frame.setVisible(false);
            PilotForm.open();
        });
        pushbackServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            PushbackServiceForm.open();
        });
        standManagerButton.addActionListener(e -> {
            frame.setVisible(false);
            StandManagerForm.open();
        });
        tankingServiceButton.addActionListener(e -> {
            frame.setVisible(false);
            TankingServiceForm.open();
        });
    }
    public static void open() {
        frame = new JFrame("Service Picker");
        frame.setContentPane(new ServicePicker().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        frame = new JFrame("Service Picker");
        frame.setContentPane(new ServicePicker().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
