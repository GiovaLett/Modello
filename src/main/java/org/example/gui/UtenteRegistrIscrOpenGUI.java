package org.example.gui;

import org.example.controller.Controller;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UtenteRegistrIscrOpenGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable hackathonTable;
    private JTextField idHackField;
    private JButton entraButton;
    private JLabel nomeCognomLabel;
    private JLabel idLabel;
    private JLabel scadenzaLabel;


    /**
     * Costruttore della classe {@code UtenteRegistrIscrOpenGUI}.
     * <p>
     * Inizializza la finestra grafica per un utente registrato quando le iscrizioni
     * sono aperte.
     * </p>
     * <p>
     * La finestra viene configurata con:
     * <ul>
     *   <li>il pannello principale {@code mainPanel};</li>
     *   <li>dimensioni fisse di 500x275 pixel;</li>
     *   <li>posizionamento centrato sullo schermo;</li>
     *   <li>visualizzazione delle informazioni dell'utente (nome, cognome, ID);</li>
     *   <li>gestione della chiusura della finestra tramite {@link #closeOperation(JFrame)};</li>
     *   <li>tabella degli hackathon tramite {@link #setHackathonTable(Controller)};</li>
     *   <li>configurazione del pulsante per entrare in un hackathon tramite {@link #setEntraButton(Controller)};</li>
     *   <li>impostazione della scadenza tramite {@link #setScadenzaLabel(Controller)};</li>
     *   <li>messaggio informativo se non ci sono hackathon disponibili.</li>
     * </ul>
     * </p>
     *
     * @param c         il {@link Controller} utilizzato per recuperare le informazioni sugli hackathon
     *                  e sull'utente
     * @param origFrame il {@link JFrame} di origine, da cui questa finestra è aperta,
     *                  che verrà ripristinato alla chiusura
     */
    public UtenteRegistrIscrOpenGUI(Controller c,JFrame origFrame){

        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        nomeCognomLabel.setText(c.getNomeUtenCorr()+" "+c.getCognomeUtenCorr());
        idLabel.setText(c.getIdUtenCorr());

        closeOperation(origFrame);
        setHackathonTable(c);
        setEntraButton(c);
        setScadenzaLabel(c);
        frame.setVisible(true);
        if(c.isListaHackathonEmpty())
            JOptionPane.showMessageDialog(frame,"Gli hackathon purtroppo non si svolgeranno\n" );

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
     * Gestisce l'operazione di chiusura della finestra corrente.
     * <p>
     * Quando l'utente chiude questa finestra, il metodo rende nuovamente
     * visibile la finestra originale passata come parametro e libera le risorse
     * della finestra corrente con {@code dispose()}.
     * </p>
     *
     * @param origFrame il {@link JFrame} da rendere nuovamente visibile
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
     * Imposta il testo della label {@code scadenzaLabel} con la data di scadenza
     * delle iscrizioni all'hackathon corrente.
     * <p>
     * Recupera la data di scadenza dal {@link Controller} tramite
     * {@link Controller#getDataScadenzaIscriz()} e la visualizza nella label.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per ottenere la data di scadenza
     *          delle iscrizioni
     */
    public void setScadenzaLabel(Controller c) {
        this.scadenzaLabel.setText(c.getDataScadenzaIscriz());
    }


    /**
     * Configura il pulsante {@code entraButton} per permettere all'utente
     * di accedere a un hackathon selezionato.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene letto l'ID dell'hackathon dal campo {@code idHackField};</li>
     *   <li>il {@link Controller} verifica l'esistenza dell'hackathon tramite
     *       {@link Controller#identificaHackathon(String)};</li>
     *   <li>se l'ID è valido, viene aperta la finestra {@link UtenteToPartecipanteGUI}
     *       e la finestra corrente viene nascosta;</li>
     *   <li>se l'ID non è valido, viene mostrato un messaggio di errore.</li>
     * </ol>
     *
     * @param c il {@link Controller} utilizzato per identificare l'hackathon
     *          e gestire il passaggio alla schermata successiva
     */
    private void setEntraButton(Controller c){
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();


                try {
                    c.identificaHackathon(idHack);
                    new UtenteToPartecipanteGUI( c,frame);
                    frame.setVisible(false);

                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);

                }


            }

        });
    }
}
