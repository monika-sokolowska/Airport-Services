package com.example.application.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StandManagerForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel standManagerLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel informationLabel;
    private JTextField informationTextField;
    private JLabel sendMessageLabel;
    private JComboBox rolesUsersComboBox;
    private JTextField messageTextField;
    private JLabel messageLabel;
    private JButton sendButton;
    private JLabel assignTimeLabel;
    private JTextField timeTextField;
    private JLabel toLabel;
    private JComboBox usersComboBox;
    private JLabel minutesLabel;
    private JButton assignButton;

    private static StandManagerForm form;

    public static void initForm(StandManagerForm frm)
    {

    }

    public StandManagerForm(){
        sendButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        assignButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void open() {
        JFrame frame = new JFrame("StandManagerForm");
        frame.setContentPane(new StandManagerForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
