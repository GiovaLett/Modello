package org.example.gui;

import org.example.controller.Controller;


//L'import di questi 2 package serve per poter stampare nome e cognome degli utenti nei messaggi di conferma

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class OrganHackPreIscrizGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeHackLabel;
    private JTable giudiciTable;
    private JTable utentiTable;
    private JTextField idUtenteField;
    private JButton selezGiudButton;
    private JLabel idLabel;
    private JTextField idGiudiceField;
    private JPanel rimuovGiudPanel;
    private JButton rimuoviButton;
    private JButton inviaRichiestaPerGiudiceButton;

    public OrganHackPreIscrizGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Selezione Giudici");
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getNomeHackCorr());
        idLabel.setText(c.getIdHackCorr());
        frame.pack();
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setUtentiTable(c);
        setSelezGiudButton(c);
        setRimuoviButton( c);
        setGiudiciTable(c);
        closeOperation(origFrame);


        rimuovGiudPanel.setVisible(!(c.isListaGiudHackCorrEmpty()));


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
        ModelloUtentiTabella modello=new ModelloUtentiTabella(c);
        utentiTable.setModel(modello);

    }

    private void setSelezGiudButton(Controller c){

        selezGiudButton.setVisible(false);


        selezGiudButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idUtente = idUtenteField.getText();

                try {
                    c.addGiudiceHackCorr(idUtente);
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
                String idGiudice = idGiudiceField.getText();
                int risp=-1;
                try {

                    c.identificaGiudiceSelezionatoHackCorr(idGiudice);
                     risp = JOptionPane.showConfirmDialog(frame, "Sicuro di voler eliminate il giudice:\n"
                            + c.getNomeGiudSel() + " " + c.getCognomeGiudSel() + " ?", "Conferma eliminazione", 0);
                }catch (IllegalArgumentException exc){
                    JOptionPane.showMessageDialog(frame,exc.getMessage());
                }
                    if(risp==0) {

                        try{
                        c.removeGiudiceSelHackCorr();
                        JOptionPane.showMessageDialog(frame, "Giudice eliminato!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        setGiudiciTable(c);
                        setUtentiTable(c);

                        rimuovGiudPanel.setVisible(!(c.isListaGiudHackCorrEmpty()));

                    }
                }

        });

    }

    private void setGiudiciTable(Controller c){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c );
        giudiciTable.setModel(modello);

    }


    public void setInviaRichiestaPerGiudiceButton(Controller c) {


        inviaRichiestaPerGiudiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String utenteID=idUtenteField.getText();
                int risp=-1;

                try {

                    c.identificaUtenteSelezionatoHackCorr(utenteID);

                    risp = JOptionPane.showConfirmDialog(frame, "Sicuro di mandare la richiesta a\n" +
                            c.getNomeUtenSele() + " " + c.getCognomeUtenSele() + " ?", "", JOptionPane.YES_NO_OPTION);
                }catch (IllegalArgumentException exc){
                    JOptionPane.showMessageDialog(frame,exc.getMessage());
                }
                    if(risp==0)
                    {
                        try {
                            c.mandaRichiestaUtenteSelForGiudiceHackCorr();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

            }
        });
    }
}
