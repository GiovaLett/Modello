package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

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

    public UtenteRegistrIscrOpenGUI(Controller c,JFrame origFrame){

        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        CloseOperation(origFrame);
        setHackathonTable(c);
        setEntraButton(c);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);
    }

    public void setHackathonTable(Controller c) {
        ModelloHackTab modello=new ModelloHackTab(c.getListaHackathon());
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



    private void setEntraButton(Controller c){
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();
                Hackathon hackathon;

                try {
                    hackathon = c.findHackId(idHack);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                new UtenteToPartecipanteGUI( c,frame, hackathon);
                frame.setVisible(false);
            }

        });
    }
}
