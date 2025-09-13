package org.example.gui;

import org.example.controller.Controller;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class UtenteToPartecipanteGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton creaTeamButton;
    private JTable teamTable;
    private JTextField codiceAccessoField;
    private JButton accediButton;


    /**
     * Costruttore della classe {@code UtenteToPartecipanteGUI}.
     * <p>
     * Inizializza la finestra grafica per un utente registrato che può accedere
     * a un hackathon come partecipante.
     * </p>
     * <p>
     * La finestra viene configurata con:
     * <ul>
     *   <li>il pannello principale {@code mainPanel};</li>
     *   <li>dimensioni fisse di 500x275 pixel;</li>
     *   <li>posizionamento centrato sullo schermo;</li>
     *   <li>gestione della chiusura della finestra tramite {@link #closeOperation(JFrame)};</li>
     *   <li>tabella dei team tramite {@link #setTeamTable(Controller)};</li>
     *   <li>pulsante per accedere a un team esistente tramite {@link #setAccediButton(Controller)};</li>
     *   <li>pulsante per creare un nuovo team tramite {@link #setCreaTeamButton(Controller)}.</li>
     * </ul>
     * </p>
     *
     * @param c         il {@link Controller} utilizzato per recuperare le informazioni sui team
     *                  e gestire l'accesso o la creazione dei team
     * @param origFrame il {@link JFrame} di origine, da cui questa finestra è aperta,
     *                  che verrà ripristinato alla chiusura
     */
    public UtenteToPartecipanteGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        closeOperation(origFrame);


        setTeamTable(c);
        setAccediButton(c);
        setCreaTeamButton(c);

        frame.setVisible(true);
        frame.setContentPane(mainPanel);
    }

    /**
     * Gestisce l'operazione di chiusura della finestra corrente.
     * <p>
     * Quando l'utente chiude questa finestra:
     * <ul>
     *   <li>la finestra di origine ({@code origFrame}) viene resa nuovamente visibile;</li>
     *   <li>la finestra corrente viene chiusa tramite {@link JFrame#dispose()}.</li>
     * </ul>
     * </p>
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
     * Configura la tabella {@code teamTable} con i team disponibili per l'hackathon corrente.
     * <p>
     * Viene creato un modello dati {@link ModelloTeamsTab} inizializzato con il
     * {@link Controller}, che fornisce le informazioni sui team.
     * Il modello viene poi assegnato alla tabella {@code teamTable}.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per recuperare i dati dei team
     */
    private void setTeamTable(Controller c) {
        ModelloTeamsTab modello=new ModelloTeamsTab(c);
        teamTable.setModel(modello);
    }


    /**
     * Configura il pulsante {@code accediButton} per permettere all'utente
     * di accedere a un team esistente tramite codice di accesso.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene verificata la scadenza delle iscrizioni tramite {@link Controller#isIscrizioneScaduta()};
     *       se la data è scaduta, viene mostrato un messaggio di avviso;</li>
     *   <li>viene letto il codice del team dal campo {@code codiceAccessoField};</li>
     *   <li>il {@link Controller} verifica l'esistenza del team tramite {@link Controller#identificaCODTeamHC(String)};
     *       se il codice non è valido, viene mostrato un messaggio di errore;</li>
     *   <li>il {@link Controller} aggiunge l'utente al team corrente tramite {@link Controller#addPartecToTeamCorrHC()};
     *       eventuali eccezioni vengono intercettate e mostrate;</li>
     *   <li>viene mostrato un messaggio di conferma dell'appartenenza al team;</li>
     *   <li>viene mostrato un messaggio informativo sul reindirizzamento alla Home;</li>
     *   <li>la finestra corrente viene chiusa e viene aperta la schermata {@link Home}.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per gestire l'accesso al team e
     *          aggiornare le informazioni dell'utente
     */
    private void setAccediButton(Controller c) {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(c.isIscrizioneScaduta()){
                    JOptionPane.showMessageDialog(frame,"Data iscrizione Scaduta");
                    return;
                }

                String codiceTeam= codiceAccessoField.getText();


                try {
                    c.identificaCODTeamHC(codiceTeam);
                }
                catch(IllegalArgumentException exception)
                {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try { c.addPartecToTeamCorrHC(); }
                catch (IllegalArgumentException exception)
                {
                    JOptionPane.showMessageDialog(frame,exception.getMessage()," ",JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return;
                }


                JOptionPane.showMessageDialog(frame,"Ora sei membro del team:\n"+c.getNomeTeamCorr());
                JOptionPane.showMessageDialog(frame,"Sarai indirizzato alla Home, riaccedi per vedere i nuovi aggiornamenti al profilo!");
                frame.dispose();
                new Home();




            }
        });
    }


    /**
     * Configura il pulsante {@code creaTeamButton} per permettere all'utente
     * di creare un nuovo team.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene verificata la scadenza delle iscrizioni tramite {@link Controller#isIscrizioneScaduta()};
     *       se la data è scaduta, viene mostrato un messaggio di avviso e l'operazione viene interrotta;</li>
     *   <li>se le iscrizioni sono ancora valide, viene aperta la finestra
     *       {@link UtenteCreaTeamGUI} per permettere la creazione del nuovo team.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per gestire la creazione del team
     *          e verificare la scadenza delle iscrizioni
     */
    private void setCreaTeamButton(Controller c) {
        creaTeamButton.addActionListener(new ActionListener() {



            @Override
            public void actionPerformed(ActionEvent e) {

                if(c.isIscrizioneScaduta()){
                    JOptionPane.showMessageDialog(frame,"Data iscrizione Scaduta");
                    return;
                }

                new UtenteCreaTeamGUI( c, frame);

            }
        });
    }
}
