package com.example.application.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CleaningServiceForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel cleaningServiceLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel timeLeftLabel;
    private JTextField timeLeftTextField;
    private JLabel timeToDepartureLabel;
    private JTextField timeToDepartureTextField;
    private JLabel informationLabel;
    private JTextField informationTextField;
    private JButton finishedButton;

    public CleaningServiceForm(){
        finishedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void open() {
        JFrame frame = new JFrame("CleaningServiceForm");
        frame.setContentPane(new CleaningServiceForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
