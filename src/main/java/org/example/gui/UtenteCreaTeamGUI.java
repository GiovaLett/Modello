package org.example.gui;

import org.example.controller.Controller;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UtenteCreaTeamGUI {

    private JDialog frame;
    private JPanel mainPanel;
    private JTextField nomeTeamField;
    private JButton creaButton;

   public UtenteCreaTeamGUI(Controller c,JFrame origFrame){

       frame=new JDialog((JFrame)null,"Crea Team",true);
       frame.setContentPane(mainPanel);
       frame.pack();

       frame.setLocationRelativeTo(null);
       setCreaButton(c,origFrame);
       frame.setVisible(true);
   }

   private void setCreaButton(Controller c,JFrame origFrame){
       creaButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String nomeTeam=nomeTeamField.getText();
               int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di creare il team con nome:\n"+nomeTeam,
                       "Conferma",JOptionPane.YES_NO_OPTION);

               if(risposta==JOptionPane.YES_OPTION)
               {
                   try{
                    c.creaTeamHC(nomeTeam);

                   frame.dispose();
                   origFrame.dispose();
                   JOptionPane.showMessageDialog(frame,"Sarai indirizzato alla Home, riaccedi per vedere i nuovi aggiornamenti al profilo!");
                   new Home();}
                   catch (IllegalArgumentException exce){
                       JOptionPane.showMessageDialog(frame,exce.getMessage());
                   }
                   catch (SQLException ex){
                       ex.printStackTrace();
                   }
               }

           }
       });
   }
}
