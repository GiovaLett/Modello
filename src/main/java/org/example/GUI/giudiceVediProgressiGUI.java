package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;
import org.example.Model.Progresso;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        public giudiceVediProgressiGUI(Controller c, Hackathon hackathon, Team team,giudiceGUI origGUI)
        {
            frame=new JDialog((Frame) null,team.getNome(),true);
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nomeTeamLabel.setText(team.getNome());
            frame.setSize(500,750);
            frame.setLocationRelativeTo(null);


            setProgressiList(team);
            setVotoFinalePanel(hackathon,team);

            if(hackathon.isEventoFinito() && !hackathon.isVotazioneConclusa())

                setFrameHackFinito(c,hackathon,team,origGUI);//Per permettere le votazioni


            else if(!hackathon.isEventoFinito()) {

                votaTeamPanel.setVisible(false);
                setSalvaButton(team);
            }
            else {
                votaTeamPanel.setVisible(false);
                commentoTextArea.setEditable(false);
                salvaButton.setVisible(false);
            }


            frame.setVisible(true);



        }

        public void setProgressiList(Team team) {
            DefaultListModel modello=new DefaultListModel<>();
            for(Progresso progresso:team.getArrayProgresso()){
                modello.add(0,progresso.getNome());
            }
            progressiList.setModel(modello);

            codiceTextArea.setEditable(false);
            progressiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            progressiList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String rigaSelezionata= (String) progressiList.getSelectedValue();
                    for(Progresso progresso : team.getArrayProgresso())
                    {
                        if(rigaSelezionata.equals(progresso.getNome()))
                        {
                            codiceTextArea.setText(progresso.getCodiceProgresso());
                            commentoTextArea.setText(progresso.getCommento());
                        }
                    }
                }
            });
        }




        public void setSalvaButton(Team team) {
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
                            for (Progresso progresso : team.getArrayProgresso()) {
                                if (rigaSelezionata.equals(progresso.getNome())) {
                                    progresso.setCommento(commento);
                                }
                            }
                            JOptionPane.showMessageDialog(frame, "Commento Salvato");
                        }
                    }
                }
            });


        }

        private void setFrameHackFinito(Controller c,Hackathon hackathon,Team team,giudiceGUI origGUI){
            commentoTextArea.setEditable(false);
            salvaButton.setVisible(false);
            votaTeamPanel.setVisible(true);


            assegnaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try{
                        c.assegnaVotoTeam(team, votoField.getText());
                        JOptionPane.showMessageDialog(frame,"Voto inserito");
                        origGUI.setTeamTable(c,hackathon);

                    }catch(IllegalArgumentException exce){
                        JOptionPane.showMessageDialog(frame,exce.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });




        }

    public void setVotoFinalePanel(Hackathon hackathon,Team team) {
        if(hackathon.isVotazioneConclusa())
        {
            votoFinaleLabel.setText(String.valueOf(team.getVoto()));
            votoFinalePanel.setVisible(true);
        }

        else
            votoFinalePanel.setVisible(false);
    }
}





