package org.example;

import java.io.IOException;

public class CaixerBanc implements Runnable
{
    private CompteBancari compte;
    private int operacioAfer;

    public CaixerBanc(CompteBancari compte, int operacioAfer)
    {
        this.compte = compte;
        this.operacioAfer = operacioAfer;
    }

    // METODO INGRESAR DINERO
    public void ingressar(float diners) throws IOException, InterruptedException
    {
        this.compte.ingressarCB(diners, Thread.currentThread().getName());
    }

    // METODO RETIRAR DINERO
    public void treure(float diners) throws IOException, InterruptedException
    {
        this.compte.treureCB(diners, Thread.currentThread().getName());
    }

    @Override
    public void run() {
        try
        {
            switch (operacioAfer)
            {
                case 1:
                {
                    System.out.println("[" + Thread.currentThread().getName() + "] (1/2) Ingressant 400");
                    ingressar(400);
                    break;
                }
                default:
                {
                    System.out.println("[" + Thread.currentThread().getName() + "] (2/2) Traguent 200");
                    treure(200);
                    break;
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
