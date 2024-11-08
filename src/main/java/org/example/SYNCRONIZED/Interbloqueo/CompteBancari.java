package org.example.SYNCRONIZED.Interbloqueo;

import java.io.IOException;

// Un banc que disposa de un compte determinat salda inicia
// Dos titulars, home i dona en una targeta bancaria
// Suposar una situacio on desde 2 caixers diferents intenten traure diners al mateix temps
public class CompteBancari
{
    private float saldo;

    public float getSaldo() { return saldo; }
    public void setSaldo(float saldo) { this.saldo = saldo; }

    public CompteBancari(float saldoInicial)
    {
        this.setSaldo(saldoInicial);
    }


    private int idCompte; // Interbloqueo

    public int getIdCompte() { return idCompte; } // Interbloqueo
    public void setIdCompte(int idCompte) { this.idCompte = idCompte; } // Interbloqueo

    public CompteBancari(float saldo, int idCompte) // Interbloqueo
    {
        this.saldo = saldo;
        this.idCompte = idCompte;
    }

    public CompteBancari (int idCompte) // Interbloqueo
    {
        this.idCompte = idCompte;
    }


    public void ingressarCB(float diners, String nomFil) throws IOException, InterruptedException
    {
        float aux;
        System.out.println("[" + nomFil + "] Ingressant " + diners + "€");
        aux = getSaldo();
        System.out.println("[" + nomFil + "] " + aux + " = getSaldo() + " + diners + "€");
        aux += diners;
        System.out.println("[" + nomFil + "] setSaldo(" + aux + ")");
        setSaldo(aux);
    }

    public void treureCB(float diners, String nomFil) throws IOException, InterruptedException
    {
        float aux;
        System.out.println("[" + nomFil + "] Traguent " + diners + "€");
        aux = getSaldo();
        System.out.println("[" + nomFil + "] " + aux + " = getSaldo() - " + diners + "€");
        aux -= diners;
        System.out.println("[" + nomFil + "] setSaldo(" + aux + ")");
        setSaldo(aux);
    }
}