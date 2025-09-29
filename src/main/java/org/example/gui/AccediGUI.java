package org.example.gui;

import org.example.controller.Controller;


import javax.swing.*;
import java.awt.event.*;


public class AccediGUI
{
    private JFrame frame=new JFrame("Accesso");
    private JTextField emailField;
    private JTextField passwordField;
    private JButton accediButton;
    private JPanel mainPanel;

    /**
     * Costruttore della classe che imposta le caratteristiche del frame:
     * avrà una posizione centrale;
     * non sarà ridimensionabile;
     * e sarà inserito un comportamento specifico per il pulsante accediButton
     * cosi come per l'applicazione di chiusura
     * @param origFrame È il frame da cui ha avuto origine questo
     * @param c il Controller
     */
    AccediGUI(JFrame origFrame, Controller c){
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();


        setAccediButton(c);
        closeOperation( origFrame);
        frame.setVisible(true);
    }

    /**
     * Funzione che permette alla chiusura di questo frame rendere visibile il frame precedente
     * @param origFrame frame precedente, da cui è stato istanziato questo
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
     * Attribuisce un comportamento specifico ad "accediButton", nello specifico:
     * acquisisce le stringhe inserite all'interno di "emailField" e "passwordField",
     * successivamente all'interno di un try viene dichiarata una variabile (n) alla quale viene assegnato il risultato
     * ottenuto dalla funzione {@link Controller#identificaUtente(String, String)} che presenta come parametri rispettivamente
     * le stringhe inserite all'interno di "emailField" e "passwordField".
     * <p>
     * In base al valore acquisito da "n" verranno istanziate differenti GUI:
     * <ol start="0">
     *     <li>OrganizzatoreGUI</li>
     *     <li>GiudiceGUI</li>
     *     <li>PartecipanteGUI</li>
     *     <li>UtenteRegistratoIscrCloseGUI</li>
     *     <li>UtenteRegistrIscrOpenGUI</li>
     *   </ol>

     * E come operazione di default un messaggio in cui non viene riconosciuto l'utente.
     *<p>
     * All'interno del catch viene catturata un IllegalArgumentException, e in tale caso viene stampato un messaggio:
     * "Errore credenziali"
     * @param c il Controller
     */
    private void setAccediButton(Controller c){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email=emailField.getText();
                String password=passwordField.getText();

                try {

                    int n=c.identificaUtente(email,password);

                    switch (n){
                        case 0:
                            new OrganizzatoreGUI(c,frame);
                            break;
                        case 1:
                            new GiudiceGUI( c, frame);
                            break;
                        case 2:
                            new PartecipanteGUI(c,frame);
                            break;
                        case 3:
                            new UtenteRegistratoIscrCloseGUI(c,frame);
                            break;
                        case 4:
                            new UtenteRegistrIscrOpenGUI(c,frame);
                            break;
                        default:
                            JOptionPane.showMessageDialog(frame,"Utente non riconosciuto");

                    }
                }
                catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Errore credenziali",JOptionPane.ERROR_MESSAGE);
                    return;

                }

                frame.setVisible(false);
            }
        });
    }
}
