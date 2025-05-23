package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class organizzatoreGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeAmministratore;
    private JButton apriIsrizButton;
    private JTable hackathonTable;
    private JTextField idHackField;
    private JButton entraButton;


    public organizzatoreGUI(Controller c,JFrame origFrame){

        frame=new JFrame();
        nomeAmministratore.setText(c.getAmministratore().getNome()+" "+c.getAmministratore().getCognome());
        frame.setContentPane(mainPanel);
        setHackathonTable(c);
        frame.pack();
        frame.setLocationRelativeTo(null);

        setApriIsrizButton();
        setEntraButton(c);
        CloseOperation( origFrame);
        frame.setVisible(true);


    }

    private void seeGiudiciTable(){

        /*if(c.Listagiudici.size()>0)
        * giudiciTable.setVisible(true)*/
    }



   private void setHackathonTable(Controller c){
        ModelloHackTab modello=new ModelloHackTab(c.getListaHackathon());
        hackathonTable.setModel(modello);
   }

   private void setEntraButton(Controller c){
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();
                Hackathon hackathon;

                try {
                    hackathon = c.findHackId(idHack);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                new organTeamGUI( c, frame, hackathon);
                frame.setVisible(false);

            }

            });
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

    public void setApriIsrizButton() {
        apriIsrizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di aprire le iscrizioni\n(modifiche ai giudici non più disponibili)",
                        "Conferma?",
                        JOptionPane.YES_NO_OPTION);
                if(risposta==JOptionPane.YES_OPTION);
                if(risposta==JOptionPane.NO_OPTION);
            }
        });
    }

    private void seeApriIscrizButton(){}
}
