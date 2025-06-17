package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.ruoli.Team;

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
    private JPanel vediProgressiPanel;
    private JButton assegnaVotiButton;
    private JPanel problemaButtonPanel;
    private JButton caricaValutazioniButton;
    private JLabel noHackLabel;


    public giudiceGUI(Controller c, JFrame orifFrame,Hackathon hackathon){

       frame=new JFrame("Giudice");
       frame.setContentPane(mainPanel);

       frame.pack();
        frame.setLocationRelativeTo(null);
       closeOperation(orifFrame);

       nomeCognomeGiudice.setText(c.getUtenteCorrente().getNome()+" "+c.getUtenteCorrente().getCognome());
       idGiudice.setText(c.getUtenteCorrente().getID());
       nomeHackathoneLabel.setText(hackathon.getNome());


      setProblemaTextArea(hackathon);
       setSalvaProblemaButton(c,hackathon);
       setTeamTable(c,hackathon);
       setPubblicaProblemaButton(c,hackathon);

        if(!hackathon.isView_problema())
            vediProgressiPanel.setVisible(false);

        if(hackathon.isEventoFinito())
            problemaButtonPanel.setVisible(false);


        setAssegnaVoto( c, hackathon);
       setVediProgressiButton(c,hackathon);
        setCaricaValutazioniButton(c,hackathon);

       frame.setVisible(true);
        cotrolloTeamSuff( c,hackathon);

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


    public void setTeamTable(Controller c,Hackathon hackathon) {     //Carattere differente in base alla fase( in periodo di votazione mostrerà anche i voti)

        if(!c.isEventoFinito(hackathon))
        {
            ModelloTeamsTab modello=new ModelloTeamsTab(hackathon.getListaTeam());
            teamTable.setModel(modello);
        }
        else
        {
            c.ordinaTeamVoti(hackathon);
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(hackathon.getListaTeam());
            teamTable.setModel(modello);

        }
    }

    public void setProblemaTextArea(Hackathon hackathon) {
        if(hackathon.isEventoFinito())
            problemaTextArea.setEditable(false);
        problemaTextArea.setText(hackathon.getProblema());
    }

    public void setPubblicaProblemaButton(Controller c, Hackathon hackathon) {

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

                    int risp=JOptionPane.showConfirmDialog(frame,"La traccia pubblicata sarà la seguente:\n"+problemaTextArea.getText()+"\n\nConfermare?\n",
                            "Conferma", JOptionPane.YES_NO_OPTION);
                    if(risp==0){
                        hackathon.setView_problema(true);
                        JOptionPane.showMessageDialog(frame,"Problema pubblicato");
                        vediProgressiPanel.setVisible(true);
                    }





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

    public void setVediProgressiButton(Controller c,Hackathon hackathon) {

        if(c.isEventoFinito(hackathon) && !hackathon.isVotazioneConclusa()) {
            vediProgressiButton.setVisible(false);

        } else {
            vediProgressiButton.setVisible(true);
        }



        vediProgressiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTeam=idTeamField.getText();
                try{
                    Team team=c.findIDTeam(idTeam,hackathon);
                    new giudiceVediProgressiGUI(c,hackathon,team,giudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }
        });

    }


    private void setAssegnaVoto(Controller c, Hackathon hackathon){

        if(c.isEventoFinito(hackathon) && !hackathon.isVotazioneConclusa()) {
            assegnaVotiButton.setVisible(true);

        } else {
            assegnaVotiButton.setVisible(false);

        }



        assegnaVotiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String idTeam=idTeamField.getText();
                try{
                    Team team=c.findIDTeam(idTeam,hackathon);
                    new giudiceVediProgressiGUI(c,hackathon,team,giudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }

        });



    }

    public void setCaricaValutazioniButton(Controller c, Hackathon hackathon) {
        if(c.isEventoFinito(hackathon) && !hackathon.isVotazioneConclusa()) {

            caricaValutazioniButton.setVisible(true);
        } else {

            caricaValutazioniButton.setVisible(false);
        }

        caricaValutazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(Team team: hackathon.getListaTeam())
                {
                    if(team.getVoto()==-1)
                    {
                        JOptionPane.showMessageDialog(frame,"Non tutti i team sono stati valutati\n" +
                                "Caricamento annullato");
                        return;
                    }

                }
                int risp=JOptionPane.showConfirmDialog(frame,"Confermare le valutazioni\nNon potranno essere più modificate",
                        "Conderma",JOptionPane.YES_NO_OPTION);

                if(risp==0){
                    hackathon.setVotazioneConclusa(true);
                    caricaValutazioniButton.setVisible(false);
                    assegnaVotiButton.setVisible(false);
                    vediProgressiButton.setVisible(true);

                }


            }
        });
    }

    private void cotrolloTeamSuff(Controller c,Hackathon hackathon)
    {
        if(c.isEventoPronto() && !hackathon.isTeam_suffic()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (solo 1 team iscritto)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }
}
