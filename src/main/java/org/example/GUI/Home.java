package org.example.GUI;

import org.example.Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home
{
    private static Controller controller=new Controller();
    private static JFrame frame;
    private JPanel mainPanel;
    private JButton accediButton;
    private JButton registratiButton;

    public Home() {

        frame = new JFrame("Home");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();

        controller.getPiattaforma().buildListaHackathon(5);
        controller.addUtenteReg();
        setAccediButton(controller);
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

    private void setRegistratiButton(){

    }




}
