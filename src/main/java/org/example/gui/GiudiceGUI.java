package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class GiudiceGUI {

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

    /**
     * Costruttore della classe, Imposta le caratteristiche del frame come il titolo "Giudice", posizione centrale
     * e modalità di chiusura tramite un altro metodo interno {@link #closeOperation(JFrame)}.<p>
     * Vi sono poi altri metodi interni che fanno riferimento all'interazione con la GUI o ad una visione dinamica dei widget,
     * i quali variano in base agli eventi accaduti.
     *
     * @param c Controller
     * @param orifFrame frame precedente a questo
     */
    public GiudiceGUI(Controller c, JFrame orifFrame){

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


    /**
     * Stabilisce il comportamento da attuare alla chiusura del frame, nello specifico si rende visibile il frame precedente
     * e viengono liberate risorse dalla memoria inerenti al frame attuale
     * @param origFrame riferimento al frame precedente
     */
    private void closeOperation(JFrame origFrame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origFrame.setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * Imposta le caratteristiche della tabella, nello specifico
     * se l'evento non è terminato mostra solamente la tabella con informazioni sui team (senza votazione),
     * altrimenti se l'evento è terminato si utilizzerà un altro modello per le tabelle che prevede l'inserimento
     * di un'altra colonna contenente la votazione del relativo team.
     * @param c Controller
     */
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

    /**
     * Imposta le caratteristiche della textArea contenente il problema da dover pubblicare.
     * Se l'evento è finito la textArea non sarà editabile, altrimenti saranno concesse modifiche al problema
     * Ogni volta che compare il frame la textArea sara caratterizzata dall' ultima versione della traccia salvata.
     * @param c Controller
     */
    public void setProblemaTextArea(Controller c) {
        if(c.isEventoFinitoHackCorr())
            problemaTextArea.setEditable(false);
        problemaTextArea.setText(c.getProblemaHackCorr());
    }

    /**
     * Caratterizza la funzionalità del bottone per pubblicare il problema, il cui comportamento varia in base a
     * specifiche condizioni:
     * <ul>
     *    <li>Se l'evento non è pronto compare il messaggio:Non puoi ancora pubblicare il problema;</li>
     *     <li>Se il numero di Team non è pari o uguale a 2 compare il messaggio: "Purtroppo non si sono formati abbastanza Team. Quest' hackathon non verrà eseguito";</li>
     *     <li>Se invece non è stata scritta o salvata alcuna traccia: "Nessun problema salvato. Scrivi-->Salva Problema-->Pubblica Problema ";</li>
     *   </ul>
     *Altrimenti comparirà un messaggio di conferma per la pubblicazione della traccia
     * @param c Controller
     */
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

    /**
     * Si occupa di salvare la traccia una volta scritta nella textArea, con un relativo messaggio di conferma;
     * se la risposta è positiva allora tale traccia verrà salvata anche nel DB con {@link Controller#salvaProblema(String)}
     * @param c Controller
     */
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

    /**
     * Vengono settate le caratteristiche del pulsante "vediProgressiButton", nello specifico
     * se l' evento è finito e la votazione non si è conclusa il pulsante non sarà visibile, altrimenti si.
     * Una volta premuto il pulsante, estrapola una stringa dall' "idTeamField" che contiene l'id del team di cui
     * si vogliono vedere i progressi e successivamente questa viene inserita come parametro a {@link Controller#identificaTeamHackCorr(String)}
     * cossicchè il teamCorrente del controller sia quello correlato a tale id e successivamente sarà aperta
     * {@link GiudiceVediProgressiGUI} che mostrerà i progressi relativi a quel team. Nel caso in cui, invece,
     * non viene trovato alcun team con quell'id comparirà un messaggio con scritto: "Not Found";
     *
     * @param c Controller
     */
    public void setVediProgressiButton(Controller c) {


        vediProgressiButton.setVisible(  !(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr()));


        vediProgressiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTeam=idTeamField.getText();
                try{
                    c.identificaTeamHackCorr(idTeam);
                    new GiudiceVediProgressiGUI(c, GiudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }
        });

    }

    /**
     * Vengono settate le caratteristiche del pulsante "vediProgressiButton", nello specifico
     * sarà visibile se l' evento è finito e la votazione non si è conclusa ancora;
     * inoltre quando viene premuto estrapola una stringa dall' "idTeamField" che contiene l'id del team a cui
     *  si vuole assegnare un voto e successivamente questa viene inserita come parametro a {@link Controller#identificaTeamHackCorr(String)}
     *  cossicchè il teamCorrente del controller sia quello correlato a tale id e successivamente sarà aperta la GUI {@link GiudiceVediProgressiGUI}
     *  che permette l assegnazione di un voto vedendo i progressi e i relativi commenti.
     *  Nel caso in cui, invece, non viene trovato alcun team con quell'id comparirà un messaggio con scritto: "Not Found";
     *
     * @param c Controller
     */
    private void setAssegnaVoto(Controller c){

        assegnaVotiButton.setVisible(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr());


        assegnaVotiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String idTeam=idTeamField.getText();
                try{
                    c.identificaTeamHackCorr(idTeam);
                    new GiudiceVediProgressiGUI(c, GiudiceGUI.this);

                } catch (IllegalArgumentException exce) {

                    JOptionPane.showMessageDialog(frame,exce.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);

                }
            }

        });



    }

    /**
     * Imposta le caratteristiche di "caricaValutazioniButton" nello specifico:
     * <ul>
     *     <li>Sarà visibile soltanto se l evento è finito e la votazione non si è conclusa.</li>
     *     <li>Quando viene premuto il pulsante verifica che tutti i team siano stati valutati e in caso contrario
     *      * mostra il messaggio: "Non tutti i team sono stati valutati. Caricamento annullato".</li>
     *     <li>Invece se tutti i team sono stati valutati, compare un messaggio di conferma, che in caso positivo:
     *       <ol>
     *        <li>Imposta la flag "votazioneConclusa" dell' hackathon a TRUE e la memorizza nel DB con il metodo
     *           {@link Controller#concludiVotazioni()}.</li>
     *        <li>Viene nascosto il pulsante "caricaValutazioniButton" come anche "assegnaVotiButton"</li>
     *        <li>Viene reso visibile il pulsante "vediProgressiButton"</li>
     *      </ol>
     *     </li>
     * </ul>
     *
     *
     *
     *
     *
     * @param c
     */
    public void setCaricaValutazioniButton(Controller c) {


        caricaValutazioniButton.setVisible(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr());

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

    /**
     * Metodo per valutare se il numero di Teams è sufficiente per poter svolgere l' hackathon (>=2).
     * Nel caso in cui i teams siano insufficienti comparirà un messaggio: "L'hackathon non si svolgerà (teams insufficienti)"
     *
     * @param c Controller
     */
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
