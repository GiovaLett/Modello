package org.example.GUI;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
    private JPanel vediProgressiPanel;
    private JButton assegnaVotiButton;
    private JPanel problemaButtonPanel;
    private JButton caricaValutazioniButton;
    private JLabel noHackLabel;


    public giudiceGUI(Controller c, JFrame orifFrame){

       frame=new JFrame("Giudice");
       frame.setContentPane(mainPanel);

       frame.pack();
        frame.setLocationRelativeTo(null);
       closeOperation(orifFrame);

       nomeCognomeGiudice.setText(c.getNomeUtenCorr()+" "+c.getCognomeUtenCorr());
       idGiudice.setText(c.getIdUtenCorr());
       nomeHackathoneLabel.setText(c.getNomeHackCorr());


      setProblemaTextArea(c);
       setSalvaProblemaButton(c);
       setTeamTable(c);
       setPubblicaProblemaButton(c);

        if(!c.isViewProblemaHackCorr())
            vediProgressiPanel.setVisible(false);

        if(c.isEventoFinitoHackCorr())
            problemaButtonPanel.setVisible(false);


        setAssegnaVoto( c);
       setVediProgressiButton(c);
        setCaricaValutazioniButton(c);

       frame.setVisible(true);
        controlloTeamSuff( c);

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


    public void setTeamTable(Controller c) {     //Carattere differente in base alla fase( in periodo di votazione mostrerà anche i voti)

        if(!c.isEventoFinitoHackCorr() )
        {
            ModelloTeamsTab modello=new ModelloTeamsTab(c);
            teamTable.setModel(modello);
        }
        else
        {
            c.ordinaTeamVoti();
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c);
            teamTable.setModel(modello);

        }
    }

    public void setProblemaTextArea(Controller c) {
        if(c.isEventoFinitoHackCorr())
            problemaTextArea.setEditable(false);
        problemaTextArea.setText(c.getProblemaHackCorr());
    }

    public void setPubblicaProblemaButton(Controller c) {

            pubblicaProblemaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!c.isEventoPronto()) {
                        JOptionPane.showMessageDialog(frame," Non puoi ancora pubblicare il problema");
                        return;
                    }

                    if(!c.isTeamSuffHackCorr()){
                        JOptionPane.showMessageDialog(frame,"Purtroppo non si sono formati abbastanza Team\nQuest'hackathon non verrà eseguito");
                        return;
                    }


                    if(c.getProblemaHackCorr()==null  ||  c.getProblemaHackCorr().equals("")  ){
                        JOptionPane.showMessageDialog(frame,"Nessun problema salvato\nScrivi-->Salva Problema-->Pubblica Problema ");
                        return;
                    }

                    int risp=JOptionPane.showConfirmDialog(frame,"La traccia pubblicata sarà la seguente:\n"+problemaTextArea.getText()+"\n\nConfermare?\n",
                            "Conferma", JOptionPane.YES_NO_OPTION);
                    if(risp==0){

                        try{
                            c.pubblicaProblema();
                            JOptionPane.showMessageDialog(frame,"Problema pubblicato");
                            vediProgressiPanel.setVisible(true);
                        }catch (SQLException ex){
                            ex.printStackTrace();
                        }

                    }
                }
            });

    }


    public void setSalvaProblemaButton(Controller c) {
        salvaProblemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String problema=problemaTextArea.getText();
                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di salvare la seguente traccia:\n"+problema+"\n","Salvataggio",JOptionPane.YES_NO_OPTION);

                if(risp==0)

                    try{
                        c.salvaProblema(problema);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

            }
        });


    }

    public void setVediProgressiButton(Controller c) {

        if(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr()) {
            vediProgressiButton.setVisible(false);

        } else {
            vediProgressiButton.setVisible(true);
        }



        vediProgressiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTeam=idTeamField.getText();
                try{
                    c.identificaTeamHackCorr(idTeam);
                    new giudiceVediProgressiGUI(c,giudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }
        });

    }


    private void setAssegnaVoto(Controller c){

        if(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr()) {
            assegnaVotiButton.setVisible(true);

        } else {
            assegnaVotiButton.setVisible(false);

        }



        assegnaVotiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String idTeam=idTeamField.getText();
                try{
                    c.identificaTeamHackCorr(idTeam);
                    new giudiceVediProgressiGUI(c,giudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }

        });



    }

    public void setCaricaValutazioniButton(Controller c) {
        if(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr()) {

            caricaValutazioniButton.setVisible(true);
        } else {

            caricaValutazioniButton.setVisible(false);
        }

        caricaValutazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if(!c.areAllTeamValuated())
                {
                    JOptionPane.showMessageDialog(frame,"Non tutti i team sono stati valutati\n" +
                            "Caricamento annullato");
                    return;
                }
                int risp=JOptionPane.showConfirmDialog(frame,"Confermare le valutazioni\nNon potranno essere più modificate",
                        "Conderma",JOptionPane.YES_NO_OPTION);

                if(risp==0){

                    try {
                        c.concludiVotazioni();
                        caricaValutazioniButton.setVisible(false);
                        assegnaVotiButton.setVisible(false);
                        vediProgressiButton.setVisible(true);
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }

                }

            }
        });
    }

    private void controlloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.isTeamSuffHackCorr()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (teams insufficienti)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }
}
