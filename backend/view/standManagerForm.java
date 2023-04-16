package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class standManagerForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel standManagerLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel timeLeftLabel;
    private JTextField timeLeftTextField;
    private JLabel departureTimeLabel;
    private JTextField timeToDepartureTextField;
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
    private JButton startButton;

    private static standManagerForm form;

    public static void initForm(standManagerForm frm)
    {

    }

    public standManagerForm(){
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

        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("Stand Manager Form");
        form = new standManagerForm();
        mainFrame.setContentPane(form.mainPanel);
        //initRates(form);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        initForm(form);
    }
}
