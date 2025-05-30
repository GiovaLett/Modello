package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PartecipanteGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable membriTable;
    private JLabel nomeTeamLabel;
    private JLabel IDTeamLabel;
    private JTextArea problemaTextArea;
    private JButton caricaProgressiButton;
    private JLabel codiceLabel;
    private JLabel nomeCognomeLabel;
    private JLabel nomeHackathon;
    private JTable altriTeamsTable;

    public PartecipanteGUI(Controller c, JFrame origFrame, Hackathon hackathon,Team team){

        frame=new JFrame("Partecipante");
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setSize(500,275);

        caricaProgressiButton.setVisible(false);
        nomeTeamLabel.setText(team.getNome());
        IDTeamLabel.setText(team.getID());
        codiceLabel.setText(team.getCodiceAccesso());
        nomeCognomeLabel.setText(c.getUtenteCorrente().getNome()+" "+c.getUtenteCorrente().getCognome());

        nomeHackathon.setText(hackathon.getNome());
        CloseOperation(origFrame);
        setMembriTable(team);
        setAltriTeamsTable(hackathon);
        setProblemaTextArea(hackathon);

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

    private void setMembriTable(Team team){
        ModelloPartecipantiTab modello=new ModelloPartecipantiTab(team.getArrayPartecipante());
        membriTable.setModel(modello);

    }

    public void setAltriTeamsTable(Hackathon hackathon) {
        ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
        altriTeamsTable.setModel(modello);
    }

    public void setProblemaTextArea(Hackathon hackathon) {

        if(hackathon.isView_problema())
            problemaTextArea.setText(hackathon.getProblema());
        else
            problemaTextArea.setText(" Problema non disponibile");
        problemaTextArea.setEditable(false);
    }
}
