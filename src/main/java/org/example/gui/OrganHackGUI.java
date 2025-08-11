package org.example.gui;

import org.example.controller.Controller;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class OrganHackGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable teamsTable;
    private JTable giudiciTable;
    private JLabel nomeHackLabel;
    private JLabel idLabel;
    private JButton terminaQuestoHackathonButton;
    private JLabel teamsOrClassificaLabels;
    private JButton pubblicaClassificaButton;
    private JLabel noHackLabel;

    public OrganHackGUI(Controller c, JFrame origFrame){


        frame=new JFrame(c.getNomeHackCorr());
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getNomeHackCorr());
        idLabel.setText(c.getIdHackCorr());
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setTeamsTable(c);
        setGiudiciTable(c);
        setTerminaQuestoHackathonButton(c);
        setPubblicaClassificaButton(c);

        closeOperation(origFrame);
        frame.setVisible(true);
        cotrolloTeamSuff(c);

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


    private void setGiudiciTable(Controller c){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c);
        giudiciTable.setModel(modello);

    }

    private void setTeamsTable(Controller c){


        if(c.isVotazioneConclusaHackCorr())
        {
            teamsOrClassificaLabels.setText("Classifica:");
            c.ordinaTeamVoti();
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c);
            teamsTable.setModel(modello);
        }
        else{
            teamsOrClassificaLabels.setText("Teams:");
            ModelloTeamsTab modello=new ModelloTeamsTab(c);
            teamsTable.setModel(modello);
        }



    }


    public void setTerminaQuestoHackathonButton(Controller c) {


        terminaQuestoHackathonButton.setVisible(!c.isEventoFinitoHackCorr() && c.isViewProblemaHackCorr());



        terminaQuestoHackathonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler forzare la chiusura dell'Hackathon prima della durata prestabilita?",
                        "Termine Hackathon",JOptionPane.YES_NO_OPTION);

                if(risp==0){

                    try{
                        c.setEventoFinito(true);
                        terminaQuestoHackathonButton.setVisible(false);
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }

                }


            }
        });

    }

    public void setPubblicaClassificaButton(Controller c) {


        pubblicaClassificaButton.setVisible(c.isVotazioneConclusaHackCorr() && !c.isClassificaPubblHackCorr());



        pubblicaClassificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    c.pubblicaClassifica();
                    pubblicaClassificaButton.setVisible(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }


    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.isTeamSuffHackCorr()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (teams insufficienti)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }


}
