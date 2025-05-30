package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class giudiceGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton mostraProblemaButton;
    private JTable teamTable;
    private JLabel nomeCognomeGiudice;
    private JLabel idGiudice;
    private JLabel nomeHackathoneLabel;


   public giudiceGUI(Controller c, JFrame orifFrame,Hackathon hackathon){

       frame=new JFrame("Giudice");
       frame.setContentPane(mainPanel);
       frame.setLocationRelativeTo(null);
       frame.pack();
       closeOperation(orifFrame);

       nomeCognomeGiudice.setText(c.getUtenteCorrente().getNome()+" "+c.getUtenteCorrente().getCognome());
       idGiudice.setText(c.getUtenteCorrente().getID());
       nomeHackathoneLabel.setText(hackathon.getNome());

       setTeamTable(hackathon);
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


    public void setTeamTable(Hackathon hackathon) {
        ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
        teamTable.setModel(modello);
    }

    public void setMostraProblemaButton(Hackathon hackathon) {

        if(hackathon.isView_problema() || !hackathon.isTeam_suffic())
            mostraProblemaButton.setVisible(false);
        else
        {
            mostraProblemaButton.setVisible(true);
        }
    }
}
