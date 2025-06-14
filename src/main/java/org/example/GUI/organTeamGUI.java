package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.ruoli.Giudice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class organTeamGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeHackLabel;
    private JTable giudiciTable;
    private JTable utentiTable;
    private JTextField idUtenteField;
    private JButton selezGiudButton;
    private JLabel IdLabel;
    private JPanel utentiTabPanel;
    private JTextField idGiudiceField;
    private JPanel rimuovGiudPanel;
    private JButton rimuoviButton;

    organTeamGUI(Controller c, JFrame origFrame, Hackathon hackathon){

        frame=new JFrame("Selezione Giudici");
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(hackathon.getNome());
        IdLabel.setText(hackathon.getID());
        frame.pack();
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setUtentiTable(c);
        setSelezGiudButton(c, hackathon);
        setRimuoviButton( c,hackathon);
        setGiudiciTable(c, hackathon);
        closeOperation(origFrame);

        if(hackathon.getListaGiudici().isEmpty())
            rimuovGiudPanel.setVisible(false);
        else
            rimuovGiudPanel.setVisible(true);

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

    private void setUtentiTable(Controller c){
        ModelloUtentiTabella modello=new ModelloUtentiTabella(c.getListaUtenti());
        utentiTable.setModel(modello);

    }

    private void setSelezGiudButton(Controller c,Hackathon hackathon){
        selezGiudButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IdUtente = idUtenteField.getText();

                try {
                    c.addGiudiceHackaton(hackathon,IdUtente);
                    setGiudiciTable(c,hackathon);
                    setUtentiTable(c);
                    rimuovGiudPanel.setVisible(true);
                    for(Giudice giudice: hackathon.getListaGiudici())
                        System.out.println(giudice.getNome());
                    System.out.println(" ");
                }
                catch(IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(),"Errore", 0);
                }



            }
        });
    }

    public void setRimuoviButton(Controller c,Hackathon hackathon) {


        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IdGiudice = idGiudiceField.getText();
                Giudice giudice;
                try {

                    giudice=c.findIdGiudice(hackathon,IdGiudice);
                    int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler eliminate il giudice:\n"
                            +giudice.getNome()+" "+giudice.getCognome()+" ?","Conferma eliminazione",0);

                    if(risp==0) {
                        c.removeGiudice(giudice, hackathon);
                        JOptionPane.showMessageDialog(frame, "Giudice eliminato!");

                        setGiudiciTable(c, hackathon);
                        setUtentiTable(c);
                        if(hackathon.getListaGiudici().isEmpty())
                            rimuovGiudPanel.setVisible(false);
                        else
                            rimuovGiudPanel.setVisible(true);
                    }




                }
                catch(IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(),"Errore", 0);
                    return;
                }


            }
        });

    }

    private void setGiudiciTable(Controller c, Hackathon hackathon){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c.getListaGiudici( hackathon.getID() ) );
        giudiciTable.setModel(modello);

    }
}
