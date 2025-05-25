package org.example.GUI;

import org.example.Controller.Controller;
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

    public PartecipanteGUI(Controller c, JFrame origFrame, Team team){

        frame=new JFrame("Partecipante");
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setSize(500,275);

        nomeTeamLabel.setText(team.getNome());
        IDTeamLabel.setText(team.getID());

        CloseOperation(origFrame);
        setMembriTable(team);

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


}
