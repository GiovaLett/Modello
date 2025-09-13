package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class RegistrazioneGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField confPasswordField;
    private JButton registratiButton;

    /**
     * Costruttore della classe {@code RegistrazioneGUI}.
     * <p>
     * Inizializza la finestra grafica per la registrazione di un nuovo utente.
     * </p>
     * <p>
     * La finestra viene configurata con:
     * <ul>
     *   <li>il pannello principale {@code mainPanel};</li>
     *   <li>posizionamento centrato sullo schermo;</li>
     *   <li>adattamento automatico delle dimensioni tramite {@link JFrame#pack()};</li>
     *   <li>gestione della chiusura della finestra tramite {@link #closeOperation(JFrame)};</li>
     *   <li>configurazione del pulsante di registrazione tramite {@link #setRegistratiButton(Controller, JFrame)}.</li>
     * </ul>
     * Infine, la finestra viene resa visibile.
     * </p>
     *
     * @param c         il {@link Controller} utilizzato per gestire la registrazione dell'utente
     * @param origFrame il {@link JFrame} di origine, da cui questa finestra è aperta,
     *                  che verrà ripristinato alla chiusura
     */
    public RegistrazioneGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Registrazione");
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.pack();

        closeOperation(origFrame);
        setRegistratiButton(c,origFrame);
        frame.setVisible(true);
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
     * Configura il pulsante {@code registratiButton} per registrare un nuovo utente.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>vengono letti i valori dei campi: nome, cognome, email, password e conferma password;</li>
     *   <li>si verifica che le due password coincidano; in caso contrario viene mostrato un messaggio di errore;</li>
     *   <li>si verifica se l'email è già registrata tramite {@link Controller#isEmailRegisteredYet(String)};
     *       se sì, viene mostrato un messaggio di avviso;</li>
     *   <li>viene mostrata una finestra di conferma con i dati inseriti;</li>
     *   <li>se confermato, l'utente viene registrato tramite {@link Controller#addUtenteRegistrato(String, String, String, String)},
     *       viene mostrato un messaggio di conferma, la finestra corrente viene chiusa
     *       e la finestra di origine ({@code origFrame}) viene resa visibile;</li>
     *   <li>eventuali {@link SQLException} vengono intercettate e segnalate con un messaggio di errore.</li>
     * </ol>
     *
     * @param c         il {@link Controller} utilizzato per verificare l'email e registrare l'utente
     * @param origFrame il {@link JFrame} di origine da rendere nuovamente visibile
     *                  dopo la chiusura della finestra di registrazione
     */
    public void setRegistratiButton(Controller c,JFrame origFrame) {
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome= nomeField.getText();
                String cognome=cognomeField.getText();
                String email= emailField.getText();

                String password= passwordField.getText();
                String confePassword= confPasswordField.getText();

                if( !password.equals(confePassword)){
                    JOptionPane.showMessageDialog(frame,"Le due password non coincidono!","Errore password",0);
                    return;
                }
                if(c.isEmailRegisteredYet(email)){
                    JOptionPane.showMessageDialog(frame,"Email già registrata\n(Registarti con un'altra email)");
                }
                else{

                    int risp=JOptionPane.showConfirmDialog(frame,"Confermi le credenziali?:\n " +
                            "Nome: "+nome+"\nCognome: "+cognome+"\nEmail: "+email+"\n","Conferma",JOptionPane.YES_NO_OPTION);
                    if(risp==0)
                    {
                        try{
                        c.addUtenteRegistrato(nome,cognome,email,password);
                        JOptionPane.showMessageDialog(frame,"Account registrato!");
                        frame.dispose();
                        origFrame.setVisible(true);
                        } catch (SQLException _ ){
                            JOptionPane.showMessageDialog(frame,"Errore con il sistema, siamo spiacenti");
                        }
                    }
                }
            }
        });
    }
}
