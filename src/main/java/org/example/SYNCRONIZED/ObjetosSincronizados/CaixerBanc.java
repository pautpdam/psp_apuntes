package org.example.SYNCRONIZED.ObjetosSincronizados;

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
        // Java permet sincronitzar només una part del codi dins d'ún método
        // Volem que s'execute en exclusió mútua
        try
        {
            switch (operacioAfer)
            {
                case 1:
                    synchronized(compte) // referenciar un objecte
                                         // SINCRONIZACION SOBRE EL OBJETO COMPARTIDO COMPTE
                    {
                        System.out.println("[" + Thread.currentThread().getName() + "] (1/2) Ingressant 400");
                        ingressar(400);
                        System.out.println("[" + Thread.currentThread().getName() + "] (1/2) Traguent 400");
                        treure(400);
                        break;
                    }
                default:
                synchronized(compte)
                {
                        System.out.println("[" + Thread.currentThread().getName() + "] (2/2) Traguent 600");
                        treure(600);
                        System.out.println("[" + Thread.currentThread().getName() + "] (2/2) Ingressant 200");
                        ingressar(200);
                        break;
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
