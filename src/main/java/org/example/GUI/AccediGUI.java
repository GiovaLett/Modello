package org.example.GUI;

import javax.swing.*;
import java.awt.event.*;

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

    private void setAccediButton(){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //controller.confrontaCredenziali(String utente,String password);
            }
        });
    }




}
