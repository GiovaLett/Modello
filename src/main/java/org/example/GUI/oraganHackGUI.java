package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    public oraganHackGUI(Controller c, JFrame origFrame, Hackathon hackathon){
        frame=new JFrame(hackathon.getNome());
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(hackathon.getNome());
        IdLabel.setText(hackathon.getID());
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setTeamsTable(c,hackathon);
        setGiudiciTable(c, hackathon);
        setTerminaQuestoHackathonButton(c, hackathon);
        setPubblicaClassificaButton( hackathon);

        closeOperation(origFrame);
        frame.setVisible(true);
        cotrolloTeamSuff(c, hackathon);

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


    private void setGiudiciTable(Controller c,Hackathon hackathon){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c.getListaGiudici( hackathon.getID() ) );
        giudiciTable.setModel(modello);

    }

    private void setTeamsTable(Controller c,Hackathon hackathon){


        if(hackathon.isVotazioneConclusa())
        {
            TeamsOrClassificaLabels.setText("Classifica:");
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(hackathon.getListaTeam());
            teamsTable.setModel(modello);
        }
        else{
            TeamsOrClassificaLabels.setText("Teams:");
            ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
            teamsTable.setModel(modello);
        }



    }


    public void setTerminaQuestoHackathonButton(Controller c, Hackathon hackathon) {

        if(!hackathon.isEventoFinito() && hackathon.isView_problema())
            terminaQuestoHackathonButton.setVisible(true);
        else
            terminaQuestoHackathonButton.setVisible(false);


        terminaQuestoHackathonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler terminare l'Hackathon",
                        "Termine Hackathon",JOptionPane.YES_NO_OPTION);

                if(risp==0){
                    hackathon.setEventoFinito(true);
                    terminaQuestoHackathonButton.setVisible(false);
                }


            }
        });

    }

    public void setPubblicaClassificaButton(Hackathon hackathon) {

        if(hackathon.isVotazioneConclusa() && !hackathon.isClassificaPubblicata())
            pubblicaClassificaButton.setVisible(true);
        else
            pubblicaClassificaButton.setVisible(false);


        pubblicaClassificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hackathon.setClassificaPubblicata(true);
                pubblicaClassificaButton.setVisible(false);
            }
        });
    }


    private void cotrolloTeamSuff(Controller c,Hackathon hackathon)
    {
        if(c.isEventoPronto() && !hackathon.isTeam_suffic()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (solo 1 team iscritto)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }


}
