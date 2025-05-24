package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.ruoli.Partecipante;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtenteCreaTeamGUI {

    private JDialog frame;
    private JPanel mainPanel;
    private JTextField nomeTeamField;
    private JButton creaButton;

   public UtenteCreaTeamGUI(Controller c, Hackathon hackathon){

       frame=new JDialog();
       frame.setContentPane(mainPanel);
       frame.pack();

       setCreaButton(c,hackathon);
       frame.setVisible(true);
   }

   private void setCreaButton(Controller c, Hackathon hackathon){
       creaButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di creare il team con nome:\n"+nomeTeamField,
                       "Conferma",JOptionPane.YES_NO_OPTION);

               if(risposta==JOptionPane.YES_OPTION)
               {
                   String nomeTeam=nomeTeamField.getText();
                   Team team=new Team(nomeTeam,hackathon);

                   c.addPartecToTeam(hackathon,team);
                   hackathon.addTeam(team);
               }

           }
       });
   }
}
