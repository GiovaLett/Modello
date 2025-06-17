package org.example.GUI;

import org.example.Controller.Controller;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UtenteRegistratoIscrCloseGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable hackathonTable;


    public UtenteRegistratoIscrCloseGUI(Controller c,JFrame origFrame)
    {
        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        setHackathonTable(c);


        CloseOperation(origFrame);
        frame.setVisible(true);
        messageToBeJudge(c);



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

    public void setHackathonTable(Controller c) {
        ModelloHackTab modello=new ModelloHackTab(c.getListaHackathon());
        hackathonTable.setModel(modello);
    }

    private void messageToBeJudge(Controller c){
        String richiesta=c.getUtenteCorrente().getRichiestaGiudice();
        if(richiesta.equals(""));
        else {
            int risp=JOptionPane.showConfirmDialog(frame,richiesta,"Vuoi essere giudice?",JOptionPane.YES_NO_OPTION);
            if(risp==0)
            {
                c.diventaGiudice();
                JOptionPane.showMessageDialog(frame,"Sei diventato giudice\n Riaccedi per vedere i cambiamenti");

            }
            else
                c.getUtenteCorrente().setRichiestaGiudice("");
        }
    }
}
