package org.example;

import org.example.Controller.Controller;
import org.example.GUI.Home;
import org.example.GUI.organizzatoreGUI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        new organizzatoreGUI(new Controller());


    }
}


/*DA RISOLVERE
* 1)  PARTECIPANTE,UTENTE: per il fatto che non si faccia a meno 2 giorni --> trovare modo per rappres. giorno attuale
* 2)  in che modo utente registrato diventa poi un partecipante (senza ridondanze). Diciamo il discorso vale per tutte le classi da padre a figlio (forse il garbage collector fa in automatico dato che non viene più usato?)
* 3)  Rifare la creazione di una classifica*/