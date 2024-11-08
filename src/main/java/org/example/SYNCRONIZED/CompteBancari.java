package org.example.SYNCRONIZED;

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

    // Sincronizamos los metodos enteros - Añadir Syncronized en la seccion critica
    public synchronized void ingressarCB(float diners, String nomFil) throws IOException, InterruptedException
    {
        float aux;
        System.out.println("[" + nomFil + "] Ingressant " + diners + "€");
        aux = getSaldo();
        System.out.println("[" + nomFil + "] " + aux + " = getSaldo() + " + diners + "€");
        aux += diners;
        System.out.println("[" + nomFil + "] setSaldo(" + aux + ")");
        setSaldo(aux);
    }

    public synchronized void treureCB(float diners, String nomFil) throws IOException, InterruptedException
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