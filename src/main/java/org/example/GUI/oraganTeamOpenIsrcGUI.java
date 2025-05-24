package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class oraganTeamOpenIsrcGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable teamsTable;
    private JTable giudiciTable;
    private JLabel nomeHackLabel;
    private JLabel IdLabel;

    public oraganTeamOpenIsrcGUI(Controller c, JFrame origFrame, Hackathon hackathon){
        frame=new JFrame(hackathon.getNome());
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(hackathon.getNome());
        IdLabel.setText(hackathon.getID());
        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        setTeamsTable(c,hackathon);
        setGiudiciTable(c, hackathon);
        closeOperation(origFrame);
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


    private void setGiudiciTable(Controller c,Hackathon hackathon){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c.getListaGiudici( hackathon.getID() ) );
        giudiciTable.setModel(modello);

    }

    private void setTeamsTable(Controller c,Hackathon hackathon){
        ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
        teamsTable.setModel(modello);
    }
}
