package com.example.application.view;

import com.example.application.infrastructure.GeneralManagerClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralManagerForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel generalManagerLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel messageLabel;
    private JTextField messageTextField;
    private JButton sendButton;
    private JLabel assignTimeLabel;
    private JTextField timeTextField;
    private JLabel minutesLabel;
    private JButton assignButton;
    private JButton getButton;
    private JTextField messagetoStandManager;
    private JButton closeButton;
    private JTextField flightNumber;

    private static JFrame frame;

    public GeneralManagerForm(){

        getButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralManagerClient client = new GeneralManagerClient();
                String getMessage = client.get();
                if(getMessage.length()>0){
                    assignButton.setEnabled(true);
                    messageTextField.setText(getMessage);
                }


            }
        });
        assignButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralManagerClient client = new GeneralManagerClient();
                client.post(messagetoStandManager.getText(),
                        timeTextField.getText(),
                        flightNumber.getText());
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
        frame = new JFrame("GeneralManagerForm");
        frame.setContentPane(new GeneralManagerForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
