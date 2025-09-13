package org.example.gui;

import org.example.controller.Controller;



import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class UtenteRegistratoIscrCloseGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable hackathonTable;


    /**
     * Costruttore della classe {@code UtenteRegistratoIscrCloseGUI}.
     * <p>
     * Inizializza la finestra grafica per un utente registrato quando le iscrizioni
     * sono chiuse o l'hackathon non è ancora disponibile.
     * </p>
     * <p>
     * La finestra viene configurata con:
     * <ul>
     *   <li>il pannello principale {@code mainPanel};</li>
     *   <li>dimensioni fisse di 500x275 pixel;</li>
     *   <li>posizionamento centrato sullo schermo;</li>
     *   <li>tabella degli hackathon tramite {@link #setHackathonTable(Controller)};</li>
     *   <li>gestione della chiusura della finestra tramite {@link #closeOperation(JFrame)};</li>
     *   <li>messaggi informativi tramite {@link #messageToBeJudge(Controller)}.</li>
     * </ul>
     * </p>
     *
     * @param c         il {@link Controller} utilizzato per recuperare le informazioni sugli hackathon
     * @param origFrame il {@link JFrame} di origine, da cui questa finestra è aperta,
     *                  che verrà ripristinato alla chiusura
     */
    public UtenteRegistratoIscrCloseGUI(Controller c,JFrame origFrame)
    {
        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        setHackathonTable(c);


        closeOperation(origFrame);
        frame.setVisible(true);
        messageToBeJudge(c);



    }

    /**
     * Imposta il comportamento di chiusura della finestra corrente.
     * <p>
     * Quando l'utente tenta di chiudere questa finestra:
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
     * Configura la tabella {@code hackathonTable} con gli hackathon disponibili.
     * <p>
     * Viene creato un modello dati {@link ModelloHackTab} inizializzato con il
     * {@link Controller}, che fornisce le informazioni sugli hackathon.
     * Il modello viene poi assegnato alla tabella {@code hackathonTable}.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per recuperare i dati degli hackathon
     */
    public void setHackathonTable(Controller c) {
        ModelloHackTab modello=new ModelloHackTab(c);
        hackathonTable.setModel(modello);
    }

    /**
     * Gestisce le richieste per diventare giudice per l'utente corrente.
     * <p>
     * Recupera la richiesta dal {@link Controller} tramite {@link Controller#getRichiestaUC()}.
     * Se la richiesta non è vuota:
     * <ul>
     *   <li>viene mostrata una finestra di conferma all'utente ("Vuoi essere giudice?");</li>
     *   <li>se l'utente conferma (YES), viene invocato {@link Controller#diventaGiudice()}
     *       e mostrato un messaggio di conferma;</li>
     *   <li>se l'utente rifiuta (NO), viene invocato {@link Controller#rifiutaInvitoGiudice()}.</li>
     * </ul>
     * <p>
     * Eventuali {@link SQLException} vengono intercettate e stampate nello stack trace.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per gestire la richiesta di diventare giudice
     */
    private void messageToBeJudge(Controller c){
        String richiesta=c.getRichiestaUC();
        if(!richiesta.equals(""))
        {
            int risp=JOptionPane.showConfirmDialog(frame,richiesta,"Vuoi essere giudice?",JOptionPane.YES_NO_OPTION);
            if(risp==0)
            {
                try {
                    c.diventaGiudice();
                    JOptionPane.showMessageDialog(frame, "Sei diventato giudice\n Riaccedi per vedere i cambiamenti");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            else
                try {
                    c.rifiutaInvitoGiudice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
    }
}
