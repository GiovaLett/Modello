package org.example.GUI;

import org.example.Controller.Controller;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class UtenteToPartecipanteGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton creaTeamButton;
    private JTable teamTable;
    private JTextField codiceAccessoField;
    private JButton accediButton;

    public UtenteToPartecipanteGUI(Controller c, JFrame origFrame){

        frame=new JFrame("Utente");

        frame.setContentPane(mainPanel);

        frame.setSize(500,275);
        frame.setLocationRelativeTo(null);

        CloseOperation(origFrame);


        setTeamTable(c);
        setAccediButton(c);
        setCreaTeamButton(c);

        frame.setVisible(true);
        frame.setContentPane(mainPanel);
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

    private void setTeamTable(Controller c) {
        ModelloTeamsTab modello=new ModelloTeamsTab(c.getHackathonCorrente().getListaTeam());
        teamTable.setModel(modello);
    }

    private void setAccediButton(Controller c) {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String codiceTeam= codiceAccessoField.getText();


                try {
                    c.setTeamCorrente(  c.findCodeAccessTeam(codiceTeam,c.getHackathonCorrente())  );
                }
                catch(IllegalArgumentException exception)
                {
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"Not Found",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try { c.addPartecToTeam(c.getTeamCorrente(),c.getHackathonCorrente()); }
                catch (IllegalArgumentException exception)
                {
                    JOptionPane.showMessageDialog(frame,exception.getMessage()," ",JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return;
                }


                JOptionPane.showMessageDialog(frame,"Ora sei membro del team:\n"+c.getTeamCorrente().getNome());
                JOptionPane.showMessageDialog(frame,"Sarai indirizzato alla Home, riaccedi per vedere i nuovi aggiornamenti al profilo!");
                frame.dispose();
                new Home();




            }
        });
    }

    private void setCreaTeamButton(Controller c) {
        creaTeamButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

                new UtenteCreaTeamGUI( c, frame);

            }
        });
    }
}
