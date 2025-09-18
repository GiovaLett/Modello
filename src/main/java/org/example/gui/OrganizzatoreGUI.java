package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrganizzatoreGUI {

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
    private JSpinner giornoSpinner;
    private JSpinner meseSpinner;
    private JSpinner annoSpinner;
    private JButton impostaDataButton;
    private JPanel dataEventoPanel;
    private JTextField sedeField;
    private JTextField durataField;


    /**
     * Costruttore della classe {@code OrganizzatoreGUI}.
     * <p>
     * Inizializza la finestra grafica dedicata all'organizzatore, mostrando
     * il nome dell'amministratore corrente e configurando la tabella degli
     * hackathon gestiti.
     * </p>
     * <p>
     * Vengono inoltre impostati i pulsanti e i pannelli principali per:
     * <ul>
     *   <li>rimuovere hackathon;</li>
     *   <li>aggiungere hackathon;</li>
     *   <li>aprire le iscrizioni;</li>
     *   <li>chiudere le iscrizioni;</li>
     *   <li>entrare in un hackathon selezionato;</li>
     *   <li>visualizzare/modificare i dati dell'evento.</li>
     * </ul>
     * La finestra di origine ({@code origFrame}) viene gestita tramite
     * {@link #closeOperation(JFrame)}.
     * </p>
     *
     * @param c         il {@link Controller} usato per recuperare i dati
     *                  dell'organizzatore e degli hackathon
     * @param origFrame il {@link JFrame} di origine, da chiudere o nascondere
     *                  quando questa GUI viene aperta
     */
    public OrganizzatoreGUI(Controller c, JFrame origFrame){

        frame=new JFrame();
        nomeAmministratore.setText(c.getNomeUtenCorr()+" "+c.getCognomeUtenCorr());
        frame.setContentPane(mainPanel);
        setHackathonTable(c);
        frame.pack();
        frame.setLocationRelativeTo(null);

        setRimuoviButton(c);
        setAggiungiButton(c);
        setApriIsrizButton(c);
        setChiudiIscrizioniButton(c);
        setEntraButton(c);
        setDataEventoPanel(c);
        closeOperation( origFrame);
        frame.setVisible(true);


    }




    /**
     * Configura la tabella degli hackathon ({@code hackathonTable})
     * assegnandole un modello dati personalizzato.
     * <p>
     * Il modello utilizzato è un'istanza di {@link ModelloHackTab},
     * inizializzata con il {@link Controller}, da cui vengono recuperate
     * le informazioni sugli hackathon gestiti dall'organizzatore corrente.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per fornire i dati degli hackathon
     */
   private void setHackathonTable(Controller c){
        ModelloHackTab modello=new ModelloHackTab(c);
        hackathonTable.setModel(modello);
   }


    /**
     * Configura il pannello relativo alla data dell'evento ({@code dataEventoPanel}).
     * <p>
     * Il pannello viene reso visibile solo se l'evento non è pronto ({@link Controller#isEventoPronto()})
     * e le iscrizioni non sono aperte ({@link Controller#isOpenIscri()}).
     * Vengono inoltre inizializzati gli spinner per:
     * <ul>
     *   <li>anno ({@link #setAnnoSpinner()});</li>
     *   <li>mese ({@link #setMeseSpinner()});</li>
     *   <li>giorno ({@link #setGiornoSpinner(int)}), con giorno iniziale 1;</li>
     * </ul>
     * Infine, viene impostato il pulsante per confermare la data dell'evento
     * tramite {@link #setImpostaDataButton(Controller)}.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per verificare lo stato dell'evento
     *          e gestire l'impostazione della data
     */
   private void setDataEventoPanel(Controller c){

        dataEventoPanel.setVisible(!c.isEventoPronto() && !c.isOpenIscri());
        setAnnoSpinner();
        setMeseSpinner();
        setGiornoSpinner(1);
       setImpostaDataButton(c);
    }


    /**
     * Configura lo spinner del giorno ({@code giornoSpinner}) in base al mese
     * selezionato e al giorno salvato.
     * <p>
     * Il valore minimo dello spinner è sempre 1. Il valore massimo dipende dal mese:
     * <ul>
     *   <li>31 giorni per gennaio, marzo, maggio, luglio, agosto, ottobre, dicembre;</li>
     *   <li>30 giorni per aprile, giugno, settembre, novembre;</li>
     *   <li>28 giorni per febbraio (non viene considerato l'anno bisestile).</li>
     * </ul>
     * Se il giorno salvato è maggiore del massimo consentito per il mese,
     * viene impostato al massimo consentito.
     * </p>
     *
     * @param giornoSalvato il giorno da impostare inizialmente nello spinner
     */
   private void setGiornoSpinner(int giornoSalvato){


        int mese=(int)meseSpinner.getValue();
       SpinnerNumberModel modello=null;

        if(mese==1 || mese==3 || mese==5 || mese==7 || mese==8 || mese==10 || mese==12){
             modello=new SpinnerNumberModel(giornoSalvato,1,31,1);

        }
       else if(mese==4 || mese==6 || mese==9 || mese==11 ){


           if(giornoSalvato>30)
              modello=new SpinnerNumberModel(30,1,30,1);
           else
               modello=new SpinnerNumberModel(giornoSalvato,1,30,1);


       }
       else if(mese==2){
            if(giornoSalvato>28)
                modello=new SpinnerNumberModel(28,1,30,1);
            else
                modello=new SpinnerNumberModel(giornoSalvato,1,30,1);

        }
       giornoSpinner.setModel(modello);


   }

    /**
     * Configura lo spinner del mese ({@code meseSpinner}) per la selezione
     * dei mesi dell'anno.
     * <p>
     * Il valore iniziale dello spinner è 6 (giugno), con valori possibili da 1 a 12.
     * Viene aggiunto un listener che, ad ogni cambiamento del mese, aggiorna
     * lo spinner del giorno ({@link #setGiornoSpinner(int)}) per adattarsi
     * al numero corretto di giorni del mese selezionato.
     * </p>
     */
   private void setMeseSpinner(){

        SpinnerNumberModel modello=new SpinnerNumberModel(6,1,12,1);
        meseSpinner.setModel(modello);

        meseSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int giornoSalvato=(int)giornoSpinner.getValue();
                setGiornoSpinner(giornoSalvato);


            }
        });
   }

    /**
     * Configura lo spinner dell'anno ({@code annoSpinner}) per la selezione
     * della data dell'evento.
     * <p>
     * Il valore iniziale dello spinner è l'anno corrente ottenuto tramite
     * {@link java.time.LocalDate#now()}. Il valore minimo è anch'esso l'anno
     * corrente, mentre non è impostato un valore massimo (può essere selezionato
     * qualsiasi anno futuro). L'incremento avviene di 1 in 1.
     * </p>
     */
   private void setAnnoSpinner(){

       LocalDate dataOggi=LocalDate.now();
       int annoOdierno=dataOggi.getYear();
       SpinnerNumberModel modello=new SpinnerNumberModel(annoOdierno,annoOdierno,null,1);
       annoSpinner.setModel(modello);
   }


    /**
     * Configura il pulsante {@code impostaDataButton} per impostare la data
     * dell'evento dell'hackathon.
     * <p>
     * Al click del pulsante:
     * <ul>
     *   <li>vengono letti i valori selezionati dagli spinner di giorno, mese e anno;</li>
     *   <li>viene invocato {@link Controller#setDataEvento(int, int, int)} per
     *       impostare la data dell'evento;</li>
     *   <li>se l'impostazione ha successo, viene mostrato un messaggio di conferma
     *       tramite {@link JOptionPane#showMessageDialog};</li>
     *   <li>in caso di eccezione {@link IllegalArgumentException}, viene mostrato
     *       un messaggio di errore all'utente.</li>
     * </ul>
     *
     * @param c il {@link Controller} utilizzato per impostare la data dell'evento
     */
   private void setImpostaDataButton(Controller c){

        impostaDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 int giorno= (int)giornoSpinner.getValue();
                int mese= (int)meseSpinner.getValue();
                int  anno= (int)annoSpinner.getValue();

                try{
                    c.setDataEvento(giorno,mese,anno);
                    JOptionPane.showMessageDialog(frame,"Data impostata");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,ex.getMessage());
                }

            }
        });

   }

    /**
     * Configura il pulsante {@code entraButton} per entrare in un hackathon
     * selezionato dall'organizzatore.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene letto l'ID dell'hackathon dal campo {@code idHackField};</li>
     *   <li>si tenta di identificare l'hackathon selezionato tramite
     *       {@link Controller#identificaHackathon(String)};</li>
     *   <li>in caso di errore (ID non valido), viene mostrato un messaggio
     *       di errore tramite {@link JOptionPane} e l'operazione viene interrotta;</li>
     *   <li>se l'hackathon non ha iscrizioni aperte e l'evento non è pronto,
     *       viene aperta la finestra {@link OrganHackPreIscrizGUI};</li>
     *   <li>altrimenti, viene aperta la finestra {@link OrganHackGUI};</li>
     *   <li>la finestra corrente viene nascosta.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per identificare l'hackathon
     *          e aprire la GUI appropriata
     */
   private void setEntraButton(Controller c){
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();

                try {
                    c.identificaHackathon(idHack);
                    if(!c.isEventoFinitoHackCorr()){ c.isDurataHCTerminata();}
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(!c.isOpenIscri() && !c.isEventoPronto())  {
                    new OrganHackPreIscrizGUI( c, frame);
                }
                else    {
                    new OrganHackGUI(c,frame);
                }

                    frame.setVisible(false);

            }

            });
   }


    /**
     * Configura il pulsante {@code rimuoviButton} per rimuovere un hackathon
     * selezionato dall'organizzatore.
     * <p>
     * Il pulsante viene reso visibile solo se le iscrizioni non sono aperte
     * ({@link Controller#isOpenIscri()}) e l'evento non è pronto
     * ({@link Controller#isEventoPronto()}). Al click del pulsante:
     * <ol>
     *   <li>viene letto l'ID dell'hackathon dal campo {@code idHackField};</li>
     *   <li>si tenta di identificare l'hackathon selezionato tramite
     *       {@link Controller#identificaHackathon(String)};</li>
     *   <li>in caso di ID non valido, viene mostrato un messaggio di errore
     *       tramite {@link JOptionPane} e l'operazione viene interrotta;</li>
     *   <li>viene mostrata una finestra di conferma per l'eliminazione dell'hackathon;</li>
     *   <li>se confermato, l'hackathon viene rimosso tramite {@link Controller#removeHackCorr()},
     *       e viene mostrato un messaggio di conferma;</li>
     *   <li>eventuali eccezioni {@link SQLException} vengono intercettate e
     *       segnalate all'utente tramite {@link JOptionPane}.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per identificare e rimuovere
     *          l'hackathon selezionato
     */
    public void setRimuoviButton(Controller c) {


        rimuoviButton.setVisible(!c.isOpenIscri() && !c.isEventoPronto());


        this.rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();


                try {
                    c.identificaHackathon(idHack);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler eliminare l'hackathon: "+
                        c.getNomeHackCorr()+" ?","Conferma",0);
                if(risp==0)
                {
                    try{
                    c.removeHackCorr();
                    JOptionPane.showMessageDialog(frame,"Hackathon eliminato!");}
                    catch (SQLException _ ) {
                        JOptionPane.showMessageDialog(frame,"Errore DB","",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Configura il pulsante {@code aggiungiButton} per aggiungere un nuovo hackathon.
     * <p>
     * Il pannello di creazione ({@code creaHackathonPanel}) viene reso visibile solo
     * se le iscrizioni non sono aperte ({@link Controller#isOpenIscri()}) e l'evento
     * corrente non è pronto ({@link Controller#isEventoPronto()}).
     * </p>
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>vengono letti i valori dei campi: nome, sede e durata dell'hackathon;</li>
     *   <li>se uno dei campi è vuoto, viene mostrato un messaggio di avviso;</li>
     *   <li>il campo durata viene convertito in intero, con gestione di {@link NumberFormatException};</li>
     *   <li>viene mostrata una finestra di conferma con i dati inseriti;</li>
     *   <li>se confermato, l'hackathon viene aggiunto tramite {@link Controller#addHackathon(String, String, int)},
     *       con gestione di eventuali {@link SQLException};</li>
     *   <li>viene mostrato un messaggio di conferma e aggiornata la tabella degli hackathon.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per aggiungere il nuovo hackathon
     *          e aggiornare la tabella degli hackathon
     */
    public void setAggiungiButton(Controller c) {


        creaHackathonPanel.setVisible(!c.isOpenIscri() && !c.isEventoPronto());


        this.aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeHackathon= nomeHackathonField.getText();
                String sedeHackathon=sedeField.getText();
                String durataHackathonStr=durataField.getText();
                int durataHackathon;

                if(nomeHackathon.equals("") || sedeHackathon.equals("") || durataHackathonStr.equals(""))
                    JOptionPane.showMessageDialog(frame,"Nessun campo è facoltativo");
                else {

                    try{
                        durataHackathon=Integer.parseInt(durataHackathonStr);
                    } catch (NumberFormatException _ ) {
                        JOptionPane.showMessageDialog(frame,"Il formato del campo durata deve essere un intero\n(Es. 1,2,34...");
                        return;
                    }


                    int risp=JOptionPane.showConfirmDialog(frame,"Confermare:\n" +
                            "- Nome:"+nomeHackathon+"\n- Sede:"+sedeHackathon+"\n- Durata:"+durataHackathon+"g","Conferma",0);
                    if(risp==JOptionPane.YES_OPTION){

                        try{
                            c.addHackathon(nomeHackathon,sedeHackathon,durataHackathon);      }
                        catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frame,"Errore con DB","",JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(frame,"Hackathon aggiunto");
                        setHackathonTable(c);
                    }
                }
            }
        });
    }

    /**
     * Imposta il comportamento di chiusura della finestra corrente.
     * <p>
     * Quando l'utente tenta di chiudere questa finestra:
     * <ul>
     *   <li>la finestra di origine ({@code origFrame}) viene resa nuovamente visibile;</li>
     *   <li>la finestra corrente viene chiusa tramite {@link JFrame#dispose()}.</li>
     * </ul>
     *
     * @param origFrame il {@link JFrame} di origine da rendere visibile al momento
     *                  della chiusura della finestra corrente
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
     * Configura il pulsante {@code apriIsrizButton} per aprire le iscrizioni
     * agli hackathon.
     * <p>
     * Il pulsante viene reso visibile solo se le iscrizioni non sono già aperte
     * ({@link Controller#isOpenIscri()}) e l'evento corrente non è pronto
     * ({@link Controller#isEventoPronto()}).
     * </p>
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene controllato se la data dell'evento è stata impostata; in caso contrario,
     *       viene mostrato un messaggio di avviso;</li>
     *   <li>viene mostrata una finestra di conferma con le informazioni sulle conseguenze
     *       dell'apertura delle iscrizioni, inclusa la data dell'evento;</li>
     *   <li>se confermato, le iscrizioni vengono aperte tramite
     *       {@link Controller#apriIscrizioni()}, il pulsante e alcuni pannelli vengono nascosti,
     *       e la tabella degli hackathon viene aggiornata;</li>
     *   <li>eventuali {@link SQLException} vengono intercettate e segnalate tramite {@link JOptionPane}.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per aprire le iscrizioni e aggiornare
     *          la tabella degli hackathon
     */
    public void setApriIsrizButton(Controller c) {

        apriIsrizButton.setVisible(!c.isOpenIscri() && !c.isEventoPronto());
        apriIsrizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c.getDataEvento()==null){
                    JOptionPane.showMessageDialog(frame,"Imposta prima una data di inizio dell'evento");
                    return;
                }
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di aprire le iscrizioni\n" +
                                "- modifiche ai giudici e agli hackathon non più disponibili\n" +
                                "- Hackathon senza giudici verranno eliminati\n "+
                                "- Data dell'evento impostata: "+c.getDataEvento(),
                        "Conferma?",
                        JOptionPane.YES_NO_OPTION);
                if(risposta==JOptionPane.YES_OPTION) {

                    try{
                    c.apriIscrizioni();
                    apriIsrizButton.setVisible(false);
                    creaHackathonPanel.setVisible(false);
                    rimuoviButton.setVisible(false);
                    dataEventoPanel.setVisible(false);
                    setHackathonTable(c);}
                    catch (SQLException exc){
                        JOptionPane.showMessageDialog(frame,"Errore DB","",JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
                    }

                }

            }
        });
    }


    /**
     * Configura il pulsante {@code chiudiIscrizioniButton} per chiudere le iscrizioni
     * agli hackathon.
     * <p>
     * Il pulsante viene reso visibile solo se le iscrizioni sono attualmente aperte
     * ({@link Controller#isOpenIscri()}).
     * </p>
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene mostrata una finestra di conferma per forzare la chiusura delle
     *       iscrizioni prima dei 2 giorni dall'evento;</li>
     *   <li>se confermato, le iscrizioni vengono chiuse tramite
     *       {@link Controller#chiudiIscrizioni()} e il pulsante viene nascosto;</li>
     *   <li>eventuali {@link SQLException} vengono intercettate e stampate nello stack trace.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per chiudere le iscrizioni
     */
    public void setChiudiIscrizioniButton(Controller c) {

        chiudiIscrizioniButton.setVisible(c.isOpenIscri());

        chiudiIscrizioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di Forzare la chiusura delle iscrizioni prima dei 2 giorni dall'evento?", "Conferma?", JOptionPane.YES_NO_OPTION);

                if(risposta==JOptionPane.YES_OPTION) {

                    try{
                    c.chiudiIscrizioni();
                    chiudiIscrizioniButton.setVisible(false);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });
    }


}
