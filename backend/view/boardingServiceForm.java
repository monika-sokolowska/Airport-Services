package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class boardingServiceForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel boardingServiceLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel timeLeftLabel;
    private JTextField timeLeftTextField;
    private JTextField timeToDepartureTextField;
    private JLabel informationLabel;
    private JTextField informationTextField;
    private JButton finishedButton;
    private JLabel departureTimeLabel;

    private static boardingServiceForm form;

    public static void initForm(boardingServiceForm frm)
    {

    }

    public boardingServiceForm(){
        finishedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("Boarding Service Form");
        form = new boardingServiceForm();
        mainFrame.setContentPane(form.mainPanel);
        //initRates(form);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        initForm(form);
    }
}
