package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class generalManagerForm {
    private JPanel mainPanel;
    private JLabel roleLabel;
    private JLabel generalManagerLabel;
    private JLabel userLabel;
    private JTextField exactUserTextField;
    private JLabel sendMessageLabel;
    private JComboBox usersComboBox;
    private JLabel messageLabel;
    private JTextField messageTextField;
    private JButton sendButton;
    private JLabel assignTimeLabel;
    private JTextField timeTextField;
    private JLabel minutesLabel;
    private JLabel toLabel;
    private JButton assignButton;
    private JComboBox usersStandManagersComboBox;

    private static generalManagerForm form;

    public static void initForm(generalManagerForm frm)
    {

    }

    public generalManagerForm(){
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

    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("General Manager Form");
        form = new generalManagerForm();
        mainFrame.setContentPane(form.mainPanel);
        //initRates(form);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        initForm(form);
    }
}
