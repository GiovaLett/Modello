package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PartecipanteGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable membriTable;
    private JLabel nomeTeamLabel;
    private JLabel idTeamLabel;
    private JTextArea problemaTextArea;
    private JButton caricaProgressiButton;
    private JLabel codiceLabel;
    private JLabel nomeCognomeLabel;
    private JLabel nomeHackathon;
    private JTable altriTeamsTable;
    private JList progressiList;
    private JTextArea progressiTextArea;



    private JLabel noHackLabel;

    private JPanel posizionamentoPanel;
    private JLabel posizLabel;



    /**
     * Costruttore della classe {@code PartecipanteGUI}.
     * <p>
     * Inizializza la finestra grafica principale per un partecipante di un hackathon.
     * </p>
     * <p>
     * La finestra mostra informazioni sul team e sull'hackathon corrente, tra cui:
     * <ul>
     *   <li>nome e ID del team;</li>
     *   <li>codice di accesso del team;</li>
     *   <li>nome e cognome del partecipante;</li>
     *   <li>nome dell'hackathon corrente.</li>
     * </ul>
     * </p>
     * <p>
     * Vengono inoltre configurate:
     * <ul>
     *   <li>le tabelle dei membri del team e degli altri team ({@link #setMembriTable(Controller)},
     *       {@link #setAltriTeamsTable(Controller)});</li>
     *   <li>l'area di testo per il problema assegnato ({@link #setProblemaTextArea(Controller)});</li>
     *   <li>la lista dei progressi e il pannello di selezione dei progressi ({@link #setProgressiList(Controller)},
     *       {@link #setSelectProgressList(Controller)});</li>
     *   <li>il pulsante per caricare nuovi progressi ({@link #setCaricaProgressiButton(Controller)});</li>
     *   <li>il pannello di posizionamento dei team ({@link #setPosizionamentoPanel(Controller)});</li>
     * </ul>
     * </p>
     * La visibilità dei componenti e la possibilità di caricare progressi vengono
     * aggiornate in base allo stato del team e dell'hackathon tramite
     * {@link #cotrolloTeamSuff(Controller)}. La finestra di origine ({@code origFrame})
     * viene gestita tramite {@link #closeOperation(JFrame)}.
     * </p>
     *
     * @param c         il {@link Controller} utilizzato per recuperare i dati
     *                  relativi al team, all'hackathon e ai progressi
     * @param origFrame il {@link JFrame} di origine, da cui questa finestra è aperta,
     *                  che verrà ripristinato alla chiusura
     */
    public PartecipanteGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Partecipante");
        frame.setContentPane(mainPanel);
        //frame.setSize(500,275);



        frame.pack();
        frame.setLocationRelativeTo(null);

        caricaProgressiButton.setVisible(false);
        nomeTeamLabel.setText(c.getNomeTeamCorr());
        idTeamLabel.setText(c.getIdTeamCorr());
        codiceLabel.setText(c.getCodiceAccessoTeamCorr());
        nomeCognomeLabel.setText(c.getNomeUtenCorr()+" "+c.getCognomeUtenCorr());

        nomeHackathon.setText(c.getNomeHackCorr());
        closeOperation(origFrame);
        setMembriTable(c);
        setAltriTeamsTable(c);//Ordina i team una volta pubblicata la classifica
        setProblemaTextArea(c);

        setProgressiList(c);
        setSelectProgressList(c);

        setCaricaProgressiButton(c);


        setPosizionamentoPanel(c);
        frame.setVisible(true);
        cotrolloTeamSuff( c);

    }

    /**
     * Imposta il comportamento di chiusura della finestra corrente.
     * <p>
     * Quando l'utente tenta di chiudere questa finestra:
     * <ul>
     *   <li>la finestra di origine ({@code origFrame}) viene resa nuovamente visibile;</li>
     *   <li>la finestra corrente viene chiusa tramite {@link JFrame#dispose()}.</li>
     * </ul>
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
     * Configura la tabella dei membri del team ({@code membriTable}) assegnandole
     * un modello dati personalizzato.
     * <p>
     * Il modello utilizzato è un'istanza di {@link ModelloPartecipantiTab},
     * inizializzata con il {@link Controller}, da cui vengono recuperate le
     * informazioni sui membri del team corrente.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per fornire i dati dei membri del team
     */
    private void setMembriTable(Controller c){
        ModelloPartecipantiTab modello=new ModelloPartecipantiTab(c);
        membriTable.setModel(modello);

    }


    /**
     * Configura la tabella degli altri team ({@code altriTeamsTable}) in base
     * allo stato della classifica dell'hackathon corrente.
     * <p>
     * Se la classifica è stata pubblicata ({@link Controller#isClassificaPubblHackCorr()}):
     * <ul>
     *   <li>i team vengono ordinati in base ai voti tramite {@link Controller#ordinaTeamVoti()};</li>
     *   <li>la tabella utilizza il modello {@link ModelloTeamsVotiTab}.</li>
     * </ul>
     * Altrimenti, viene utilizzato il modello {@link ModelloTeamsTab}.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per recuperare i dati dei team
     *          e lo stato della classifica
     */
    private void setAltriTeamsTable(Controller c) {


        if(c.isClassificaPubblHackCorr()) {
            c.ordinaTeamVoti();
            ModelloTeamsVotiTab modello=new ModelloTeamsVotiTab(c);
            altriTeamsTable.setModel(modello);
        }else
        {
        ModelloTeamsTab modello=new ModelloTeamsTab(c);
            altriTeamsTable.setModel(modello);
        }

    }

    /**
     * Configura l'area di testo {@code problemaTextArea} per visualizzare
     * il problema assegnato all'hackathon corrente.
     * <p>
     * Se il problema è disponibile ({@link Controller#isViewProblemaHackCorr()}),
     * l'area di testo mostra il contenuto del problema tramite
     * {@link Controller#getProblemaHackCorr()}. Altrimenti, viene mostrato
     * il messaggio "Problema non disponibile".
     * </p>
     * <p>
     * L'area di testo viene sempre impostata come non modificabile
     * ({@code setEditable(false)}).
     * </p>
     *
     * @param c il {@link Controller} utilizzato per recuperare le informazioni
     *          sul problema dell'hackathon corrente
     */
    private void setProblemaTextArea(Controller c) {

        if(c.isViewProblemaHackCorr())
            problemaTextArea.setText(c.getProblemaHackCorr());
        else
            problemaTextArea.setText(" Problema non disponibile");
        problemaTextArea.setEditable(false);
    }


    /**
     * Configura il pulsante {@code caricaProgressiButton} per permettere al partecipante
     * di aggiungere nuovi progressi.
     * <p>
     * Il pulsante viene reso visibile solo se:
     * <ul>
     *   <li>il problema dell'hackathon corrente è disponibile ({@link Controller#isViewProblemaHackCorr()});</li>
     *   <li>l'evento non è terminato ({@link Controller#isEventoFinitoHackCorr()}).</li>
     * </ul>
     * </p>
     * <p>
     * Al click del pulsante:
     * <ul>
     *   <li>se la durata dell'hackathon è terminata ({@link Controller#isDurataHCTerminata()}),
     *       viene mostrato un messaggio di avviso e l'operazione viene interrotta;</li>
     *   <li>altrimenti, viene aperta la finestra {@link PartecipanteAddProgressiGUI} per aggiungere
     *       progressi;</li>
     *   <li>la lista dei progressi viene aggiornata tramite {@link #setProgressiList(Controller)}.</li>
     * </ul>
     * </p>
     *
     * @param c il {@link Controller} utilizzato per verificare lo stato dell'hackathon
     *          e per aggiornare i progressi del team
     */
    private void setCaricaProgressiButton(Controller c){

        caricaProgressiButton.setVisible( c.isViewProblemaHackCorr() && !c.isEventoFinitoHackCorr() );


            caricaProgressiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(c.isDurataHCTerminata()){
                        JOptionPane.showMessageDialog(frame,"L'hackathon è terminato\n(Impossibile caricare progressi)");
                        return;
                    }

                    new PartecipanteAddProgressiGUI(c,PartecipanteGUI.this);
                    setProgressiList(c);
                }
            });


    }


    /**
     * Popola la lista {@code progressiList} con i progressi del team corrente.
     * <p>
     * Viene creato un nuovo {@link DefaultListModel} e vengono aggiunti tutti
     * i nomi dei progressi del team corrente ottenuti tramite
     * {@link Controller#getNomiProgressTeamCorr()}. I progressi più recenti
     * vengono inseriti in cima alla lista.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per recuperare i nomi dei progressi
     *          del team corrente
     */
    public void setProgressiList(Controller c) {

        DefaultListModel modello=new DefaultListModel<>();

        ArrayList<String> nomiProgressi=c.getNomiProgressTeamCorr();
        for(String nomeProgresso:nomiProgressi){
            modello.add(0,nomeProgresso);
        }
        progressiList.setModel(modello);

    }

    /**
     * Configura la lista {@code progressiList} per permettere la selezione di un progresso
     * e aggiornare l'area di testo {@code progressiTextArea} con il contenuto selezionato.
     * <p>
     * La lista viene impostata con selezione singola ({@link ListSelectionModel#SINGLE_SELECTION}),
     * mentre l'area di testo viene resa non modificabile.
     * </p>
     * <p>
     * Al cambiare della selezione:
     * <ul>
     *   <li>se non è selezionata alcuna voce, l'area di testo viene svuotata;</li>
     *   <li>altrimenti, il progresso selezionato viene identificato tramite
     *       {@link Controller#identificaProgressoTC(String)} e il suo contenuto
     *       viene mostrato in {@code progressiTextArea} tramite
     *       {@link Controller#getCodiceProgCorr()}.</li>
     * </ul>
     * </p>
     *
     * @param c il {@link Controller} utilizzato per identificare il progresso selezionato
     *          e recuperarne il contenuto
     */
    public void setSelectProgressList(Controller c){

        progressiTextArea.setEditable(false);
        progressiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        progressiList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void  valueChanged(ListSelectionEvent e) {
                String rigaSelezionata= (String) progressiList.getSelectedValue();

                if(rigaSelezionata==null)
                    progressiTextArea.setText("");

                else{

                   c.identificaProgressoTC(rigaSelezionata);
                   progressiTextArea.setText(c.getCodiceProgCorr());

                }

            }
        });
    }


    /**
     * Controlla se il numero di team iscritti all'hackathon corrente è sufficiente
     * per lo svolgimento dell'evento.
     * <p>
     * Se l'evento è pronto ({@link Controller#isEventoPronto()}) ma i team
     * iscritti non sono sufficienti ({@link Controller#isTeamSuffHackCorr()} restituisce false),
     * viene mostrato un messaggio di avviso tramite {@link JOptionPane} e
     * l'etichetta {@code noHackLabel} viene resa visibile.
     * <p>
     * Altrimenti, {@code noHackLabel} viene nascosta.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per verificare lo stato dell'evento
     *          e la sufficienza dei team iscritti
     */
    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.isTeamSuffHackCorr()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (solo 1 team iscritto)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }


    /**
     * Configura il pannello {@code posizionamentoPanel} per mostrare il posizionamento
     * del team corrente nell'hackathon.
     * <p>
     * Se la classifica dell'hackathon corrente è stata pubblicata
     * ({@link Controller#isClassificaPubblHackCorr()}):
     * <ul>
     *   <li>il pannello viene reso visibile;</li>
     *   <li>l'etichetta {@code posizLabel} viene aggiornata con la posizione
     *       calcolata tramite {@link Controller#calcolaPosizionamentoTeamCorrente()}.</li>
     * </ul>
     * Altrimenti, il pannello viene nascosto.
     * </p>
     *
     * @param c il {@link Controller} utilizzato per verificare lo stato della classifica
     *          e calcolare la posizione del team corrente
     */
    public void setPosizionamentoPanel(Controller c) {


        if(c.isClassificaPubblHackCorr()) {
            posizionamentoPanel.setVisible(true);

            posizLabel.setText(c.calcolaPosizionamentoTeamCorrente()+"° posizione");
        }
        else
            posizionamentoPanel.setVisible(false);


    }
}
