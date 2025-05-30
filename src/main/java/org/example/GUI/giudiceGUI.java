package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class giudiceGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton pubblicaProblemaButton;
    private JTable teamTable;
    private JLabel nomeCognomeGiudice;
    private JLabel idGiudice;
    private JLabel nomeHackathoneLabel;
    private JButton salvaProblemaButton;
    private JTextArea problemaTextArea;
    private JTextField idTeamField;
    private JButton vediProgressiButton;


    public giudiceGUI(Controller c, JFrame orifFrame,Hackathon hackathon){

       frame=new JFrame("Giudice");
       frame.setContentPane(mainPanel);

       frame.pack();
        frame.setLocationRelativeTo(null);
       closeOperation(orifFrame);

       nomeCognomeGiudice.setText(c.getUtenteCorrente().getNome()+" "+c.getUtenteCorrente().getCognome());
       idGiudice.setText(c.getUtenteCorrente().getID());
       nomeHackathoneLabel.setText(hackathon.getNome());

       problemaTextArea.setText(hackathon.getProblema());
        setSalvaProblemaButton(c,hackathon);
       setTeamTable(hackathon);
        setPubblicaProblemaButton(c,hackathon);
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

    public void setPubblicaProblemaButton(Controller c,Hackathon hackathon) {

            pubblicaProblemaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!c.isEventoPronto()) {
                        JOptionPane.showMessageDialog(frame,"Le iscrizioni sono ancora aperte, non puoi pubblicare il problema");
                        return;
                    }

                    if(!hackathon.isTeam_suffic()){
                        JOptionPane.showMessageDialog(frame,"Purtroppo non si sono formati abbastanza Team\nQuest'hackathon non verrà eseguito");
                        return;
                    }


                    if(hackathon.getProblema().equals("")){
                        JOptionPane.showMessageDialog(frame,"Nessun problema salvato\nScrivi-->Salva Problema-->Pubblica Problema ");
                        return;
                    }

                    JOptionPane.showMessageDialog(frame,"Problema pubblicato");
                    hackathon.setView_problema(true);


                }
            });

    }


    public void setSalvaProblemaButton(Controller c,Hackathon hackathon) {
        salvaProblemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String problema=problemaTextArea.getText();
                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di salvare la seguente traccia:\n"+problema+"\n","Salvataggio",JOptionPane.YES_NO_OPTION);

                if(risp==0)
                    hackathon.setProblema(problema);
            }
        });


    }
}
