package org.example.GUI;

import org.example.Controller.Controller;

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
            //controller.getPiattaforma().buildListaHackathon(5);

            try{
                controller.getCostantiID();
                controller.getAllHackathonDB();//Importante l'ordine!!!
                controller.getAllTeamDB();
                controller.getUtentiRegistratiDB();
                controller.getFlagsPiattaforma();


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame,"Errore Database","",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            //controller.addUtentiProva();
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
                new registrazioneGUI( c,frame);
                frame.setVisible(false);
            }
        });

    }




}
