package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.ruoli.Giudice;
import org.example.Model.ruoli.Organizzatore;
import org.example.Model.ruoli.Partecipante;
import org.example.Model.ruoli.Utente_registrato;

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

    private void setAccediButton(Controller c){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean correct=true;

                Utente_registrato utente=null;

                String email=emailField.getText();
                String password=passwordField.getText();
                try { utente=c.findAccount(email,password); }
                catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Errore credenziali",JOptionPane.ERROR_MESSAGE);
                    correct=false;
                }

                if(correct)
                {
                    if(utente instanceof Organizzatore);
                        //organizzatoreGUI()
                    else if(utente instanceof Giudice);
                        //giudiceGUI();
                    else if (utente instanceof Partecipante);
                        //partecipanteGUI();
                }
            }
        });
    }




}
