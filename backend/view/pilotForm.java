package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pilotForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel pilotLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel informationLabel;
    private JTextField informationTextField;
    private JButton enginesOffButton;
    private JButton readyButton;
    private JTextField timeToDepartureTextField;
    private JLabel departureTimeLabel;


    private static pilotForm form;

    public static void initForm(pilotForm frm)
    {

    }

    public pilotForm(){
        enginesOffButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        readyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("Pilot Form");
        form = new pilotForm();
        mainFrame.setContentPane(form.mainPanel);
        //initRates(form);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        initForm(form);
    }
}
