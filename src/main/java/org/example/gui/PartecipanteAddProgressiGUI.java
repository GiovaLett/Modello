package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class PartecipanteAddProgressiGUI {

    private JDialog frame=new JDialog((Frame) null,"Aggiungi progresso",true);
    private JPanel mainPanel;
    private JTextArea progressoTextArea;
    private JTextField nomeProgressoField;
    private JButton caricaButton;



    /**
     * Costruttore della classe {@code PartecipanteAddProgressiGUI}.
     * <p>
     * Inizializza la finestra grafica che permette a un partecipante di
     * aggiungere progressi al proprio progetto.
     * </p>
     * <p>
     * La finestra viene configurata con il pannello principale, dimensioni fisse,
     * centrata sullo schermo e chiusura tramite {@link JFrame#DISPOSE_ON_CLOSE}.
     * Vengono impostati:
     * <ul>
     *   <li>il pulsante per caricare i progressi tramite {@link #setCaricaButton(Controller, PartecipanteGUI)};</li>
     *   <li>il comportamento di chiusura tramite {@link #closeOperation(Controller, PartecipanteGUI)}.</li>
     * </ul>
     * Infine, la finestra viene resa visibile.
     * </p>
     *
     * @param c       il {@link Controller} utilizzato per gestire l'aggiunta dei progressi
     * @param origGUI la GUI originale del partecipante, da cui questa finestra è aperta
     */
    public PartecipanteAddProgressiGUI(Controller c, PartecipanteGUI origGUI){


        frame.setContentPane(mainPanel);
        frame.setSize(500,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setCaricaButton(c,origGUI);
        closeOperation( c,origGUI);


        frame.setVisible(true);
    }


    /**
     * Imposta il comportamento di chiusura della finestra corrente.
     * <p>
     * Quando l'utente tenta di chiudere questa finestra:
     * <ul>
     *   <li>viene aggiornata la lista dei progressi nella GUI originale del partecipante
     *       tramite {@link PartecipanteGUI#setProgressiList(Controller)};</li>
     *   <li>la finestra corrente viene chiusa tramite {@link JFrame#dispose()}.</li>
     * </ul>
     *
     * @param c       il {@link Controller} utilizzato per aggiornare i progressi
     * @param origGUI la GUI originale del partecipante da aggiornare alla chiusura
     */
    private void closeOperation(Controller c, PartecipanteGUI origGUI){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origGUI.setProgressiList(c);
                frame.dispose();
            }
        });

    }


    /**
     * Configura il pulsante {@code caricaButton} per caricare un nuovo progresso
     * del team del partecipante.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>vengono letti i valori dei campi {@code nomeProgressoField} e
     *       {@code progressoTextArea};</li>
     *   <li>si verifica che il nome del progresso sia unico tramite
     *       {@link Controller#isNomeProgressoOriginale(String)}; in caso contrario,
     *       viene mostrato un messaggio di errore;</li>
     *   <li>viene mostrata una finestra di conferma per caricare il progresso;</li>
     *   <li>se confermato, il progresso viene caricato tramite
     *       {@link Controller#caricaProgressoTeamCorr(String, String)},
     *       viene mostrato un messaggio di conferma e aggiornata la lista dei progressi
     *       nella GUI originale ({@link PartecipanteGUI#setProgressiList(Controller)});</li>
     *   <li>la finestra corrente viene chiusa;</li>
     *   <li>eventuali {@link SQLException} vengono intercettate e stampate nello stack trace.</li>
     * </ol>
     *
     * @param c       il {@link Controller} utilizzato per verificare l'unicità del nome
     *                e caricare il progresso
     * @param origGUI la GUI originale del partecipante, da aggiornare dopo l'aggiunta
     *                del progresso
     */
    public void setCaricaButton(Controller c, PartecipanteGUI origGUI) {

        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String progresso=progressoTextArea.getText();
                String nomeProgresso=nomeProgressoField.getText();

                if( !c.isNomeProgressoOriginale(nomeProgresso)){
                    JOptionPane.showMessageDialog(frame,"Presente un altro progresso con lo stesso nome\n" +
                            "(Cambiare nome al progresso corrente)");
                    return;
                }

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di caricare questo progresso dal nome:\n"+nomeProgresso,"Conferma",JOptionPane.YES_NO_OPTION);
                if(risp==0)
                {
                    try{
                        c.caricaProgressoTeamCorr(nomeProgresso,progresso);

                        JOptionPane.showMessageDialog(frame,"Progresso aggiunto!");
                        origGUI.setProgressiList(c);

                        frame.dispose();
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }


                }
            }
        });

    }
}
