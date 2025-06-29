package org.example.GUI;

import org.example.Controller.Controller;


//L'import di questi 2 package serve per poter stampare nome e cognome degli utenti nei messaggi di conferma
import org.example.Model.ruoli.Giudice;
import org.example.Model.ruoli.Utente_registrato;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class organHackPreIscrizGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeHackLabel;
    private JTable giudiciTable;
    private JTable utentiTable;
    private JTextField idUtenteField;
    private JButton selezGiudButton;
    private JLabel IdLabel;
    private JPanel utentiTabPanel;
    private JTextField idGiudiceField;
    private JPanel rimuovGiudPanel;
    private JButton rimuoviButton;
    private JButton inviaRichiestaPerGiudiceButton;

    public organHackPreIscrizGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Selezione Giudici");
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getHackathonCorrente().getNome());
        IdLabel.setText(c.getHackathonCorrente().getID());
        frame.pack();
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setUtentiTable(c);
        setSelezGiudButton(c);
        setRimuoviButton( c);
        setGiudiciTable(c);
        closeOperation(origFrame);

        if(c.getHackathonCorrente().getListaGiudici().isEmpty())
            rimuovGiudPanel.setVisible(false);
        else
            rimuovGiudPanel.setVisible(true);

        setInviaRichiestaPerGiudiceButton( c);
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

    private void setUtentiTable(Controller c){
        ModelloUtentiTabella modello=new ModelloUtentiTabella(c.getListaUtenti());
        utentiTable.setModel(modello);

    }

    private void setSelezGiudButton(Controller c){

        selezGiudButton.setVisible(false);


        selezGiudButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IdUtente = idUtenteField.getText();

                try {
                    c.addGiudiceHackaton(c.getHackathonCorrente(),IdUtente);
                    setGiudiciTable(c);
                    setUtentiTable(c);
                    rimuovGiudPanel.setVisible(true);

                }
                catch(IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(),"Errore", 0);
                }

            }
        });
    }

    public void setRimuoviButton(Controller c) {


        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IdGiudice = idGiudiceField.getText();
                Giudice giudice;
                try {

                    giudice=c.findIdGiudice(c.getHackathonCorrente(),IdGiudice);
                    int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler eliminate il giudice:\n"
                            +giudice.getNome()+" "+giudice.getCognome()+" ?","Conferma eliminazione",0);

                    if(risp==0) {

                        try{
                        c.removeGiudice(giudice, c.getHackathonCorrente());
                        JOptionPane.showMessageDialog(frame, "Giudice eliminato!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        setGiudiciTable(c);
                        setUtentiTable(c);
                        if(c.getHackathonCorrente().getListaGiudici().isEmpty())
                            rimuovGiudPanel.setVisible(false);
                        else
                            rimuovGiudPanel.setVisible(true);
                    }




                }
                catch(IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(),"Errore", 0);
                    return;
                }


            }
        });

    }

    private void setGiudiciTable(Controller c){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c.getListaGiudici( c.getHackathonCorrente().getID() ) );
        giudiciTable.setModel(modello);

    }


    public void setInviaRichiestaPerGiudiceButton(Controller c) {


        inviaRichiestaPerGiudiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String utenteID=idUtenteField.getText();

                try {

                    Utente_registrato utente=c.trovaUtenteForGiudice(c.getHackathonCorrente(),utenteID);


                    int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di mandare la richiesta a\n" +
                            utente.getNome()+" "+utente.getCognome()+" ?","",JOptionPane.YES_NO_OPTION);

                    if(risp==0)
                    {
                        try {
                            c.mandaRichiestaUtenteForGiudice(utente, c.getHackathonCorrente());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }


                }
                catch(IllegalArgumentException exce){
                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }
}
