package org.example.GUI;

import org.example.controller.Controller;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class giudiceVediProgressiGUI {


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


    public giudiceVediProgressiGUI(){
        frame=new JDialog((Frame) null,"",true);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(500,550);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }

        public giudiceVediProgressiGUI(Controller c, giudiceGUI origGUI)
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

        private void setFrameHackFinito(Controller c,giudiceGUI origGUI){
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





