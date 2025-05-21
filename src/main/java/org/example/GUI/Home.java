package org.example.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home
{

    private static JFrame frame;
    private JPanel mainPanel;
    private JButton accediButton;
    private JButton registratiButton;

    public Home() {

        frame = new JFrame("Home");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();


        setAccediButton();
        frame.setVisible(true);

    }

    private void setAccediButton(){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccediGUI(frame);
                frame.setVisible(false);
            }
        });

    }

    private void setRegistratiButton(){

    }




}
