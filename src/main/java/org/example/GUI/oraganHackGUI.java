package org.example.GUI;

import org.example.Controller.Controller;


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


        frame=new JFrame(c.getHackathonCorrente().getNome());
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getHackathonCorrente().getNome());
        IdLabel.setText(c.getHackathonCorrente().getID());
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
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c.getListaGiudici( c.getHackathonCorrente().getID() ) );
        giudiciTable.setModel(modello);

    }

    private void setTeamsTable(Controller c){


        if(c.getHackathonCorrente().isVotazioneConclusa())
        {
            TeamsOrClassificaLabels.setText("Classifica:");
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c.getHackathonCorrente().getListaTeam());
            teamsTable.setModel(modello);
        }
        else{
            TeamsOrClassificaLabels.setText("Teams:");
            ModelloTeamsTab modello=new ModelloTeamsTab(c.getHackathonCorrente().getListaTeam());
            teamsTable.setModel(modello);
        }



    }


    public void setTerminaQuestoHackathonButton(Controller c) {

        if(!c.getHackathonCorrente().isEventoFinito() && c.getHackathonCorrente().isView_problema())
            terminaQuestoHackathonButton.setVisible(true);
        else
            terminaQuestoHackathonButton.setVisible(false);


        terminaQuestoHackathonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler terminare l'Hackathon",
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

        if(c.getHackathonCorrente().isVotazioneConclusa() && !c.getHackathonCorrente().isClassificaPubblicata())
            pubblicaClassificaButton.setVisible(true);
        else
            pubblicaClassificaButton.setVisible(false);


        pubblicaClassificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.getHackathonCorrente().setClassificaPubblicata(true);
                pubblicaClassificaButton.setVisible(false);
            }
        });
    }


    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.getHackathonCorrente().isTeam_suffic()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (teams insufficienti)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }


}
