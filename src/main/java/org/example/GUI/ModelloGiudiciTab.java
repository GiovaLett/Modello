package org.example.GUI;

import org.example.Model.ruoli.Giudice;
import org.example.Model.ruoli.Utente_registrato;
import org.example.controller.Controller;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloGiudiciTab extends AbstractTableModel {

    Controller contr;

    private String[] nomeColonne ={"ID","Nome","Cognome","Email"};



    ModelloGiudiciTab(Controller c){

        contr=c;

    }


    @Override
    public int getRowCount() {
        return contr.getSizeListaGiud();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColonne[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) //ID,nome,cognome,email;
    {


        if(columnIndex==0)  { return contr.idGiudTab(rowIndex);}
        if(columnIndex==1)  {return contr.nomeGiudTab(rowIndex);}
        if(columnIndex==2)  {return contr.cognGiudTab(rowIndex);}
        if(columnIndex==3)  {return contr.emailGiudTab(rowIndex);}
        else  return null;




    }
}
