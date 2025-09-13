package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Home
{
    private static int nApriHome=0;
    private static Controller controller=new Controller();
    private static JFrame frame = new JFrame("Home");
    private JPanel mainPanel;
    private JButton accediButton;
    private JButton registratiButton;

    /**
     * Costruttore della classe {@code Home}.
     * <p>
     * Inizializza il frame principale dell'applicazione impostando il contenuto,
     * le proprietà di base e la visibilità. Alla prima apertura della home
     * (quando {@code nApriHome == 0}) recupera dal database tutte le informazioni
     * necessarie tramite il controller, tra cui:
     * <ul>
     *   <li>Costanti e identificativi</li>
     *   <li>Lista degli hackathon</li>
     *   <li>Team registrati</li>
     *   <li>Utenti registrati</li>
     *   <li>Flags della piattaforma</li>
     *   <li>Dati dell'evento</li>
     * </ul>
     * In caso di errore di connessione al database viene mostrato un messaggio
     * d'errore all'utente e stampato lo stack trace.
     * </p>
     *
     * Inoltre, vengono inizializzati i pulsanti di accesso e registrazione.
     */
    public Home() {



        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();

        if(nApriHome==0){


            try{
                controller.getCostantiIdDB();
                controller.getAllHackathonDB();//Importante l'ordine!!!
                controller.getAllTeamDB();
                controller.getUtentiRegistratiDB();
                controller.getFlagsPiattaformaDB();
                controller.getDataEventoDB();


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame,"Errore con il sistema, siamo spiacenti\n(Riavvia il programma)");
                e.printStackTrace();
            }

            nApriHome++;
        }


        setAccediButton(controller);
        setRegistratiButton(controller );
        frame.setVisible(true);

    }

    /**
     * Configura il pulsante {@code accediButton} assegnandogli un
     * {@link java.awt.event.ActionListener}.
     * <p>
     * Al click del pulsante viene aperta la finestra di login
     * ({@link AccediGUI}) e la home corrente viene nascosta.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per gestire le operazioni
     *           di autenticazione e passare i dati necessari alla GUI di accesso
     */
    private void setAccediButton(Controller c){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccediGUI(frame,c);
                frame.setVisible(false);
            }
        });

    }

    /**
     * Configura il pulsante {@code registratiButton} assegnandogli un
     * {@link java.awt.event.ActionListener}.
     * <p>
     * Al click del pulsante viene aperta la finestra di registrazione
     * ({@link RegistrazioneGUI}) e la home corrente viene nascosta.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per gestire le operazioni
     *           di registrazione e passare i dati necessari alla GUI di registrazione
     */
    private void setRegistratiButton(Controller c){
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrazioneGUI( c,frame);
                frame.setVisible(false);
            }
        });

    }




}
