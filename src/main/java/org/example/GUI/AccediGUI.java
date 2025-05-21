package org.example.GUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AccediGUI
{
    private JFrame frame=new JFrame("Accesso");
    private JTextField nomeUtenteField;
    private JTextField passwordField;
    private JButton accediButton;
    private JPanel mainPanel;

    AccediGUI(JFrame origFrame){
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();


        CloseOperation( origFrame);
        frame.setVisible(true);
    }


    private void CloseOperation(JFrame origFrame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origFrame.setVisible(true);
                frame.dispose();
            }
        });
    }


}
