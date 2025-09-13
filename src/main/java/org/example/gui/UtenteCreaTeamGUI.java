package org.example.gui;

import org.example.controller.Controller;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UtenteCreaTeamGUI {

    private JDialog frame;
    private JPanel mainPanel;
    private JTextField nomeTeamField;
    private JButton creaButton;


    /**
     * Costruttore della classe {@code UtenteCreaTeamGUI}.
     * <p>
     * Inizializza una finestra di dialogo modale per consentire a un utente
     * di creare un nuovo team.
     * </p>
     * <p>
     * La finestra viene configurata con:
     * <ul>
     *   <li>il pannello principale {@code mainPanel};</li>
     *   <li>dimensioni adattate automaticamente tramite {@link JDialog#pack()};</li>
     *   <li>posizionamento centrato sullo schermo;</li>
     *   <li>configurazione del pulsante di creazione del team tramite {@link #setCreaButton(Controller, JFrame)}.</li>
     * </ul>
     * Infine, la finestra viene resa visibile.
     * </p>
     *
     * @param c         il {@link Controller} utilizzato per creare il nuovo team
     * @param origFrame il {@link JFrame} di origine da cui viene aperta questa finestra
     */
   public UtenteCreaTeamGUI(Controller c,JFrame origFrame){

       frame=new JDialog((JFrame)null,"Crea Team",true);
       frame.setContentPane(mainPanel);
       frame.pack();

       frame.setLocationRelativeTo(null);
       setCreaButton(c,origFrame);
       frame.setVisible(true);
   }



    /**
     * Configura il pulsante {@code creaButton} per creare un nuovo team.
     * <p>
     * Al click del pulsante:
     * <ol>
     *   <li>viene letto il nome del team dal campo {@code nomeTeamField};</li>
     *   <li>viene mostrata una finestra di conferma per verificare l'intenzione dell'utente;</li>
     *   <li>se confermato, il team viene creato tramite {@link Controller#creaTeamHC(String)};</li>
     *   <li>la finestra corrente e la finestra di origine ({@code origFrame}) vengono chiuse;</li>
     *   <li>viene mostrato un messaggio che invita a riaccedere per vedere i nuovi aggiornamenti;</li>
     *   <li>viene aperta la schermata {@link Home} per reindirizzare l'utente;</li>
     *   <li>eventuali {@link IllegalArgumentException} vengono intercettate e mostrate all'utente;
     *       eventuali {@link SQLException} vengono stampate nello stack trace.</li>
     * </ol>
     *
     * @param c         il {@link Controller} utilizzato per creare il nuovo team
     * @param origFrame il {@link JFrame} di origine che verrà chiuso dopo la creazione del team
     */
   private void setCreaButton(Controller c,JFrame origFrame){
       creaButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String nomeTeam=nomeTeamField.getText();
               int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di creare il team con nome:\n"+nomeTeam,
                       "Conferma",JOptionPane.YES_NO_OPTION);

               if(risposta==JOptionPane.YES_OPTION)
               {
                   try{
                    c.creaTeamHC(nomeTeam);

                   frame.dispose();
                   origFrame.dispose();
                   JOptionPane.showMessageDialog(frame,"Sarai indirizzato alla Home, riaccedi per vedere i nuovi aggiornamenti al profilo!");
                   new Home();}
                   catch (IllegalArgumentException exce){
                       JOptionPane.showMessageDialog(frame,exce.getMessage());
                   }
                   catch (SQLException ex){
                       ex.printStackTrace();
                   }
               }

           }
       });
   }
}
