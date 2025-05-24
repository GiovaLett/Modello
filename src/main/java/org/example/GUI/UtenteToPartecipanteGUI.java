package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UtenteToPartecipanteGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton creaTeamButton;
    private JTable teamTable;
    private JTextField codiceAccessoField;
    private JButton accediButton;

    public UtenteToPartecipanteGUI(Controller c, JFrame origFrame, Hackathon hackathon){

        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        CloseOperation(origFrame);
        setTeamTable(c,hackathon);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);
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

    private void setTeamTable(Controller c, Hackathon hackathon) {
        ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
        teamTable.setModel(modello);
    }


}
