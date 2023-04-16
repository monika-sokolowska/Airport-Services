package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cleaningServiceForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel cleaningServiceLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel timeLeftLabel;
    private JTextField timeLeftTextField;
    private JTextField timeToDepartureTextField;
    private JLabel informationLabel;
    private JTextField informationTextField;
    private JButton finishedButton;
    private JLabel departureTimeLabel;

    private static cleaningServiceForm form;

    public static void initForm(cleaningServiceForm frm)
    {

    }

    public cleaningServiceForm(){
        finishedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("Cleaning Service Form");
        form = new cleaningServiceForm();
        mainFrame.setContentPane(form.mainPanel);
        //initRates(form);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        initForm(form);
    }
}
