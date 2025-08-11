package org.example.gui;

import org.example.controller.Controller;


import javax.swing.*;
import java.awt.event.*;


public class AccediGUI
{
    private JFrame frame=new JFrame("Accesso");
    private JTextField emailField;
    private JTextField passwordField;
    private JButton accediButton;
    private JPanel mainPanel;

    AccediGUI(JFrame origFrame, Controller c){
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();


        setAccediButton(c);
        closeOperation( origFrame);
        frame.setVisible(true);
    }


    private void closeOperation(JFrame origFrame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origFrame.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void setAccediButton(Controller c){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email=emailField.getText();
                String password=passwordField.getText();

                try {

                    int n=c.identificaUtente(email,password);

                    switch (n){
                        case 0:
                            new OrganizzatoreGUI(c,frame);
                            break;
                        case 1:
                            new GiudiceGUI( c, frame);
                            break;
                        case 2:
                            new PartecipanteGUI(c,frame);
                            break;
                        case 3:
                            new UtenteRegistratoIscrCloseGUI(c,frame);
                            break;
                        case 4:
                            new UtenteRegistrIscrOpenGUI(c,frame);
                            break;
                        default:
                            JOptionPane.showMessageDialog(frame,"Utente non riconosciuto");

                    }
                }
                catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Errore credenziali",JOptionPane.ERROR_MESSAGE);
                    return;

                }

                frame.setVisible(false);
            }
        });
    }
}
