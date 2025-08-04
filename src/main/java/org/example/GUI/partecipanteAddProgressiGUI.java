package org.example.GUI;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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

    public partecipanteAddProgressiGUI(Controller c, PartecipanteGUI origGUI){


        frame.setContentPane(mainPanel);
        frame.setSize(500,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setCaricaButton(c,origGUI);
        CloseOperation( c,origGUI);


        frame.setVisible(true);
    }

    private void CloseOperation(Controller c, PartecipanteGUI origGUI){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                origGUI.setProgressiList(c);
                frame.dispose();
            }
        });

    }



    public void setCaricaButton(Controller c, PartecipanteGUI origGUI) {

        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String progresso=progressoTextArea.getText();
                String nomeProgresso=nomeProgressoField.getText();

                if( !c.isNomeProgressoOriginale(nomeProgresso)){
                    JOptionPane.showMessageDialog(frame,"Presente un altro progresso con lo stesso nome\n" +
                            "(Cambiare nome al progresso corrente)");
                    return;
                }

                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di caricare questo progresso dal nome:\n"+nomeProgresso,"Conferma",JOptionPane.YES_NO_OPTION);
                if(risp==0)
                {
                    try{
                        c.caricaProgressoTeamCorr(nomeProgresso,progresso);

                        JOptionPane.showMessageDialog(frame,"Progresso aggiunto!");
                        origGUI.setProgressiList(c);

                        frame.dispose();
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }


                }
            }
        });

    }
}
