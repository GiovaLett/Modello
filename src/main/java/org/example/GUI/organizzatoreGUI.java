package org.example.GUI;

import org.example.controller.Controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;

public class organizzatoreGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel nomeAmministratore;
    private JButton apriIsrizButton;
    private JTable hackathonTable;
    private JTextField idHackField;
    private JButton entraButton;
    private JButton chiudiIscrizioniButton;
    private JTextField nomeHackathonField;
    private JButton aggiungiButton;
    private JPanel creaHackathonPanel;
    private JButton rimuoviButton;
    private JSpinner giornoSpinner;
    private JSpinner meseSpinner;
    private JSpinner annoSpinner;
    private JButton impostaDataButton;
    private JPanel dataEventoPanel;
    private JTextField sedeField;
    private JTextField durataField;


    public organizzatoreGUI(Controller c,JFrame origFrame){

        frame=new JFrame();
        nomeAmministratore.setText(c.getNomeUtenCorr()+" "+c.getCognomeUtenCorr());
        frame.setContentPane(mainPanel);
        setHackathonTable(c);
        frame.pack();
        frame.setLocationRelativeTo(null);

        setRimuoviButton(c);
        setAggiungiButton(c);
        setApriIsrizButton(c);
        setChiudiIscrizioniButton(c);
        setEntraButton(c);
        setDataEventoPanel(c);
        CloseOperation( origFrame);
        frame.setVisible(true);


    }





   private void setHackathonTable(Controller c){
        ModelloHackTab modello=new ModelloHackTab(c);
        hackathonTable.setModel(modello);
   }


   private void setDataEventoPanel(Controller c){

        dataEventoPanel.setVisible(!c.isEventoPronto() && !c.isOpenIscri());
        setAnnoSpinner();
        setMeseSpinner();
        setGiornoSpinner(1);
       setImpostaDataButton(c);
    }

   private void setGiornoSpinner(int giornoSalvato){


        int mese=(int)meseSpinner.getValue();
       SpinnerNumberModel modello=null;

        if(mese==1 || mese==3 || mese==5 || mese==7 || mese==8 || mese==10 || mese==12){
             modello=new SpinnerNumberModel(giornoSalvato,1,31,1);

        }
       else if(mese==4 || mese==6 || mese==9 || mese==11 ){


           if(giornoSalvato>30)
              modello=new SpinnerNumberModel(30,1,30,1);
           else
               modello=new SpinnerNumberModel(giornoSalvato,1,30,1);


       }
       else if(mese==2){
            if(giornoSalvato>28)
                modello=new SpinnerNumberModel(28,1,30,1);
            else
                modello=new SpinnerNumberModel(giornoSalvato,1,30,1);

        }
       giornoSpinner.setModel(modello);


   }

   private void setMeseSpinner(){

        SpinnerNumberModel modello=new SpinnerNumberModel(6,1,12,1);
        meseSpinner.setModel(modello);

        meseSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int giornoSalvato=(int)giornoSpinner.getValue();
                setGiornoSpinner(giornoSalvato);


            }
        });
   }

   private void setAnnoSpinner(){

       LocalDate dataOggi=LocalDate.now();
       int annoOdierno=dataOggi.getYear();
       SpinnerNumberModel modello=new SpinnerNumberModel(annoOdierno,annoOdierno,null,1);
       annoSpinner.setModel(modello);
   }

   private void setImpostaDataButton(Controller c){

        impostaDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 int giorno= (int)giornoSpinner.getValue();
                int mese= (int)meseSpinner.getValue();
                int  anno= (int)annoSpinner.getValue();

                try{
                    c.setDataEvento(giorno,mese,anno);
                    JOptionPane.showMessageDialog(frame,"Data impostata");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame,ex.getMessage());
                }

            }
        });

   }

   private void setEntraButton(Controller c){
        entraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();

                try {
                    c.identificaHackathon(idHack);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(!c.isOpenIscri() && !c.isEventoPronto())  {
                    new organHackPreIscrizGUI( c, frame);
                }
                else    {
                    new oraganHackGUI(c,frame);
                }

                    frame.setVisible(false);

            }

            });
   }



    public void setRimuoviButton(Controller c) {

        if(!c.isOpenIscri() && !c.isEventoPronto())
            rimuoviButton.setVisible(true);
        else
            rimuoviButton.setVisible(false);

        this.rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idHack = idHackField.getText();


                try {
                    c.identificaHackathon(idHack);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int risp=JOptionPane.showConfirmDialog(frame,"Sicuro di voler eliminare l'hackathon: "+
                        c.getNomeHackCorr()+" ?","Conferma",0);
                if(risp==0)
                {
                    try{
                    c.removeHackCorr();
                    JOptionPane.showMessageDialog(frame,"Hackathon eliminato!");}
                    catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame,"Errore DB","",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void setAggiungiButton(Controller c) {

        if(!c.isOpenIscri() && !c.isEventoPronto())
            creaHackathonPanel.setVisible(true);
        else
            creaHackathonPanel.setVisible(false);

        this.aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeHackathon= nomeHackathonField.getText();
                String sedeHackathon=sedeField.getText();
                String durataHackathonStr=durataField.getText();
                int durataHackathon;

                if(nomeHackathon.equals("") || sedeHackathon.equals("") || durataHackathonStr.equals(""))
                    JOptionPane.showMessageDialog(frame,"Nessun campo è facoltativo");
                else {

                    try{
                        durataHackathon=Integer.parseInt(durataHackathonStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame,"Il formato del campo durata deve essere un intero\n(Es. 1,2,34...");
                        return;
                    }


                    int risp=JOptionPane.showConfirmDialog(frame,"Confermare:\n" +
                            "- Nome:"+nomeHackathon+"\n- Sede:"+sedeHackathon+"\n- Durata:"+durataHackathon+"g","Conferma",0);
                    if(risp==JOptionPane.YES_OPTION){

                        try{
                            c.addHackathon(nomeHackathon,sedeHackathon,durataHackathon);      }
                        catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frame,"Errore con DB","",JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(frame,"Hackathon aggiunto");
                        setHackathonTable(c);
                    }
                }
            }
        });
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

    public void setApriIsrizButton(Controller c) {

        apriIsrizButton.setVisible(!c.isOpenIscri() && !c.isEventoPronto());
        apriIsrizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c.getDataEvento()==null){
                    JOptionPane.showMessageDialog(frame,"Imposta prima una data di inizio dell'evento");
                    return;
                }
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di aprire le iscrizioni\n" +
                                "- modifiche ai giudici e agli hackathon non più disponibili\n" +
                                "- Hackathon senza giudici verranno eliminati\n "+
                                "- Data dell'evento impostata: "+c.getDataEvento(),
                        "Conferma?",
                        JOptionPane.YES_NO_OPTION);
                if(risposta==JOptionPane.YES_OPTION) {

                    try{
                    c.apriIscrizioni();
                    apriIsrizButton.setVisible(false);
                    creaHackathonPanel.setVisible(false);
                    rimuoviButton.setVisible(false);
                    dataEventoPanel.setVisible(false);
                    setHackathonTable(c);}
                    catch (SQLException exc){
                        JOptionPane.showMessageDialog(frame,"Errore DB","",JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
                    }

                }

            }
        });
    }

    public void setChiudiIscrizioniButton(Controller c) {

        chiudiIscrizioniButton.setVisible(c.isOpenIscri());

        chiudiIscrizioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta=JOptionPane.showConfirmDialog(frame,"Sicuro di Forzare la chiusura delle iscrizioni prima dei 2 giorni dall'evento?", "Conferma?", JOptionPane.YES_NO_OPTION);

                if(risposta==JOptionPane.YES_OPTION) {

                    try{
                    c.chiudiIscrizioni();
                    chiudiIscrizioniButton.setVisible(false);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });
    }


}
