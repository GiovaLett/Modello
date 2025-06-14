package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class partecipanteAddProgressiGUI {

    private JDialog frame=new JDialog((Frame) null,"Aggiungi progresso",true);
    private JPanel mainPanel;
    private JTextArea progressoTextArea;
    private JTextField nomeProgressoField;
    private JButton caricaButton;


    public partecipanteAddProgressiGUI(){frame.setContentPane(mainPanel);
        frame.setSize(500,600);
        frame.setLocationRelativeTo(null);




        frame.setVisible(true);}

    public partecipanteAddProgressiGUI(Controller c, Team team, PartecipanteGUI origGUI){


        frame.setContentPane(mainPanel);
        frame.setSize(500,600);
        frame.setLocationRelativeTo(null);

        setCaricaButton(c,team,origGUI);
        CloseOperation( origGUI,  team);


        frame.setVisible(true);
    }

    private void CloseOperation(PartecipanteGUI origGUI, Team team){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origGUI.setProgressiList(team);
                frame.dispose();
            }
        });

    }



    public void setCaricaButton(Controller c,Team team, PartecipanteGUI origGUI) {

        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String progresso=progressoTextArea.getText();
                String nomeProgresso=nomeProgressoField.getText();

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di caricare questo progresso dal nome:\n"+nomeProgresso,"Conferma",JOptionPane.YES_NO_OPTION);
                if(risp==0)
                {
                    c.caricaProgresso(nomeProgresso,progresso,team);

                    JOptionPane.showMessageDialog(frame,"Progresso aggiunto!");
                    origGUI.setProgressiList(team);

                    frame.dispose();

                }
            }
        });

    }
}
