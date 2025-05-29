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

   public UtenteCreaTeamGUI(Controller c, Hackathon hackathon,JFrame origFrame){

       frame=new JDialog((JFrame)null,"Crea Team",true);
       frame.setContentPane(mainPanel);
       frame.pack();

       frame.setLocationRelativeTo(null);
       setCreaButton(c,hackathon,origFrame);
       frame.setVisible(true);
   }

   private void setCreaButton(Controller c, Hackathon hackathon,JFrame origFrame){
       creaButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String nomeTeam=nomeTeamField.getText();
               int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di creare il team con nome:\n"+nomeTeam,
                       "Conferma",JOptionPane.YES_NO_OPTION);

               if(risposta==JOptionPane.YES_OPTION)
               {
                    c.creaTeam(nomeTeam,hackathon);
                   /*Team team=new Team(nomeTeam,hackathon);

                   c.addPartecToTeam(team);
                   hackathon.addTeam(team);*/
                   frame.dispose();
                   origFrame.dispose();
                   JOptionPane.showMessageDialog(frame,"Sarai indirizzato alla Home, riaccedi per vedere i nuovi aggiornamenti al profilo!");
                   new Home();
               }

           }
       });
   }
}
