package org.example.GUI;

import org.example.Controller.Controller;

import javax.swing.*;

public class organizzatoreGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeAmministratore;
    private JButton apriIsrizButton;
    private JTable hackathonTable;
    private JTextField idHackField;
    private JButton entraButton;


    public organizzatoreGUI(Controller c){

        frame=new JFrame();
        frame.setContentPane(mainPanel);
        setHackathonTable();
        frame.pack();
        frame.setVisible(true);


    }

    private void seeGiudiciTable(){

        /*if(c.Listagiudici.size()>0)
        * giudiciTable.setVisible(true)*/
    }



   private void setHackathonTable(){
        ModelloHackTab modello=new ModelloHackTab();
        hackathonTable.setModel(modello);
   }

    private void seeApriIscrizButton(){}
}
