package org.example.gui;

import org.example.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Home
{
    private static int nApriHome=0;
    private static Controller controller=new Controller();
    private static JFrame frame = new JFrame("Home");
    private JPanel mainPanel;
    private JButton accediButton;
    private JButton registratiButton;

    public Home() {



        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();

        if(nApriHome==0){


            try{
                controller.getCostantiIdDB();
                controller.getAllHackathonDB();//Importante l'ordine!!!
                controller.getAllTeamDB();
                controller.getUtentiRegistratiDB();
                controller.getFlagsPiattaformaDB();
                controller.getDataEventoDB();


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame,"Errore con il sistema, siamo spiacenti\n(Riavvia il programma)");
                e.printStackTrace();
            }

            nApriHome++;
        }


        setAccediButton(controller);
        setRegistratiButton(controller );
        frame.setVisible(true);

    }

    private void setAccediButton(Controller c){

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccediGUI(frame,c);
                frame.setVisible(false);
            }
        });

    }

    private void setRegistratiButton(Controller c){
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrazioneGUI( c,frame);
                frame.setVisible(false);
            }
        });

    }




}
