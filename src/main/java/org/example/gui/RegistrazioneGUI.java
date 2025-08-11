package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class RegistrazioneGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField confPasswordField;
    private JButton registratiButton;


    public RegistrazioneGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Registrazione");
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.pack();

        closeOperation(origFrame);
        setRegistratiButton(c,origFrame);
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

    public void setRegistratiButton(Controller c,JFrame origFrame) {
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome= nomeField.getText();
                String cognome=cognomeField.getText();
                String email= emailField.getText();

                String password= passwordField.getText();
                String confePassword= confPasswordField.getText();

                if( !password.equals(confePassword)){
                    JOptionPane.showMessageDialog(frame,"Le due password non coincidono!","Errore password",0);
                    return;
                }
                if(c.isEmailRegisteredYet(email)){
                    JOptionPane.showMessageDialog(frame,"Email già registrata\n(Registarti con un'altra email)");
                }
                else{

                    int risp=JOptionPane.showConfirmDialog(frame,"Confermi le credenziali?:\n " +
                            "Nome: "+nome+"\nCognome: "+cognome+"\nEmail: "+email+"\n","Conferma",JOptionPane.YES_NO_OPTION);
                    if(risp==0)
                    {
                        try{
                        c.addUtenteRegistrato(nome,cognome,email,password);
                        JOptionPane.showMessageDialog(frame,"Account registrato!");
                        frame.dispose();
                        origFrame.setVisible(true);
                        } catch (SQLException _ ){
                            JOptionPane.showMessageDialog(frame,"Errore con il sistema, siamo spiacenti");
                        }
                    }
                }
            }
        });
    }
}
