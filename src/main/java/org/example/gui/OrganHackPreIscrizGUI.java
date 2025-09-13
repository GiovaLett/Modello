package org.example.gui;

import org.example.controller.Controller;


//L'import di questi 2 package serve per poter stampare nome e cognome degli utenti nei messaggi di conferma

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class OrganHackPreIscrizGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeHackLabel;
    private JTable giudiciTable;
    private JTable utentiTable;
    private JTextField idUtenteField;
    private JButton selezGiudButton;
    private JLabel idLabel;
    private JTextField idGiudiceField;
    private JPanel rimuovGiudPanel;
    private JButton rimuoviButton;
    private JButton inviaRichiestaPerGiudiceButton;


    /**
     * Costruttore della classe {@code OrganHackPreIscrizGUI}.
     * <p>
     * Inizializza la finestra grafica per la selezione e la gestione dei giudici
     * di un hackathon prima dell'iscrizione.
     * </p>
     * <p>
     * La finestra mostra il nome e l'ID dell'hackathon corrente, configura le
     * tabelle degli utenti e dei giudici, e imposta i pulsanti per:
     * <ul>
     *   <li>selezionare giudici;</li>
     *   <li>rimuovere giudici;</li>
     *   <li>inviare richieste per nuovi giudici.</li>
     * </ul>
     * Viene gestita la visibilità dei pannelli in base allo stato della lista
     * dei giudici, e la finestra di origine ({@code origFrame}) viene gestita
     * tramite {@link #closeOperation(JFrame)}.
     * </p>
     *
     * @param c         il {@link Controller} usato per recuperare i dati
     *                  dell'hackathon e gestire le operazioni sui giudici
     * @param origFrame il {@link JFrame} di origine, da chiudere o nascondere
     *                  quando questa GUI viene aperta
     */
    public OrganHackPreIscrizGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Selezione Giudici");
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getNomeHackCorr());
        idLabel.setText(c.getIdHackCorr());
        frame.pack();
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setUtentiTable(c);
        setSelezGiudButton(c);
        setRimuoviButton( c);
        setGiudiciTable(c);
        closeOperation(origFrame);


        rimuovGiudPanel.setVisible(!(c.isListaGiudHackCorrEmpty()));


        setInviaRichiestaPerGiudiceButton( c);
        frame.setVisible(true);




    }


    /**
     * Imposta il comportamento di chiusura della finestra corrente.
     * <p>
     * Quando l'utente tenta di chiudere questa finestra, viene resa nuovamente
     * visibile la finestra di origine ({@code origFrame}) e la finestra corrente
     * viene eliminata tramite {@link JFrame#dispose()}.
     * </p>
     *
     * @param origFrame il {@link JFrame} di origine da rendere nuovamente visibile
     *                  al momento della chiusura della finestra corrente
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
     * Configura la tabella degli utenti ({@code utentiTable}) assegnandole
     * un modello dati personalizzato.
     * <p>
     * Il modello utilizzato è un'istanza di {@link ModelloUtentiTabella},
     * inizializzata con il {@link Controller}, da cui vengono recuperate
     * le informazioni sugli utenti disponibili per la selezione come giudici.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per fornire i dati degli utenti
     */
    private void setUtentiTable(Controller c){
        ModelloUtentiTabella modello=new ModelloUtentiTabella(c);
        utentiTable.setModel(modello);

    }


    /**
     * Configura il pulsante {@code selezGiudButton} per la selezione di un giudice
     * dall'elenco degli utenti disponibili.
     * <p>
     * Il pulsante viene inizialmente nascosto. Al click:
     * <ul>
     *   <li>viene recuperato l'ID dell'utente dal campo {@code idUtenteField};</li>
     *   <li>viene aggiunto come giudice all'hackathon corrente tramite
     *       {@link Controller#addGiudiceHackCorr(String)};</li>
     *   <li>le tabelle degli utenti e dei giudici vengono aggiornate;</li>
     *   <li>il pannello {@code rimuovGiudPanel} viene reso visibile.</li>
     * </ul>
     * In caso di eccezione {@link IllegalArgumentException}, viene mostrato
     * un messaggio di errore all'utente tramite {@link JOptionPane}.
     *
     * @param c il {@link Controller} utilizzato per gestire l'aggiunta dei giudici
     *          e l'aggiornamento dei dati delle tabelle
     */
    private void setSelezGiudButton(Controller c){

        selezGiudButton.setVisible(false);


        selezGiudButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idUtente = idUtenteField.getText();

                try {
                    c.addGiudiceHackCorr(idUtente);
                    setGiudiciTable(c);
                    setUtentiTable(c);
                    rimuovGiudPanel.setVisible(true);

                }
                catch(IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(),"Errore", 0);
                }

            }
        });
    }

    /**
     * Configura il pulsante {@code rimuoviButton} per rimuovere un giudice
     * selezionato dall'hackathon corrente.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene recuperato l'ID del giudice dal campo {@code idGiudiceField};</li>
     *   <li>si tenta di identificare il giudice selezionato tramite
     *       {@link Controller#identificaGiudiceSelezionatoHackCorr(String)};</li>
     *   <li>se il giudice è valido, viene mostrata una finestra di conferma
     *       tramite {@link JOptionPane#showConfirmDialog};</li>
     *   <li>in caso di conferma, il giudice viene rimosso tramite
     *       {@link Controller#removeGiudiceSelHackCorr()}, viene mostrato un
     *       messaggio di conferma e le tabelle degli utenti e dei giudici vengono aggiornate;</li>
     *   <li>il pannello {@code rimuovGiudPanel} viene reso visibile solo se
     *       la lista dei giudici non è vuota.</li>
     * </ol>
     * Se l'ID del giudice non è valido, viene mostrato un messaggio di errore
     * tramite {@link JOptionPane}. Eventuali {@link SQLException} vengono
     * intercettate e stampate nello stack trace.
     *
     * @param c il {@link Controller} utilizzato per gestire l'identificazione,
     *          la rimozione dei giudici e l'aggiornamento delle tabelle
     */
    public void setRimuoviButton(Controller c) {


        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idGiudice = idGiudiceField.getText();
                int risp=-1;
                try {

                    c.identificaGiudiceSelezionatoHackCorr(idGiudice);
                     risp = JOptionPane.showConfirmDialog(frame, "Sicuro di voler eliminate il giudice:\n"
                            + c.getNomeGiudSel() + " " + c.getCognomeGiudSel() + " ?", "Conferma eliminazione", 0);
                }catch (IllegalArgumentException exc){
                    JOptionPane.showMessageDialog(frame,exc.getMessage());
                }
                    if(risp==0) {

                        try{
                        c.removeGiudiceSelHackCorr();
                        JOptionPane.showMessageDialog(frame, "Giudice eliminato!");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        setGiudiciTable(c);
                        setUtentiTable(c);

                        rimuovGiudPanel.setVisible(!(c.isListaGiudHackCorrEmpty()));

                    }
                }

        });

    }


    /**
     * Configura la tabella dei giudici ({@code giudiciTable})
     * assegnandole un modello dati personalizzato.
     * <p>
     * Il modello utilizzato è un'istanza di {@link ModelloGiudiciTab},
     * inizializzata con il {@link Controller}, da cui vengono recuperate
     * le informazioni sui giudici già selezionati per l'hackathon corrente.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per fornire i dati dei giudici
     */
    private void setGiudiciTable(Controller c){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c );
        giudiciTable.setModel(modello);

    }


    /**
     * Configura il pulsante {@code inviaRichiestaPerGiudiceButton} per inviare
     * una richiesta a un utente selezionato affinché diventi giudice dell'hackathon.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene recuperato l'ID dell'utente dal campo {@code idUtenteField};</li>
     *   <li>si tenta di identificare l'utente selezionato tramite
     *       {@link Controller#identificaUtenteSelezionatoHackCorr(String)};</li>
     *   <li>se l'utente è valido, viene mostrata una finestra di conferma tramite
     *       {@link JOptionPane#showConfirmDialog};</li>
     *   <li>in caso di conferma, viene inviata la richiesta tramite
     *       {@link Controller#mandaRichiestaUtenteSelForGiudiceHackCorr()};</li>
     * </ol>
     * Se l'ID dell'utente non è valido, viene mostrato un messaggio di errore
     * tramite {@link JOptionPane}. Eventuali {@link SQLException} vengono
     * intercettate e stampate nello stack trace.
     *
     * @param c il {@link Controller} utilizzato per identificare l'utente
     *          selezionato e inviare la richiesta
     */
    public void setInviaRichiestaPerGiudiceButton(Controller c) {


        inviaRichiestaPerGiudiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String utenteID=idUtenteField.getText();
                int risp=-1;

                try {

                    c.identificaUtenteSelezionatoHackCorr(utenteID);

                    risp = JOptionPane.showConfirmDialog(frame, "Sicuro di mandare la richiesta a\n" +
                            c.getNomeUtenSele() + " " + c.getCognomeUtenSele() + " ?", "", JOptionPane.YES_NO_OPTION);
                }catch (IllegalArgumentException exc){
                    JOptionPane.showMessageDialog(frame,exc.getMessage());
                }
                    if(risp==0)
                    {
                        try {
                            c.mandaRichiestaUtenteSelForGiudiceHackCorr();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

            }
        });
    }
}
