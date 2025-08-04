package org.example.GUI;

import org.example.controller.Controller;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UtenteRegistrIscrOpenGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable hackathonTable;
    private JTextField idHackField;
    private JButton entraButton;
    private JLabel nomeCognomLabel;
    private JLabel idLabel;
    private JLabel scadenzaLabel;

    public UtenteRegistrIscrOpenGUI(Controller c,JFrame origFrame){

        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        nomeCognomLabel.setText(c.getNomeUtenCorr()+" "+c.getCognomeUtenCorr());
        idLabel.setText(c.getIdUtenCorr());

        CloseOperation(origFrame);
        setHackathonTable(c);
        setEntraButton(c);
        setScadenzaLabel(c);
        frame.setVisible(true);
        if(c.isListaHackathonEmpty())
            JOptionPane.showMessageDialog(frame,"Gli hackathon purtroppo non si svolgeranno\n" );

    }

    public void setHackathonTable(Controller c) {


        ModelloHackTab modello=new ModelloHackTab(c);

        hackathonTable.setModel(modello);
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

    public void setScadenzaLabel(Controller c) {
        this.scadenzaLabel.setText(c.getDataScadenzaIscriz());
    }

    private void setEntraButton(Controller c){
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();


                try {
                    c.identificaHackathon(idHack);
                    new UtenteToPartecipanteGUI( c,frame);
                    frame.setVisible(false);

                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            }

        });
    }
}
