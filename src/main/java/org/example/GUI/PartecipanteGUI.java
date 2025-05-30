package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.Progresso;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JList progressiList;

    public PartecipanteGUI(Controller c, JFrame origFrame, Hackathon hackathon,Team team){

        frame=new JFrame("Partecipante");
        frame.setContentPane(mainPanel);
        //frame.setSize(500,275);
        frame.pack();
        frame.setLocationRelativeTo(null);

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
        setProgressiList(team);
        setCaricaProgressiButton(c,hackathon,team);
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

    private void setAltriTeamsTable(Hackathon hackathon) {
        ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
        altriTeamsTable.setModel(modello);
    }

    private void setProblemaTextArea(Hackathon hackathon) {

        if(hackathon.isView_problema())
            problemaTextArea.setText(hackathon.getProblema());
        else
            problemaTextArea.setText(" Problema non disponibile");
        problemaTextArea.setEditable(false);
    }

    private void setCaricaProgressiButton(Controller c,Hackathon hackathon,Team team){

        if(!hackathon.isView_problema())
            caricaProgressiButton.setVisible(false);
        else{
            caricaProgressiButton.setVisible(true);

            caricaProgressiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new partecipanteAddProgressiGUI(c,team,frame);
                    frame.setVisible(false);
                }
            });
        }

    }

    public void setProgressiList(Team team) {
        DefaultListModel modello=new DefaultListModel<>();
        for(Progresso progresso:team.getArrayProgresso()){
            modello.add(0,progresso.getNome());
        }
        progressiList.setModel(modello);
    }
}
