package com.example.application.view;

import com.example.application.infrastructure.PilotClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PilotForm extends JFrame{
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel pilotLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel informationLabel;
    private JTextField informationTextField;
    private JButton enginesOffButton;
    private JButton readyButton;
    private JButton closeButton;

    private static JFrame frame;

    public PilotForm(){

        enginesOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PilotClient().post();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ServicePicker.open();
            }
        });
    }

    public static void open() {
        frame = new JFrame("PilotForm");
        frame.setContentPane(new PilotForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
