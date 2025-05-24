package org.example.GUI;

import org.example.Controller.Controller;

import javax.swing.*;

public class UtenteRegistratoIscrCloseGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable hackathonTable;


    public UtenteRegistratoIscrCloseGUI(Controller c)
    {
        frame=new JFrame("Utente");
        frame=new JFrame("Selezione Giudici");
        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        setHackathonTable(c);

        frame.setVisible(true);
        frame.setContentPane(mainPanel);


    }

    public void setHackathonTable(Controller c) {
        ModelloHackTab modello=new ModelloHackTab(c.getListaHackathon());
        hackathonTable.setModel(modello);
    }
}
