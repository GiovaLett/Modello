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


    private void closeOperation(JFrame origFrame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origFrame.setVisible(true);
                frame.dispose();
            }
        });

    }

    private void setMembriTable(Controller c){
        ModelloPartecipantiTab modello=new ModelloPartecipantiTab(c);
        membriTable.setModel(modello);

    }

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

    private void setProblemaTextArea(Controller c) {

        if(c.isViewProblemaHackCorr())
            problemaTextArea.setText(c.getProblemaHackCorr());
        else
            problemaTextArea.setText(" Problema non disponibile");
        problemaTextArea.setEditable(false);
    }

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


    public void setProgressiList(Controller c) {

        DefaultListModel modello=new DefaultListModel<>();

        ArrayList<String> nomiProgressi=c.getNomiProgressTeamCorr();
        for(String nomeProgresso:nomiProgressi){
            modello.add(0,nomeProgresso);
        }
        progressiList.setModel(modello);

    }

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


    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.isTeamSuffHackCorr()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (solo 1 team iscritto)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }

    public void setPosizionamentoPanel(Controller c) {


        if(c.isClassificaPubblHackCorr()) {
            posizionamentoPanel.setVisible(true);

            posizLabel.setText(c.calcolaPosizionamentoTeamCorrente()+"° posizione");
        }
        else
            posizionamentoPanel.setVisible(false);


    }
}
