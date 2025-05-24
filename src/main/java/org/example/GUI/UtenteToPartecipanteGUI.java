package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        setAccediButton(c,hackathon);
        setTeamTable(c,hackathon);
        setAccediButton(c,hackathon);
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

    private void setAccediButton(Controller c,Hackathon hackathon) {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codiceTeam= codiceAccessoField.getText();
                Team team;
                try {team=c.findCodeAccessTeam(codiceTeam,hackathon);}
                catch(IllegalArgumentException exception)
                {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {c.addPartecToTeam(hackathon,team);}
                catch (IllegalArgumentException exception)
                {JOptionPane.showMessageDialog(frame,exception.getMessage(),"Team Pieno",JOptionPane.INFORMATION_MESSAGE);
                return;}
                JOptionPane.showMessageDialog(frame,"Ora sei membro");
                setTeamTable(c,hackathon);


            }
        });
    }

    private void setCreaTeamButton(Controller c,Hackathon hackathon) {
        creaTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
