package org.example.gui;

import org.example.controller.Controller;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GiudiceVediProgressiGUI {


        private JDialog frame;
        private JPanel mainPanel;
        private JTextArea codiceTextArea;
        private JTextArea commentoTextArea;
        private JList progressiList;
        private JButton salvaButton;
    private JTextField votoField;
    private JButton assegnaButton;
    private JPanel votaTeamPanel;
    private JPanel votoFinalePanel;
    private JLabel votoFinaleLabel;
    private JLabel nomeTeamLabel;


    /**
     * Costruttore che imposta il frame, il quale è un istanza della classe "JDialog".
     * Viene impostata una posizione centrale, la dimensione e la modalità di chiusura.
     * Poi vengono chiamati dei metodi interni alla classe utilizzati per caratterizzare il frame in base al contesto,
     * intraprendendo 2 aspetti:
     * 1) Commentare i progressi mentre l hackathon è in corso;
     * 2) Votare i team una volta terminato l' hackathon
     * @param c Controller
     * @param origGUI utilizzato per aggiornare in tempo reale la tabella team del frame precedente quando
     *                viene inserita una votazione, utilizzando il metodo {@link #setFrameHackFinito(Controller, GiudiceGUI)}
     */
        public GiudiceVediProgressiGUI(Controller c, GiudiceGUI origGUI)
        {
            frame=new JDialog((Frame) null,c.getNomeTeamCorr(),true);
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nomeTeamLabel.setText(c.getNomeTeamCorr());
            frame.setSize(500,750);
            frame.setLocationRelativeTo(null);


            setProgressiList(c);
            setVotoFinalePanel(c);

            if(c.isEventoFinitoHackCorr() && !c.isVotazioneConclusaHackCorr())

                setFrameHackFinito(c,origGUI);//Per permettere le votazioni


            else if(!c.isEventoFinitoHackCorr()) {

                votaTeamPanel.setVisible(false);
                setSalvaButton(c);
            }
            else {
                votaTeamPanel.setVisible(false);
                commentoTextArea.setEditable(false);
                salvaButton.setVisible(false);
            }


            frame.setVisible(true);



        }

    /**
     * Caratterizza la lista dei progressi, aggiungendo tutti i suoi nomi e facendo si che una volta cliccato su
     * uno di essi compaia il relativo commento e codice
     * @param c Controller
     */
    public void setProgressiList(Controller c) {
            DefaultListModel modello=new DefaultListModel<>();

            ArrayList<String> nomiProgressi= c.getNomiProgressTeamCorr();

            for(String nomeProgresso: nomiProgressi){
                modello.add(0,nomeProgresso);
            }
            progressiList.setModel(modello);

            codiceTextArea.setEditable(false);
            progressiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            progressiList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String rigaSelezionata= (String) progressiList.getSelectedValue();

                    if(rigaSelezionata==null){
                        codiceTextArea.setText("");
                        commentoTextArea.setText("");
                    }


                    else{
                        c.identificaProgressoTC(rigaSelezionata);

                        codiceTextArea.setText(c.getCodiceProgCorr());
                        commentoTextArea.setText(c.getCommentoProgCorr());
                    }


                }
            });
        }


    /**
     * Metodo che imposta le funzionalità del pulsante "salvaButton" utilizzato per salvare un commento inerente a un determinato progresso.
     * Nel caso non sia stato selezionato alcun progresso comparirà il messaggio: "Seleziona prima una progresso della lista";
     * Altrimenti compare un messaggio di conferma per il salvataggio del commento.
     * @param c Controller
     */
        public void setSalvaButton(Controller c) {
            salvaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(progressiList.getSelectedIndex()==-1){
                        JOptionPane.showMessageDialog(frame,"Seleziona prima una progresso della lista");

                    }
                    else {
                        String commento = commentoTextArea.getText();
                        int risp = JOptionPane.showConfirmDialog(frame, "Sicuro di salvare il seguente commento:\n" + commento + "\n", "Salvataggio", JOptionPane.YES_NO_OPTION);

                        if (risp == 0) {
                            String rigaSelezionata = (String) progressiList.getSelectedValue();

                            try{
                                c.setCommentoTeamCorrente(commento,rigaSelezionata);
                                JOptionPane.showMessageDialog(frame, "Commento Salvato");
                            }catch (SQLException ex){
                                ex.printStackTrace();
                            }

                        }
                    }
                }
            });


        }

    /**
     * Imposta il frame per quando l' hackathon è finito.
     * Quindi i commenti non saranno più editabili e il bottone che permette il salvataggio del commento viene nascosto.
     * Viene reso visibile il "JPanel" per la votazione, in cui all'interno presenta il pulsante "assegnaButton" per assegnare
     * il voto che è stato inserito nel widget "votoField";
     * @param c Controller
     * @param origGUI per aggiornare in tempo reale la tabella dei teams del frame precedente quando viene salvato un voto
     */
        private void setFrameHackFinito(Controller c, GiudiceGUI origGUI){
            commentoTextArea.setEditable(false);
            salvaButton.setVisible(false);
            votaTeamPanel.setVisible(true);


            assegnaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try{
                        c.assegnaVotoTeamCorr( votoField.getText());
                        JOptionPane.showMessageDialog(frame,"Voto inserito");
                        origGUI.setTeamTable(c);

                    }catch(IllegalArgumentException exce){
                        JOptionPane.showMessageDialog(frame,exce.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
                    }catch (SQLException ex){

                        ex.printStackTrace();
                    }
                }
            });




        }

    /**
     * Nel caso in cui le votazioni si siano concluse, viene reso visibile il "JPanel" votoFinalePanel, il quale mostra il voto
     * finale che il team ha ottenuto
     * @param c Controller
     */
    public void setVotoFinalePanel(Controller c) {
        if(c.isVotazioneConclusaHackCorr())
        {
            votoFinaleLabel.setText(String.valueOf(c.getVotoTeamCorr()));
            votoFinalePanel.setVisible(true);
        }

        else
            votoFinalePanel.setVisible(false);
    }
}





