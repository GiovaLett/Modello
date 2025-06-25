package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.ruoli.Team;

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

       nomeCognomeGiudice.setText(c.getUtenteCorrente().getNome()+" "+c.getUtenteCorrente().getCognome());
       idGiudice.setText(c.getUtenteCorrente().getID());
       nomeHackathoneLabel.setText(c.getHackathonCorrente().getNome());


      setProblemaTextArea(c);
       setSalvaProblemaButton(c);
       setTeamTable(c);
       setPubblicaProblemaButton(c);

        if(!c.getHackathonCorrente().isView_problema())
            vediProgressiPanel.setVisible(false);

        if(c.getHackathonCorrente().isEventoFinito())
            problemaButtonPanel.setVisible(false);


        setAssegnaVoto( c);
       setVediProgressiButton(c);
        setCaricaValutazioniButton(c);

       frame.setVisible(true);
        cotrolloTeamSuff( c);

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

        if(!c.isEventoFinito(c.getHackathonCorrente() ) )
        {
            ModelloTeamsTab modello=new ModelloTeamsTab(c.getHackathonCorrente().getListaTeam());
            teamTable.setModel(modello);
        }
        else
        {
            c.ordinaTeamVoti(c.getHackathonCorrente());
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c.getHackathonCorrente().getListaTeam());
            teamTable.setModel(modello);

        }
    }

    public void setProblemaTextArea(Controller c) {
        if(c.getHackathonCorrente().isEventoFinito())
            problemaTextArea.setEditable(false);
        problemaTextArea.setText(c.getHackathonCorrente().getProblema());
    }

    public void setPubblicaProblemaButton(Controller c) {

            pubblicaProblemaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!c.isEventoPronto()) {
                        JOptionPane.showMessageDialog(frame," Non puoi ancora pubblicare il problema");
                        return;
                    }

                    if(!c.getHackathonCorrente().isTeam_suffic()){
                        JOptionPane.showMessageDialog(frame,"Purtroppo non si sono formati abbastanza Team\nQuest'hackathon non verrà eseguito");
                        return;
                    }


                    if(c.getHackathonCorrente().getProblema()==null  ||  c.getHackathonCorrente().getProblema().equals("")  ){
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

        if(c.isEventoFinito(c.getHackathonCorrente()) && !c.getHackathonCorrente().isVotazioneConclusa()) {
            vediProgressiButton.setVisible(false);

        } else {
            vediProgressiButton.setVisible(true);
        }



        vediProgressiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTeam=idTeamField.getText();
                try{
                    c.setTeamCorrente(c.findIDTeam(idTeam,c.getHackathonCorrente()) );
                    new giudiceVediProgressiGUI(c,giudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }
        });

    }


    private void setAssegnaVoto(Controller c){

        if(c.isEventoFinito(c.getHackathonCorrente()) && !c.getHackathonCorrente().isVotazioneConclusa()) {
            assegnaVotiButton.setVisible(true);

        } else {
            assegnaVotiButton.setVisible(false);

        }



        assegnaVotiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String idTeam=idTeamField.getText();
                try{
                    c.setTeamCorrente(  c.findIDTeam(idTeam,c.getHackathonCorrente())  );
                    new giudiceVediProgressiGUI(c,giudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }

        });



    }

    public void setCaricaValutazioniButton(Controller c) {
        if(c.isEventoFinito(c.getHackathonCorrente()) && !c.getHackathonCorrente().isVotazioneConclusa()) {

            caricaValutazioniButton.setVisible(true);
        } else {

            caricaValutazioniButton.setVisible(false);
        }

        caricaValutazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(Team team: c.getHackathonCorrente().getListaTeam())
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

    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.getHackathonCorrente().isTeam_suffic()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (teams insufficienti)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }
}
