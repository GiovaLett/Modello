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
    private JButton chiudiIscrizioniButton;
    private JTextField nomeHackathonField;
    private JButton aggiungiButton;
    private JPanel creaHackathonPanel;
    private JButton rimuoviButton;


    public organizzatoreGUI(Controller c,JFrame origFrame){

        frame=new JFrame();
        nomeAmministratore.setText(c.getAmministratore().getNome()+" "+c.getAmministratore().getCognome());
        frame.setContentPane(mainPanel);
        setHackathonTable(c);
        frame.pack();
        frame.setLocationRelativeTo(null);

        setRimuoviButton(c);
        setAggiungiButton(c);
        setApriIsrizButton(c);
        setChiudiIscrizioniButton(c);
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

                if(!c.isOpenIscri() && !c.isEventoPronto())  {
                    new organHackPreIscrizGUI( c, frame, hackathon);
                }
                else    {
                    new oraganHackGUI(c,frame,hackathon);
                }

                    frame.setVisible(false);

            }

            });
   }

    public void setRimuoviButton(Controller c) {

        if(!c.isOpenIscri() && !c.isEventoPronto())
            rimuoviButton.setVisible(true);
        else
            rimuoviButton.setVisible(false);

        this.rimuoviButton.addActionListener(new ActionListener() {
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
                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler eliminare l'hackathon: "+
                        hackathon.getNome()+" ?","Conferma",0);
                if(risp==0)
                {
                    c.removeHackathon(hackathon);
                    JOptionPane.showMessageDialog(frame,"Hackathon eliminato!");
                }
            }
        });
    }

    public void setAggiungiButton(Controller c) {

        if(!c.isOpenIscri() && !c.isEventoPronto())
            creaHackathonPanel.setVisible(true);
        else
            creaHackathonPanel.setVisible(false);

        this.aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeHackathon= nomeHackathonField.getText();
                if(nomeHackathon.equals(""))
                    JOptionPane.showMessageDialog(frame,"Digita il nome dell'hackathon\n(non può essere un campo vuoto)");
                else {
                    int risp=JOptionPane.showConfirmDialog(frame,"Confermare il nome <<"+nomeHackathon+">> ?","Conferma",0);
                    if(risp==JOptionPane.YES_OPTION){
                        c.addHackathon(nomeHackathon);
                        JOptionPane.showMessageDialog(frame,"Hackathon aggiunto");
                        setHackathonTable(c);
                    }
                }
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

    public void setApriIsrizButton(Controller c) {

        apriIsrizButton.setVisible(!c.isOpenIscri() && !c.isEventoPronto());
        apriIsrizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di aprire le iscrizioni\n" +
                                "- modifiche ai giudici e agli hackathon non più disponibili\n- Hackathon senza giudici verranno eliminati ",
                        "Conferma?",
                        JOptionPane.YES_NO_OPTION);
                if(risposta==JOptionPane.YES_OPTION) {
                    c.apriIscrizioni();
                    apriIsrizButton.setVisible(false);
                    creaHackathonPanel.setVisible(false);
                    rimuoviButton.setVisible(false);
                    setHackathonTable(c);

                }

            }
        });
    }

    public void setChiudiIscrizioniButton(Controller c) {

        chiudiIscrizioniButton.setVisible(c.isOpenIscri());

        chiudiIscrizioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di chiudere le iscrizioni?", "Conferma?", JOptionPane.YES_NO_OPTION);

                if(risposta==JOptionPane.YES_OPTION) { c.chiudiIscrizioni(); chiudiIscrizioniButton.setVisible(false);}

            }
        });
    }

    private void seeApriIscrizButton(){}
}
