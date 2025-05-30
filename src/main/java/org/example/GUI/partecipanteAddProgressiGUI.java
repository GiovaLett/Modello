package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.ruoli.Team;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class partecipanteAddProgressiGUI {

    private JDialog frame=new JDialog();
    private JPanel mainPanel;
    private JTextArea progressoTextArea;
    private JTextField nomeProgressoField;
    private JButton caricaButton;

    public partecipanteAddProgressiGUI(Controller c, Team team, JFrame origFrame){

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        setCaricaButton(c,team);
        closeOperation(origFrame);
        frame.setVisible(true);
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

    public void setCaricaButton(Controller c,Team team) {

        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String progresso=progressoTextArea.getText();
                String nomeProgresso=nomeProgressoField.getText();

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di caricare questo progresso dal nome:\n"+nomeProgresso);
                if(risp==0)
                {
                    c.caricaProgresso(nomeProgresso,progresso,team);
                }
            }
        });

    }
}
