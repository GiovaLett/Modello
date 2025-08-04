package org.example.GUI;

import org.example.controller.Controller;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class oraganHackGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable teamsTable;
    private JTable giudiciTable;
    private JLabel nomeHackLabel;
    private JLabel IdLabel;
    private JButton terminaQuestoHackathonButton;
    private JLabel TeamsOrClassificaLabels;
    private JButton pubblicaClassificaButton;
    private JLabel noHackLabel;

    public oraganHackGUI(Controller c, JFrame origFrame){


        frame=new JFrame(c.getNomeHackCorr());
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getNomeHackCorr());
        IdLabel.setText(c.getIdHackCorr());
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
            TeamsOrClassificaLabels.setText("Classifica:");
            c.ordinaTeamVoti();
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c);
            teamsTable.setModel(modello);
        }
        else{
            TeamsOrClassificaLabels.setText("Teams:");
            ModelloTeamsTab modello=new ModelloTeamsTab(c);
            teamsTable.setModel(modello);
        }



    }


    public void setTerminaQuestoHackathonButton(Controller c) {

        if(!c.isEventoFinitoHackCorr() && c.isViewProblemaHackCorr())
            terminaQuestoHackathonButton.setVisible(true);
        else
            terminaQuestoHackathonButton.setVisible(false);


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

        if(c.isVotazioneConclusaHackCorr() && !c.isClassificaPubblHackCorr())
            pubblicaClassificaButton.setVisible(true);
        else
            pubblicaClassificaButton.setVisible(false);


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
