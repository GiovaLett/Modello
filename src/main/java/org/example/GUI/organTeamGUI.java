package org.example.GUI;

import org.example.Controller.Controller;
import org.example.Model.Hackathon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class organTeamGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeHackLabel;
    private JTable giudiciTable;
    private JTable utentiTable;
    private JTextField idUtenteField;
    private JButton selezGiudButton;
    private JLabel IdLabel;
    private JPanel utentiTabPanel;

    organTeamGUI(Controller c, JFrame origFrame, Hackathon hackathon){

        frame=new JFrame("Selezione Giudici");
        frame.setContentPane(mainPanel);
        nomeHackLabel.setText(hackathon.getNome());
        IdLabel.setText(hackathon.getID());
        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        if(c.isOpenIscri()){
            selezGiudButton.setVisible(false);
        }

        else {

            setUtentiTable(c);

            setSelezGiudButton(c, hackathon);
        }
        setGiudiciTable(c, hackathon);
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

    private void setUtentiTable(Controller c){
        ModelloUtentiTabella modello=new ModelloUtentiTabella(c.getListaUtenti());
        utentiTable.setModel(modello);

    }

    private void setSelezGiudButton(Controller c,Hackathon hackathon){
        selezGiudButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IdUtente = idUtenteField.getText();

                try {c.addGiudiceHackaton(hackathon,IdUtente);}
                catch(IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(),"Errore", 0);
                    return;
                }

                setGiudiciTable(c,hackathon);
                setUtentiTable(c);

            }
        });
    }

    private void setGiudiciTable(Controller c,Hackathon hackathon){
        ModelloGiudiciTab modello=new ModelloGiudiciTab( c.getListaGiudici( hackathon.getID() ) );
        giudiciTable.setModel(modello);

    }
}
