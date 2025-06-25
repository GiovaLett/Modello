package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.Progresso;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PartecipanteGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable membriTable;
    private JLabel nomeTeamLabel;
    private JLabel IDTeamLabel;
    private JTextArea problemaTextArea;
    private JButton caricaProgressiButton;
    private JLabel codiceLabel;
    private JLabel nomeCognomeLabel;
    private JLabel nomeHackathon;
    private JTable altriTeamsTable;
    private JList progressiList;
    private JTextArea progressiTextArea;
    private JPanel codicePanel;
    private JPanel listPanel;
    private JScrollPane progressListScroll;
    private JLabel noHackLabel;
    private JLabel classificaField;
    private JPanel posizionamentoPanel;
    private JLabel posizLabel;

    public PartecipanteGUI(){
        frame=new JFrame("Partecipante");
        frame.setContentPane(mainPanel);
        /*codicePanel.setSize(90,100);
        progressiList.setFixedCellWidth(50);
        progressListScroll.setSize(50,20);
        listPanel.setSize(50,20);*/
        frame.pack();
       // frame.setSize(1200,600);



        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public PartecipanteGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Partecipante");
        frame.setContentPane(mainPanel);
        //frame.setSize(500,275);



        frame.pack();
        frame.setLocationRelativeTo(null);

        caricaProgressiButton.setVisible(false);
        nomeTeamLabel.setText(c.getTeamCorrente().getNome());
        IDTeamLabel.setText(c.getTeamCorrente().getID());
        codiceLabel.setText(c.getTeamCorrente().getCodiceAccesso());
        nomeCognomeLabel.setText(c.getUtenteCorrente().getNome()+" "+c.getUtenteCorrente().getCognome());

        nomeHackathon.setText(c.getHackathonCorrente().getNome());
        CloseOperation(origFrame);
        setMembriTable(c);
        setAltriTeamsTable(c);
        setProblemaTextArea(c);

        setProgressiList(c);
        setSelectProgressList(c);

        setCaricaProgressiButton(c);


        setPosizionamentoPanel(c);
        frame.setVisible(true);
        cotrolloTeamSuff( c);

    }


    private void CloseOperation(JFrame origFrame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origFrame.setVisible(true);
                frame.dispose();
            }
        });

    }

    private void setMembriTable(Controller c){
        ModelloPartecipantiTab modello=new ModelloPartecipantiTab(c.getTeamCorrente().getArrayPartecipante());
        membriTable.setModel(modello);

    }

    private void setAltriTeamsTable(Controller c) {
        ModelloTeamsTab modello=new ModelloTeamsTab(c.getHackathonCorrente().getListaTeam());
        altriTeamsTable.setModel(modello);
    }

    private void setProblemaTextArea(Controller c) {

        if(c.getHackathonCorrente().isView_problema())
            problemaTextArea.setText(c.getHackathonCorrente().getProblema());
        else
            problemaTextArea.setText(" Problema non disponibile");
        problemaTextArea.setEditable(false);
    }

    private void setCaricaProgressiButton(Controller c){

        caricaProgressiButton.setVisible( c.getHackathonCorrente().isView_problema() && !c.getHackathonCorrente().isEventoFinito() );


            caricaProgressiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new partecipanteAddProgressiGUI(c,PartecipanteGUI.this);
                    setProgressiList(c);
                }
            });


    }

    public void setProgressiList(Controller c) {
        DefaultListModel modello=new DefaultListModel<>();
        for(Progresso progresso:c.getTeamCorrente().getArrayProgresso()){
            modello.add(0,progresso.getNome());
        }
        progressiList.setModel(modello);

    }

    public void setSelectProgressList(Controller c){

        progressiTextArea.setEditable(false);
        progressiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        progressiList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String rigaSelezionata= (String) progressiList.getSelectedValue();
                for(Progresso progresso : c.getTeamCorrente().getArrayProgresso())
                {
                    if(rigaSelezionata.equals(progresso.getNome()))
                    {
                        progressiTextArea.setText(progresso.getCodiceProgresso());
                    }
                }
            }
        });
    }


    private void cotrolloTeamSuff(Controller c)
    {
        if(c.isEventoPronto() && !c.getHackathonCorrente().isTeam_suffic()) {
            JOptionPane.showMessageDialog(frame, "L'hackathon non si svolgerà\n (solo 1 team iscritto)");
            noHackLabel.setVisible(true);
        }
        else
            noHackLabel.setVisible(false);
    }

    public void setPosizionamentoPanel(Controller c) {

        int posiz=0;
        if(c.getHackathonCorrente().isClassificaPubblicata()) {
            posizionamentoPanel.setVisible(true);

            for (Team team : c.getHackathonCorrente().getListaTeam())
            {
                if (team.getID().equals(c.getTeamCorrente().getID()))
                {
                    posiz=c.getHackathonCorrente().getListaTeam().indexOf(team)+1;
                    break;
                }
            }
            posizLabel.setText(posiz+"° posizione");
        }
        else
            posizionamentoPanel.setVisible(false);


    }
}
