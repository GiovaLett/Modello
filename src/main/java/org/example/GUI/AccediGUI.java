package org.example.GUI;

import org.example.Controller.Controller;

import org.example.Model.ruoli.*;


import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

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
                try {

                    utente=c.findAccount(email,password);

                }
                catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Errore credenziali",JOptionPane.ERROR_MESSAGE);
                    correct=false;

                }

                if(correct)
                {

                    if(utente instanceof Organizzatore organizzatore)     {
                        c.setUtenteCorrente(organizzatore);
                        new organizzatoreGUI(c,frame);
                    }

                    else if (utente instanceof Partecipante partecipante)  {
                        c.setUtenteCorrente(partecipante);
                        c.setHackathonCorrente( c.findHackId(partecipante.getIDHackathon()) );
                        c.setTeamCorrente( c.findIDTeam(partecipante.getIDTeam(), c.getHackathonCorrente()));
                        new PartecipanteGUI(c,frame);
                    }


                    else if(utente instanceof Giudice giudice)
                    {
                        c.setUtenteCorrente(giudice);
                        c.setHackathonCorrente(  c.findHackId(giudice.getIDHackatlon() )  );
                        new giudiceGUI( c, frame);
                    }

                    else if(utente instanceof Utente_registrato utenteReg) {
                        c.setUtenteCorrente(utenteReg);
                        if(!c.isOpenIscri())
                            new UtenteRegistratoIscrCloseGUI(c,frame);

                        else
                            new UtenteRegistrIscrOpenGUI(c,frame);




                    }


                    frame.setVisible(false);





                }
            }
        });
    }




}
