package org.example.gui;

import org.example.controller.Controller;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class OrganHackGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable teamsTable;
    private JTable giudiciTable;
    private JLabel nomeHackLabel;
    private JLabel idLabel;
    private JButton terminaQuestoHackathonButton;
    private JLabel teamsOrClassificaLabels;
    private JButton pubblicaClassificaButton;
    private JLabel noHackLabel;

    /**
     * Costruttore della classe {@code OrganHackGUI}.
     * <p>
     * Inizializza la finestra grafica dedicata alla gestione di un hackathon
     * selezionato. La finestra mostra il nome e l'ID dell'hackathon corrente,
     * configurando i pannelli principali e le relative tabelle di dati
     * (team e giudici).
     * </p>
     * <p>
     * Inoltre, vengono impostati i pulsanti per terminare l'hackathon e per
     * pubblicare la classifica. La finestra originale da cui proviene
     * l'utente viene gestita tramite il metodo {@code closeOperation}.
     * </p>
     *
     * @param c         il {@link Controller} usato per recuperare i dati
     *                  dell'hackathon e gestire le relative operazioni
     * @param origFrame il {@link JFrame} di origine, da chiudere o nascondere
     *                  quando questa GUI viene aperta
     */
    public OrganHackGUI(Controller c, JFrame origFrame){


        frame=new JFrame(c.getNomeHackCorr());
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(c.getNomeHackCorr());
        idLabel.setText(c.getIdHackCorr());
        frame.setSize(700,500);
        frame.setLocationRelativeTo(null);

        setTeamsTable(c);
        setGiudiciTable(c);
        setTerminaQuestoHackathonButton(c);
        setPubblicaClassificaButton(c);

        closeOperation(origFrame);
        frame.setVisible(true);
        cotrolloTeamSuff(c);

    }

    /**
     * Imposta il comportamento di chiusura della finestra corrente.
     * <p>
     * Quando l'utente tenta di chiudere la finestra di organizzazione
     * dell'hackathon, viene nuovamente resa visibile la finestra
     * di origine ({@code origFrame}) e la finestra corrente viene
     * eliminata tramite {@code dispose()}.
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
     * Configura la tabella dei giudici ({@code giudiciTable})
     * assegnandole un modello dati personalizzato.
     * <p>
     * Il modello utilizzato è un'istanza di {@link ModelloGiudiciTab},
     * popolata tramite il {@link Controller}, che fornisce i dati
     * relativi ai giudici dell'hackathon corrente.
     * </p>
     *
     * @param c il {@link Controller} usato per recuperare i dati dei giudici
     */
    private void setGiudiciTable(Controller c){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c);
        giudiciTable.setModel(modello);

    }

    private void setTeamsTable(Controller c){


        if(c.isVotazioneConclusaHackCorr())
        {
            teamsOrClassificaLabels.setText("Classifica:");
            c.ordinaTeamVoti();
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c);
            teamsTable.setModel(modello);
        }
        else{
            teamsOrClassificaLabels.setText("Teams:");
            ModelloTeamsTab modello=new ModelloTeamsTab(c);
            teamsTable.setModel(modello);
        }



    }

    /**
     * Configura la tabella dei team ({@code teamsTable}) in base allo stato
     * dell'hackathon corrente.
     * <p>
     * Se la votazione dell'hackathon è conclusa:
     * <ul>
     *   <li>l'etichetta viene impostata a {@code "Classifica:"};</li>
     *   <li>i team vengono ordinati in base ai voti tramite
     *       {@link Controller#ordinaTeamVoti()};</li>
     *   <li>alla tabella viene assegnato un modello di tipo
     *       {@link ModelloTeamsVotiTab}.</li>
     * </ul>
     * Altrimenti:
     * <ul>
     *   <li>l'etichetta viene impostata a {@code "Teams:"};</li>
     *   <li>alla tabella viene assegnato un modello di tipo
     *       {@link ModelloTeamsTab}.</li>
     * </ul>
     *
     * @param c il {@link Controller} utilizzato per determinare lo stato
     *          dell'hackathon e fornire i dati dei team
     */
    public void setTerminaQuestoHackathonButton(Controller c) {


        terminaQuestoHackathonButton.setVisible(!c.isEventoFinitoHackCorr() && c.isViewProblemaHackCorr());



        terminaQuestoHackathonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler forzare la chiusura dell'Hackathon prima della durata prestabilita?",
                        "Termine Hackathon",JOptionPane.YES_NO_OPTION);

                if(risp==0){

                    try{
                        c.setEventoFinito(true);
                        terminaQuestoHackathonButton.setVisible(false);
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }

                }


            }
        });

    }


    /**
     * Configura il pulsante {@code pubblicaClassificaButton}
     * che consente di pubblicare la classifica di un hackathon.
     * <p>
     * Il pulsante viene reso visibile solo se:
     * <ul>
     *   <li>la votazione per l'hackathon corrente è conclusa, e</li>
     *   <li>la classifica non è ancora stata pubblicata.</li>
     * </ul>
     * Al click del pulsante:
     * <ul>
     *   <li>viene invocato {@link Controller#pubblicaClassifica()} per
     *       pubblicare la classifica;</li>
     *   <li>il pulsante viene nascosto;</li>
     *   <li>eventuali eccezioni {@link SQLException} vengono intercettate
     *       e stampate nello stack trace.</li>
     * </ul>
     *
     * @param c il {@link Controller} utilizzato per verificare lo stato
     *          dell'hackathon e pubblicare la classifica
     */
    public void setPubblicaClassificaButton(Controller c) {


        pubblicaClassificaButton.setVisible(c.isVotazioneConclusaHackCorr() && !c.isClassificaPubblHackCorr());



        pubblicaClassificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    c.pubblicaClassifica();
                    pubblicaClassificaButton.setVisible(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }


    /**
     * Verifica se l'hackathon corrente dispone di un numero sufficiente di team
     * per potersi svolgere.
     * <p>
     * Se l'evento è contrassegnato come pronto ({@link Controller#isEventoPronto()})
     * ma i team non sono sufficienti ({@link Controller#isTeamSuffHackCorr()} == false):
     * <ul>
     *   <li>mostra un messaggio di avviso all'utente tramite {@link JOptionPane};</li>
     *   <li>rende visibile l'etichetta {@code noHackLabel}.</li>
     * </ul>
     * In caso contrario, l'etichetta viene nascosta.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per verificare lo stato
     *          dell'evento e il numero di team registrati
     */
    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.isTeamSuffHackCorr()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (teams insufficienti)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }


}
